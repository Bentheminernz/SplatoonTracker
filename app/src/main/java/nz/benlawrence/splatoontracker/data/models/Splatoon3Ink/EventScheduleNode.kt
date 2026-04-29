package nz.benlawrence.splatoontracker.data.models.Splatoon3Ink

data class EventScheduleNode(
    val leagueMatchSetting: LeagueMatchSetting,
    val timePeriods: List<TimePeriod>
)