package nz.benlawrence.splatoontracker.data

import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.SchedulesResponse
import nz.benlawrence.splatoontracker.data.models.coral.AuthURL
import nz.benlawrence.splatoontracker.data.models.coral.SessionRequest
import nz.benlawrence.splatoontracker.data.models.coral.SessionResponse
import nz.benlawrence.splatoontracker.data.models.coral.SplatnetAuthData
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleDetailRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.BankaraBattleHistoriesResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.RegularBattleHistoryResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleDetail
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleHistories
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopDetailBlobRequest
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetailResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryResponse
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.sideorder.SideOrderRecords
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.sql.Blob

interface Splatoon3InkAPI {
    @GET("schedules.json")
    suspend fun getSchedules(): SchedulesResponse
}

data class SessionBlobRequest(
    val sessionBlob: String
)

interface CoralAPI {
    @GET("auth/url")
    suspend fun getAuthURL(): AuthURL

    @POST("auth/session")
    suspend fun createSession(@Body request: SessionRequest): SessionResponse

    @POST("splatnet3/side-order")
    suspend fun fetchSideOrderRecords(@Body request: SessionBlobRequest): BlobResponse<SideOrderRecords>

    @POST("splatnet3/coop/history")
//    suspend fun fetchCoopHistory(@Body request: Map<String, SplatnetAuthData>): CoopResultResponse
    suspend fun fetchCoopHistory(@Body request: SessionBlobRequest): BlobResponse<CoopHistoryResponse>

    @POST("splatnet3/coop/history/details")
    suspend fun fetchCoopHistoryDetails(@Body request: CoopDetailBlobRequest): BlobResponse<CoopHistoryDetailResponse>

    @POST("splatnet3/bankara/history")
    suspend fun fetchBankaraHistory(@Body request: SessionBlobRequest): BlobResponse<BankaraBattleHistoriesResponse>

    @POST("splatnet3/regular-battle/history")
    suspend fun fetchRegularBattleHistory(@Body request: SessionBlobRequest): BlobResponse<RegularBattleHistoryResponse>

    @POST("splatnet3/vsbattle/history/details")
    suspend fun fetchVsBattleBattleHistoryDetails(@Body request: VsBattleDetailRequest): BlobResponse<VsBattleDetail>
}