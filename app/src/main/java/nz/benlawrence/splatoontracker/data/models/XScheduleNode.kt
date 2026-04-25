package nz.benlawrence.splatoontracker.data.models

data class XScheduleNode(
    val endTime: String,
    val festMatchSettings: Any,
    val startTime: String,
    val xMatchSetting: XMatchSetting
)