package nz.benlawrence.splatoontracker.data.models

data class BankaraNode(
    val bankaraMatchSettings: List<BankaraMatchSetting>,
    val endTime: String,
    val festMatchSettings: Any,
    val startTime: String
)