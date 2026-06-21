package nz.benlawrence.splatoontracker.data.models.Splatoon3Ink

data class Setting(
    val __isCoopSetting: String,
    val __typename: String,
    val boss: Boss,
    val rule: CoopRule?,
    val coopStage: CoopStage,
    val weapons: List<Weapon>
)

enum class CoopRule {
    BIG_RUN
}