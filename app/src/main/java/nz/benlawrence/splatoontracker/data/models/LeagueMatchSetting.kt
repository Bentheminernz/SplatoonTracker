package nz.benlawrence.splatoontracker.data.models

data class LeagueMatchSetting(
    val __isVsSetting: String,
    val __typename: String,
    val leagueMatchEvent: LeagueMatchEvent,
    val vsRule: VsRule,
    val vsStages: List<VsStage>
)