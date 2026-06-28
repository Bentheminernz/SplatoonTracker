package nz.benlawrence.splatoontracker.data.models.Splatoon3Ink

import nz.benlawrence.splatoontracker.ui.components.ScheduleDisplayData

data class RegularScheduleNode(
    val endTime: String,
    val festMatchSettings: Any?,
    val regularMatchSetting: RegularMatchSetting,
    val startTime: String
)

fun RegularScheduleNode.toScheduleDisplayData(): ScheduleDisplayData {
  return ScheduleDisplayData(
    vsStages = regularMatchSetting.vsStages,
    vsRule = regularMatchSetting.vsRule,
    startTime = startTime,
    endTime = endTime
  )
}