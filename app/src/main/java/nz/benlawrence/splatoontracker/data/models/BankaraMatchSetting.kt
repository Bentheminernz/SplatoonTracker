package nz.benlawrence.splatoontracker.data.models

data class BankaraMatchSetting(
    val __isVsSetting: String,
    val __typename: String,
    val bankaraMode: String,
    val vsRule: VsRule,
    val vsStages: List<VsStage>
)