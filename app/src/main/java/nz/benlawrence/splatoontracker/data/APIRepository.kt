package nz.benlawrence.splatoontracker.data

import nz.benlawrence.splatoontracker.data.models.Data
import nz.benlawrence.splatoontracker.data.models.SchedulesResponse
import retrofit2.http.GET

interface SplatoonAPI {
    @GET("schedules.json")
    suspend fun getSchedules(): SchedulesResponse
}