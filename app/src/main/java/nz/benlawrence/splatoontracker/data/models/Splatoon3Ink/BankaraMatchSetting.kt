package nz.benlawrence.splatoontracker.data.models.Splatoon3Ink

data class BankaraMatchSetting(
  val __isVsSetting: String,
  val __typename: String,
  var bankaraMode: String,
  val vsRule: VsRule,
  val vsStages: List<VsStage>
)