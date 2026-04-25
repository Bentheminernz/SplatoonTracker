package nz.benlawrence.splatoontracker.data.models

data class Setting(
    val __isCoopSetting: String,
    val __typename: String,
    val boss: Boss,
    val coopStage: CoopStage,
    val weapons: List<Weapon>
)