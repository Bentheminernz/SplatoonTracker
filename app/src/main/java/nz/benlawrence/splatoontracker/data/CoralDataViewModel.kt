package nz.benlawrence.splatoontracker.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.benlawrence.splatoontracker.data.models.coral.AuthURL
import nz.benlawrence.splatoontracker.data.models.coral.SessionRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleDetail
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleDetailRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleHistories
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopDetailBlobRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetailResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.sideorder.SideOrderRecords
import nz.benlawrence.splatoontracker.utils.debugOnly

@Suppress("unused")
class CoralDataViewModel(appContext: Context?) : ViewModel() {

  private val sessionCache = appContext?.let {
    SessionCache(it)
  }

  val isAuthenticated: Boolean
    get() = sessionCache != null

  var dataState: CoralDataState by mutableStateOf(CoralDataState())
    private set

  init {
    if (appContext == null) {
      debugOnly { Log.e("CoralDataViewModel", "CoralDataViewModel created with null context") }
    }
    loadCachedSession()

    if (!isAuthenticated) {
      loadAuthURL()
    }
  }

  // region Auth / Session

  private fun loadCachedSession() {
    if (sessionCache == null) {
      debugOnly { Log.e("CoralDataViewModel", "SessionCache is null - cannot load session") }
      return
    }

    viewModelScope.launch {
      try {
        val blob = sessionCache.getSessionBlob()
        if (blob != null) {
          dataState = dataState.copy(sessionBlob = blob)
        }
      } catch (e: Exception) {
        debugOnly { Log.e("CoralDataViewModel", "Exception loading cached session", e) }
      }
    }
  }

  fun loadAuthURL() = fetchData(
    setLoading = { it.copy(authURL = DataState.Loading) },
    setResult = { state, result -> state.copy(authURL = result) },
    fetch = { SplatoonAPIClient.coralAPI.getAuthURL() },
    tag = "auth URL"
  )

  fun createSession(session: SessionRequest) {
    viewModelScope.launch {
      dataState = dataState.copy(sessionBlob = null)
      try {
        val response = SplatoonAPIClient.coralAPI.createSession(session)
        sessionCache?.saveSessionBlob(response.sessionBlob)
        dataState = dataState.copy(sessionBlob = response.sessionBlob)
      } catch (e: Exception) {
        debugOnly { Log.e("CoralDataViewModel", "Error creating session", e) }
      }
    }
  }

  fun clearSessionCache() {
    viewModelScope.launch {
      sessionCache?.clearSession()
      dataState = dataState.copy(sessionBlob = null)
    }
  }

  private suspend fun recoverSessionBlobFromCache(): String? {
    return try {
      val recoveredBlob = sessionCache?.getSessionBlob()
      if (recoveredBlob != null) {
        dataState = dataState.copy(sessionBlob = recoveredBlob)
        recoveredBlob
      } else {
        null
      }
    } catch (e: Exception) {
      debugOnly { Log.e("CoralDataViewModel", "Exception recovering blob from cache", e) }
      null
    }
  }

  private fun handleNewBlob(newBlob: String?) {
    if (newBlob != null) {
      viewModelScope.launch {
        sessionCache?.saveSessionBlob(newBlob)
      }
      dataState = dataState.copy(sessionBlob = newBlob)
    }
  }

  // endregion

  // region Side Order

  fun fetchSideOrderRecords() = fetchBlobData(
    setLoading = { it.copy(sideOrderRecords = DataState.Loading) },
    setResult = { state, result -> state.copy(sideOrderRecords = result) },
    fetch = { blob -> SplatoonAPIClient.coralAPI.fetchSideOrderRecords(SessionBlobRequest(sessionBlob = blob)) },
    tag = "side order records"
  )

  // endregion

  // region Salmon Run

  fun getCoopResult() = fetchBlobData(
    setLoading = { it.copy(coopResult = DataState.Loading) },
    setResult = { state, result -> state.copy(coopResult = result) },
    fetch = { blob -> SplatoonAPIClient.coralAPI.fetchCoopHistory(SessionBlobRequest(sessionBlob = blob)) },
    tag = "coop result"
  )

  fun getCoopHistoryDetail(id: String) = fetchBlobDetail(
    id = id,
    getMap = { it.coopHistoryDetails },
    setMap = { state, map -> state.copy(coopHistoryDetails = map) },
    fetch = { blob ->
      SplatoonAPIClient.coralAPI.fetchCoopHistoryDetails(
        CoopDetailBlobRequest(
          sessionBlob = blob,
          historyDetailRequest = CoopDetailBlobRequest.CoopHistoryDetailRequest(historyId = id)
        )
      )
    },
    tag = "coop history detail"
  )

  // endregion

  // region Bankara Battles

  fun getBankaraBattleHistories() = fetchData(
    setLoading = { it.copy(bankaraBattleHistories = DataState.Loading) },
    setResult = { state, result -> state.copy(bankaraBattleHistories = result) },
    fetch = {
      val blob = dataState.sessionBlob ?: throw IllegalStateException("No session blob available")
      SplatoonAPIClient.coralAPI.fetchBankaraHistory(SessionBlobRequest(sessionBlob = blob)).data.bankaraBattleHistories
    },
    tag = "bankara battle histories"
  )

  // endregion

  // region Regular Battles

  fun getRegularBattleHistories() = fetchData(
    setLoading = { it.copy(regularBattleHistories = DataState.Loading) },
    setResult = { state, result -> state.copy(regularBattleHistories = result) },
    fetch = {
      val blob = resolveSessionBlob() ?: throw IllegalStateException("Session blob is null")
      SplatoonAPIClient.coralAPI.fetchRegularBattleHistory(SessionBlobRequest(sessionBlob = blob)).data.latestBattleHistories
    },
    tag = "regular battle histories"
  )

  // endregion

  // region Vs Battle Details
  fun getVsBattleHistoryDetail(id: String) = fetchBlobDetail(
    id = id,
    getMap = { it.vsBattleHistoryDetails },
    setMap = { state, map -> state.copy(vsBattleHistoryDetails = map) },
    fetch = { blob ->
      SplatoonAPIClient.coralAPI.fetchVsBattleBattleHistoryDetails(
        VsBattleDetailRequest(
          sessionBlob = blob,
          historyDetailRequest = VsBattleDetailRequest.HistoryDetailRequest(historyId = id)
        )
      )
    },
    tag = "regular battle detail"
  )

  // region Helpers

  private suspend fun resolveSessionBlob(): String? {
    dataState.sessionBlob?.let { return it }
    return recoverSessionBlobFromCache()
  }

  private fun <T> fetchBlobData(
    setLoading: (CoralDataState) -> CoralDataState,
    setResult: (CoralDataState, DataState<T>) -> CoralDataState,
    fetch: suspend (sessionBlob: String) -> BlobResponse<T>,
    tag: String
  ) {
    viewModelScope.launch {
      val blob = resolveSessionBlob() ?: return@launch
      dataState = setLoading(dataState)
      try {
        val response = fetch(blob)
        handleNewBlob(response.sessionBlob)
        dataState = setResult(dataState, DataState.Success(response.data))
      } catch (e: Exception) {
        dataState = setResult(dataState, DataState.Error(e.message ?: "An error has occurred"))
        debugOnly { Log.e("CoralDataViewModel", "Error fetching $tag", e) }
      }
    }
  }

  private fun <T> fetchBlobDetail(
    id: String,
    getMap: (CoralDataState) -> Map<String, DataState<T>>,
    setMap: (CoralDataState, Map<String, DataState<T>>) -> CoralDataState,
    fetch: suspend (sessionBlob: String) -> BlobResponse<T>,
    tag: String
  ) {
    viewModelScope.launch {
      val blob = resolveSessionBlob() ?: return@launch
      dataState = setMap(dataState, getMap(dataState) + (id to DataState.Loading))
      try {
        val response = fetch(blob)
        handleNewBlob(response.sessionBlob)
        dataState = setMap(dataState, getMap(dataState) + (id to DataState.Success(response.data)))
      } catch (e: Exception) {
        dataState = setMap(dataState, getMap(dataState) + (id to DataState.Error(e.message ?: "An error has occurred")))
        debugOnly { Log.e("CoralDataViewModel", "Error fetching $tag $id", e) }
      }
    }
  }

  private fun <T> fetchData(
    setLoading: (CoralDataState) -> CoralDataState,
    setResult: (CoralDataState, DataState<T>) -> CoralDataState,
    fetch: suspend () -> T,
    tag: String
  ) {
    viewModelScope.launch {
      dataState = setLoading(dataState)
      try {
        dataState = setResult(dataState, DataState.Success(fetch()))
      } catch (e: Exception) {
        dataState = setResult(dataState, DataState.Error(e.message ?: "An error has occurred"))
        debugOnly { Log.e("CoralDataViewModel", "Error fetching $tag", e) }
      }
    }
  }

  // endregion
}

data class BlobResponse<T>(
  val data: T,
  val sessionBlob: String?
)

sealed class DataState<out T> {
  object Loading : DataState<Nothing>()
  data class Success<T>(val data: T) : DataState<T>()
  data class Error(val message: String) : DataState<Nothing>()
}

data class CoralDataState(
  val authURL: DataState<AuthURL> = DataState.Loading,
  val sessionBlob: String? = null,
  val sideOrderRecords: DataState<SideOrderRecords> = DataState.Loading,
  val coopResult: DataState<CoopHistoryResponse> = DataState.Loading,
  val coopHistoryDetails: Map<String, DataState<CoopHistoryDetailResponse>> = emptyMap(),
  val bankaraBattleHistories: DataState<VsBattleHistories> = DataState.Loading,
  val regularBattleHistories: DataState<VsBattleHistories> = DataState.Loading,
  val vsBattleHistoryDetails: Map<String, DataState<VsBattleDetail>> = emptyMap()
)