package nz.benlawrence.splatoontracker.data.models

data class LeagueMatchEvent(
    val desc: String,
    val id: String,
    val leagueMatchEventId: String,
    val name: String,
    val regulation: String,
    val regulationUrl: Any
)