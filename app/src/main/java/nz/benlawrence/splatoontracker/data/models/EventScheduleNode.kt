package nz.benlawrence.splatoontracker.data.models

data class EventScheduleNode(
    val leagueMatchSetting: LeagueMatchSetting,
    val timePeriods: List<TimePeriod>
)