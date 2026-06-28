package nz.benlawrence.splatoontracker.data.models.Splatoon3Ink
import kotlinx.serialization.Serializable

@Serializable
data class SchedulesResponse(
    val data: Data
)

data class Data(
    val bankaraSchedules: BankaraSchedules,
    val coopGroupingSchedule: CoopGroupingSchedule,
    val currentFest: Any,
    val currentPlayer: CurrentPlayer,
    val eventSchedules: EventSchedules,
    val festSchedules: FestSchedules,
    val regularSchedules: RegularSchedulesX,
    val vsStages: VsStages,
    val xSchedules: XSchedules
)