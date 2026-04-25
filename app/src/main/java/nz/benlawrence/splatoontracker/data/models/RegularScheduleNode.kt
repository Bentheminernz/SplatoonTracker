package nz.benlawrence.splatoontracker.data.models

data class RegularScheduleNode(
    val endTime: String,
    val festMatchSettings: Any,
    val regularMatchSetting: RegularMatchSetting,
    val startTime: String
)