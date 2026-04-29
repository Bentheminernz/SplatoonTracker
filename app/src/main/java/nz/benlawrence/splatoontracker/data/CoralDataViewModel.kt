package nz.benlawrence.splatoontracker.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import nz.benlawrence.splatoontracker.data.models.coral.AuthURL
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.benlawrence.splatoontracker.data.models.coral.SessionRequest
import nz.benlawrence.splatoontracker.data.models.coral.SessionResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.sideorder.SideOrderRecords

class CoralDataViewModel(context: Context? = null): ViewModel() {
  private val sessionCache = context?.let { SessionCache(it) }
  var dataState: CoralDataState by mutableStateOf(CoralDataState())
    private set

  init {
      loadAuthURL()
      loadCachedSession()
  }

  private fun loadCachedSession() {
    val cached = sessionCache?.getSession()
    if (cached != null) {
      dataState = dataState.copy(coralSession = DataState.Success(cached))
      fetchSideOrderRecords()
      Log.d("CoralDataViewModel", "Loaded session from cache")
    } else {
      Log.e("CoralDataViewModel", "No valid session found in cache")
    }
  }

  fun loadAuthURL() {
     viewModelScope.launch {
       dataState = dataState.copy(authURL = DataState.Loading)
       try {
         val response = SplatoonAPIClient.coralAPI.getAuthURL()
         dataState = dataState.copy(authURL = DataState.Success(response))
       } catch(e: Exception) {
         dataState = dataState.copy(authURL = DataState.Error(e.message ?: "An error has occurred"))
         Log.e("CoralDataViewModel", "Error loading auth URL", e)
         e.printStackTrace()
       }
     }
  }

  fun createSession(session: SessionRequest) {
    viewModelScope.launch {
      dataState = dataState.copy(coralSession = DataState.Loading)
      try {
        val response = SplatoonAPIClient.coralAPI.createSession(session)
        sessionCache?.saveSession(response)
        dataState = dataState.copy(coralSession = DataState.Success(response))
        Log.d("CoralDataViewModel", "Session created and cached")
        fetchSideOrderRecords()
      } catch(e: Exception) {
        dataState = dataState.copy(coralSession = DataState.Error(e.message ?: "An error has occurred"))
        Log.e("CoralDataViewModel", "Error creating session", e)
        e.printStackTrace()
      }
    }
  }

  fun fetchSideOrderRecords() {
    val sessionData = (dataState.coralSession as? DataState.Success)?.data

    if (sessionData == null) {
      dataState = dataState.copy(coralSession = DataState.Error("Session Data is null"))
    }

    viewModelScope.launch {
      dataState = dataState.copy(sideOrderRecords = DataState.Loading)
      try {
        val response = SplatoonAPIClient.coralAPI.fetchSideOrderRecords(mapOf("splatnetAuthData" to sessionData!!.splatnet.data))
        Log.e("CoralDataViewModel", "Fetched side order records: $response")
        // The API nests the record under `challenges.sideOrderRecord` — use the correct path
        Log.e(
          "CoralDataViewModel",
          "High Score: ${response.challenges?.sideOrderRecord?.highestScoreTryResult?.floor ?: "Undefined"}"
        )
        dataState = dataState.copy(sideOrderRecords = DataState.Success(response))
      } catch(e: Exception) {
        dataState = dataState.copy(sideOrderRecords = DataState.Error(e.message ?: "An error has occurred"))
        Log.e("CoralDataViewModel", "Error fetching side order records", e)
        e.printStackTrace()
      }
    }
  }

  fun clearSessionCache() {
    sessionCache?.clearSession()
    dataState = dataState.copy(coralSession = DataState.Loading)
    Log.d("CoralDataViewModel", "Session cache cleared")
  }
}

sealed class DataState<out T> {
  object Loading: DataState<Nothing>()
  data class Success<T>(val data: T): DataState<T>()
  data class Error(val message: String): DataState<Nothing>()
}

data class CoralDataState(
  val authURL: DataState<AuthURL> = DataState.Loading,
  val coralSession: DataState<SessionResponse> = DataState.Loading,
  val sideOrderRecords: DataState<SideOrderRecords> = DataState.Loading
)