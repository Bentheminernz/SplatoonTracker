package nz.benlawrence.splatoontracker.data.models

import nz.benlawrence.splatoontracker.ui.components.ScheduleDisplayData

data class BankaraNode(
    val bankaraMatchSettings: List<BankaraMatchSetting>,
    val endTime: String,
    val festMatchSettings: Any?,
    val startTime: String,
)

fun BankaraNode.toDisplayData(mode: String): ScheduleDisplayData {
    val setting = bankaraMatchSettings.first { it.bankaraMode == mode }
    return ScheduleDisplayData(
        vsStages = setting.vsStages,
        vsRule = setting.vsRule,
        startTime = startTime,
        endTime = endTime
    )
}