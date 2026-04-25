package nz.benlawrence.splatoontracker.data.models

data class XMatchSetting(
    val __isVsSetting: String,
    val __typename: String,
    val vsRule: VsRule,
    val vsStages: List<VsStage>
)