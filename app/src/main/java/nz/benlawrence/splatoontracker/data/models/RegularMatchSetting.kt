package nz.benlawrence.splatoontracker.data.models

data class RegularMatchSetting(
    val __isVsSetting: String,
    val __typename: String,
    val vsRule: VsRule,
    val vsStages: List<VsStage>
)