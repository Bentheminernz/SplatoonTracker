package nz.benlawrence.splatoontracker.data.models.coral.splatnet.sideorder

import kotlinx.serialization.Serializable
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Connection
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Edge
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Image
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.SpecialWeapon
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.SubWeapon
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.UnknownScalar
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.WallpaperReward
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Weapon


@Serializable
data class SideOrderRecords(
  val record: SideOrderRecordWrapper? = null,
  val challenges: SideOrderRecordWrapper? = null,
  val colorChips: SideOrderRecordWrapper? = null,
  val pallets: SideOrderRecordWrapper? = null,
  val enemies: SideOrderRecordWrapper? = null,
)

@Serializable
data class SideOrderRecordWrapper(
  val sideOrderRecord: SideOrderRecord? = null,
)

data class SideOrderChipCategoryGroup(
  val id: String,
  val group: String,
  val image: Image,
  val name: String,
  val sideOrderChipCategoryGroupId: String,
)

data class SideOrderFeverChipCategoryGroup(
  val id: String,
  val group: String,
  val image: Image,
  val name: String,
)

data class SideOrderHackCategory(
  val id: String,
  val name: String,
)

data class SideOrderHack(
  val id: String,
  val currentLevel: Int,
  val hackCategory: SideOrderHackCategory,
  val image: Image,
  val maxLevel: Int,
  val name: String,
)

data class SideOrderStage(
  val id: String,
  val name: String,
)

data class SideOrderSubGoal(
  val id: String,
  val name: String,
)

data class SideOrderTroubleEvent(
  val id: String,
  val name: String,
)

data class SideOrderTryRule(
  val id: String,
  val image: Image,
  val name: String,
)

data class SideOrderEnemy(
  val id: String,
  val image: Image,
  val isPortal: Boolean,
  val name: String,
)

data class SideOrderChipCategory(
  val id: String,
  val image: Image,
  val categoryGroup: SideOrderChipCategoryGroup,
  val name: String,
)

data class SideOrderChip(
  val id: String,
  val chipCategory: SideOrderChipCategory,
  val image: Image,
  val maxAccumulateCount: Int,
  val name: String,
)

data class SideOrderChipRecord(
  val acquireCount: Int,
  val chip: SideOrderChip,
  val currentAccumulateCount: Int,
  val highestAccumulateCount: Int,
)

typealias SideOrderChipRecordConnection = Connection<SideOrderChipRecord>

data class SideOrderDroneAction(
  val image: Image,
  val isEmpty: Boolean,
  val name: String,
)

data class SideOrderChipUsageRate(
  val __typename: String = "SideOrderChipUsageRate",
  val chipCategoryGroup: SideOrderChipCategoryGroup,
  val usageRate: Double,
)

data class SideOrderHackSetting(
  val currentLevel: Int,
  val hack: SideOrderHack,
)

typealias SideOrderHackSettingConnection = Connection<SideOrderHackSetting>

data class SideOrderTryPoint(
  val chip: SideOrderChip,
  val chipIndex: Int,
  val kind: String,
  val point: Int,
)

typealias SideOrderTryPointConnection = Connection<SideOrderTryPoint>
typealias SideOrderTryPointEdge = Edge<SideOrderTryPoint>

data class SideOrderLastStage(
  val id: String,
  val boss: SideOrderEnemy?,
  val difficulty: String,
  val rule: SideOrderTryRule?,
  val stage: SideOrderStage,
  val subGoal: SideOrderSubGoal?,
  val troubleEvent: SideOrderTroubleEvent?,
  val feverChipCategoryGroup: SideOrderFeverChipCategoryGroup?,
)

typealias SideOrderChipConnection = Connection<SideOrderChip>

data class SideOrderTryResult(
  val id: String,
  val cause: String,
  val clearTimeSec: Double,
  val enemy: SideOrderEnemy,
  val floor: Int,
  val nextTryResult: SideOrderTryResult?,
  val palette: SideOrderPalette,
  val playedTime: String,
  val previousTryResult: SideOrderTryResult?,
  val primaryChipUsageRate: SideOrderChipUsageRate,
  val result: UnknownScalar,
  val retry: UnknownScalar,
  val score: Int,
  val secondaryChipUsageRate: SideOrderChipUsageRate,
  val slots: List<SideOrderChip?>,
  val chipRecords: SideOrderChipRecordConnection,
  val hackSettings: SideOrderHackSettingConnection,
  val points: SideOrderTryPointConnection,
  val secondBoss: SideOrderEnemy,
  val firstBoss: SideOrderEnemy?,
  val lastStage: SideOrderLastStage?,
  val hack: SideOrderHack?,
  val availableChipSlotCount: Int,
  val bossHackLevel: Int,
  val coin: Int,
  val droneHackLevel: Int,
  val gemHackLevel: Int,
  val hackCategoryLevel: Int,
  val hackTotalLevel: Int,
  val ikuraHackLevel: Int,
  val paidCoin: Int,
  val paletteKeyStatus: UnknownScalar,
  val pearl: UnknownScalar,
  val pearlRate: UnknownScalar,
  val playerHackLevel: Int,
  val randomHackLevel: Int,
  val shopHackLevel: Int,
  val specialWeapon: SpecialWeapon,
  val subWeapon: SubWeapon,
  val chips: SideOrderChipConnection,
)

typealias SideOrderTryResultConnection = Connection<SideOrderTryResult>

data class SideOrderPalette(
  val id: String,
  val isHachi: Boolean,
  val sideOrderPaletteId: String,
  val image: Image,
  val name: String,
  val weapon: Weapon,
)

data class SideOrderPaletteRecord(
  val clearCount: Int,
  val clearOrder: Int,
  val fastestTryResult: SideOrderTryResult,
  val highestFloor: Int,
  val palette: SideOrderPalette,
  val reward: WallpaperReward,
)

data class SideOrderDefeatEnemyRecord(
  val defeatCount: Int,
  val enemy: SideOrderEnemy,
)

@Serializable
data class SideOrderRecord(
  val __typename: String = "SideOrderRecord",
  val chipUsageRates: List<SideOrderChipUsageRate> = emptyList(),
  val defeatBossRecords: List<SideOrderDefeatEnemyRecord> = emptyList(),
  val defeatEnemyRecords: List<SideOrderDefeatEnemyRecord> = emptyList(),
  val droneColorChips: Connection<SideOrderChip>,
  val highestScoreTryResult: SideOrderTryResult? = null,
  val luckyColorChips: Connection<SideOrderChip>,
  val moveColorChips: Connection<SideOrderChip>,
  val palettes: List<SideOrderPaletteRecord> = emptyList(),
  val powerColorChips: Connection<SideOrderChip>,
  val rangeColorChips: Connection<SideOrderChip>,
  val reward: WallpaperReward? = null,
  val supportColorChips: Connection<SideOrderChip>,
  val tryResults: SideOrderTryResultConnection? = null,
)