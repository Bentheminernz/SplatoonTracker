package nz.benlawrence.splatoontracker.data

import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.SchedulesResponse
import nz.benlawrence.splatoontracker.data.models.coral.AuthURL
import nz.benlawrence.splatoontracker.data.models.coral.SessionRequest
import nz.benlawrence.splatoontracker.data.models.coral.SessionResponse
import nz.benlawrence.splatoontracker.data.models.coral.SplatnetAuthData
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.BankaraBattleHistoriesRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.BankaraBattleHistoriesResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleDetail
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetailRequestBody
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetailResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopResultResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.sideorder.SideOrderRecords
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Splatoon3InkAPI {
    @GET("schedules.json")
    suspend fun getSchedules(): SchedulesResponse
}

interface CoralAPI {
    @GET("auth/url")
    suspend fun getAuthURL(): AuthURL

    @POST("auth/session")
    suspend fun createSession(@Body request: SessionRequest): SessionResponse

    @POST("splatnet3/side-order")
    suspend fun fetchSideOrderRecords(@Body request: Map<String, SplatnetAuthData>): SideOrderRecords

    @POST("splatnet3/coop/history")
//    suspend fun fetchCoopHistory(@Body request: Map<String, SplatnetAuthData>): CoopResultResponse
    suspend fun fetchCoopHistory(): CoopResultResponse

    @POST("splatnet3/coop/history/details")
    suspend fun fetchCoopHistoryDetails(@Body request: CoopHistoryDetailRequestBody): CoopHistoryDetailResponse

    @POST("splatnet3/bankara/history")
//    suspend fun fetchBankaryHistory(@Body request: Map<String, SplatnetAuthData>): BankaraBattleHistoriesResponse
    suspend fun fetchBankaryHistory(): BankaraBattleHistoriesResponse

    @POST("splatnet3/bankara/history/details")
    suspend fun fetchBankaryHistoryDetails(@Body request: BankaraBattleHistoriesRequest): VsBattleDetail
}