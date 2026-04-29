package nz.benlawrence.splatoontracker.data.models.Splatoon3Ink

data class CoopGroupingSchedule(
    val bannerImage: Any,
    val bigRunSchedules: BigRunSchedules,
    val regularSchedules: CoopGroupingRegularSchedules,
    val teamContestSchedules: TeamContestSchedules
)