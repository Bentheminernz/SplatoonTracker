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
import nz.benlawrence.splatoontracker.data.models.coral.SessionResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleDetail
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleDetailRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleHistories
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetailRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetailRequestBody
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetailResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopResult
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.sideorder.SideOrderRecords
import nz.benlawrence.splatoontracker.utils.debugOnly

class CoralDataViewModel(context: Context? = null) : ViewModel() {
  private val sessionCache = context?.let { SessionCache(it) }

  var dataState: CoralDataState by mutableStateOf(CoralDataState())
    private set

  init {
    loadAuthURL()
    loadCachedSession()
  }

  // region Auth / Session

  private fun loadCachedSession() {
    val cached = sessionCache?.getSession()
    if (cached != null) {
      dataState = dataState.copy(coralSession = DataState.Success(cached))
      debugOnly { Log.d("CoralDataViewModel", "Loaded session from cache") }
    } else {
      debugOnly { Log.e("CoralDataViewModel", "No valid session found in cache") }
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
      dataState = dataState.copy(coralSession = DataState.Loading)
      try {
        val response = SplatoonAPIClient.coralAPI.createSession(session)
        sessionCache?.saveSession(response)
        dataState = dataState.copy(coralSession = DataState.Success(response))
        debugOnly { Log.d("CoralDataViewModel", "Session created and cached") }
        fetchSideOrderRecords()
      } catch (e: Exception) {
        dataState = dataState.copy(coralSession = DataState.Error(e.message ?: "An error has occurred"))
        debugOnly { Log.e("CoralDataViewModel", "Error creating session", e) }
      }
    }
  }

  fun clearSessionCache() {
    sessionCache?.clearSession()
    dataState = dataState.copy(coralSession = DataState.Loading)
    debugOnly { Log.d("CoralDataViewModel", "Session cache cleared") }
  }

  // endregion

  // region Side Order

  fun fetchSideOrderRecords() {
    val sessionData = (dataState.coralSession as? DataState.Success)?.data
    if (sessionData == null) {
      dataState = dataState.copy(sideOrderRecords = DataState.Error("Session Data is null"))
      return
    }

    fetchData(
      setLoading = { it.copy(sideOrderRecords = DataState.Loading) },
      setResult = { state, result -> state.copy(sideOrderRecords = result) },
      fetch = { SplatoonAPIClient.coralAPI.fetchSideOrderRecords(mapOf("splatnetAuthData" to sessionData.splatnet.data)) },
      tag = "side order records"
    )
  }

  // endregion

  // region Salmon Run

  fun getCoopResult() = fetchData(
    setLoading = { it.copy(coopResult = DataState.Loading) },
    setResult = { state, result -> state.copy(coopResult = result) },
    fetch = { SplatoonAPIClient.coralAPI.fetchCoopHistory().coopHistory.coopResult },
    tag = "coop result"
  )

  fun getCoopHistoryDetail(id: String) = fetchDetail(
    id = id,
    getMap = { it.coopHistoryDetails },
    setMap = { state, map -> state.copy(coopHistoryDetails = map) },
    fetch = {
      SplatoonAPIClient.coralAPI.fetchCoopHistoryDetails(
        CoopHistoryDetailRequestBody(historyDetailRequest = CoopHistoryDetailRequest(id))
      )
    },
    tag = "coop history detail"
  )

  // endregion

  // region Bankara Battles

  fun getBankaraBattleHistories() = fetchData(
    setLoading = { it.copy(bankaraBattleHistories = DataState.Loading) },
    setResult = { state, result -> state.copy(bankaraBattleHistories = result) },
    fetch = { SplatoonAPIClient.coralAPI.fetchBankaryHistory().bankaraBattleHistories },
    tag = "bankara battle histories"
  )

  fun getBankaraBattleHistoryDetail(id: String) = fetchDetail(
    id = id,
    getMap = { it.bankaraHistoryDetails },
    setMap = { state, map -> state.copy(bankaraHistoryDetails = map) },
    fetch = {
      SplatoonAPIClient.coralAPI.fetchBankaryHistoryDetails(
        VsBattleDetailRequest(battleDetailRequest = VsBattleDetailRequest.BattleDetailRequest(id))
      )
    },
    tag = "bankara battle detail"
  )

  // endregion

  // region Regular Battles

  fun getRegularBattleHistories() = fetchData(
    setLoading = { it.copy(regularBattleHistories = DataState.Loading) },
    setResult = { state, result -> state.copy(regularBattleHistories = result) },
    fetch = { SplatoonAPIClient.coralAPI.fetchRegularBattleHistory().regularBattleHistory.latestBattleHistories },
    tag = "regular battle histories"
  )

  fun getRegularBattleHistoryDetail(id: String) = fetchDetail(
    id = id,
    getMap = { it.regularBattleHistoryDetails },
    setMap = { state, map -> state.copy(regularBattleHistoryDetails = map) },
    fetch = {
      SplatoonAPIClient.coralAPI.fetchRegularBattleHistoryDetails(
        VsBattleDetailRequest(battleDetailRequest = VsBattleDetailRequest.BattleDetailRequest(id))
      )
    },
    tag = "regular battle detail"
  )

  // endregion

  // region helpers

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

  private fun <T> fetchDetail(
    id: String,
    getMap: (CoralDataState) -> Map<String, DataState<T>>,
    setMap: (CoralDataState, Map<String, DataState<T>>) -> CoralDataState,
    fetch: suspend () -> T,
    tag: String
  ) {
    viewModelScope.launch {
      dataState = setMap(dataState, getMap(dataState) + (id to DataState.Loading))
      try {
        dataState = setMap(dataState, getMap(dataState) + (id to DataState.Success(fetch())))
      } catch (e: Exception) {
        dataState = setMap(dataState, getMap(dataState) + (id to DataState.Error(e.message ?: "An error has occurred")))
        debugOnly { Log.e("CoralDataViewModel", "Error fetching $tag $id", e) }
      }
    }
  }

  // endregion
}

sealed class DataState<out T> {
  object Loading : DataState<Nothing>()
  data class Success<T>(val data: T) : DataState<T>()
  data class Error(val message: String) : DataState<Nothing>()
}

data class CoralDataState(
  val authURL: DataState<AuthURL> = DataState.Loading,
  val coralSession: DataState<SessionResponse> = DataState.Loading,
  val sideOrderRecords: DataState<SideOrderRecords> = DataState.Loading,
  val coopResult: DataState<CoopResult> = DataState.Loading,
  val coopHistoryDetails: Map<String, DataState<CoopHistoryDetailResponse>> = emptyMap(),
  val bankaraBattleHistories: DataState<VsBattleHistories> = DataState.Loading,
  val bankaraHistoryDetails: Map<String, DataState<VsBattleDetail>> = emptyMap(),
  val regularBattleHistories: DataState<VsBattleHistories> = DataState.Loading,
  val regularBattleHistoryDetails: Map<String, DataState<VsBattleDetail>> = emptyMap()
)