package nz.benlawrence.splatoontracker.data.models

data class CoopGroupingSchedule(
    val bannerImage: Any,
    val bigRunSchedules: BigRunSchedules,
    val regularSchedules: CoopGroupingRegularSchedules,
    val teamContestSchedules: TeamContestSchedules
)