package nz.benlawrence.splatoontracker.data.models.Splatoon3Ink

import nz.benlawrence.splatoontracker.ui.components.ScheduleDisplayData

data class XScheduleNode(
    val endTime: String,
    val festMatchSettings: Any?,
    val startTime: String,
    val xMatchSetting: XMatchSetting
)

fun XScheduleNode.toScheduleDisplayData(): ScheduleDisplayData {
  return ScheduleDisplayData(
    startTime = startTime,
    endTime = endTime,
    vsStages = xMatchSetting.vsStages,
    vsRule = xMatchSetting.vsRule
  )
}