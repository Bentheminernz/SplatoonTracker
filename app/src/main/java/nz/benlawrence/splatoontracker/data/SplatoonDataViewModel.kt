package nz.benlawrence.splatoontracker.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.Data
import nz.benlawrence.splatoontracker.utils.debugOnly

class SplatoonDataViewModel: ViewModel() {
    var dataState: SplatoonDataState by mutableStateOf(SplatoonDataState.Loading)
        private set

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            try {
                val response = SplatoonAPIClient.splatoonAPI.getSchedules()
                dataState = SplatoonDataState.Success(response.data)
                debugOnly {
                    Log.d("SplatoonDataViewModel", "Data loaded successfully")
                }
            } catch(e: Exception) {
                dataState = SplatoonDataState.Error(e.message ?: "An error has occured")
                debugOnly {
                    Log.e("SplatoonDataViewModel", "Error loading data", e)
                }
                e.printStackTrace()
            }
        }
    }
}

sealed class SplatoonDataState {
    object Loading: SplatoonDataState()
    data class Success(val data: Data): SplatoonDataState()
    data class Error(val message: String): SplatoonDataState()
}