package nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun

import kotlinx.serialization.Serializable
import nz.benlawrence.splatoontracker.data.models.coral.SplatnetAuthData
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Image

// --- Request types ---

data class CoopHistoryDetailRequestBody(
//  val splatnetAuthData: SplatnetAuthData,
  val historyDetailRequest: CoopHistoryDetailRequest
)

data class CoopHistoryDetailRequest(
  val historyId: String
)

// --- Response types ---
@Serializable
data class CoopHistoryDetailResponse(
  val coopHistoryDetail: CoopHistoryDetail
)

data class CoopHistoryDetail(
  val __typename: String,
  val afterGrade: AfterGrade,           // reused from CoopResult file
  val afterGradePoint: Int,
  val boss: Boss,                        // reused
  val bossResult: BossResult,            // reused
  val bossResults: Any,
  val coopStage: CoopStage,             // reused
  val dangerRate: Double,
  val enemyResults: List<EnemyResult>,
  val id: String,
  val jobBonus: Int,
  val jobPoint: Int,
  val jobRate: Double,
  val jobScore: Int,
  val memberResults: List<MemberResultDetail>,
  val myResult: MyResultDetail,
  val nextHistoryDetail: NextHistoryDetail,   // reused
  val playedTime: String,
  val previousHistoryDetail: PreviousHistoryDetail, // reused
  val resultWave: Int,
  val rule: String,
  val scale: Scale,                     // reused
  val scenarioCode: Any,
  val smellMeter: Int,
  val waveResults: List<WaveResultDetail>,
  val weapons: List<WeaponDetail>
)

data class EnemyResult(
  val defeatCount: Int,
  val enemy: Enemy,
  val popCount: Int,
  val teamDefeatCount: Int
)

// Renamed from MemberResult to avoid clash with the slim MemberResult in CoopResult file
data class MemberResultDetail(
  val defeatEnemyCount: Int,
  val deliverCount: Int,
  val goldenAssistCount: Int,
  val goldenDeliverCount: Int,
  val player: Player,
  val rescueCount: Int,
  val rescuedCount: Int,
  val specialWeapon: SpecialWeapon,
  val weapons: List<WeaponDetail>
)

// Renamed from MyResult to avoid clash
data class MyResultDetail(
  val defeatEnemyCount: Int,
  val deliverCount: Int,
  val goldenAssistCount: Int,
  val goldenDeliverCount: Int,
  val player: Player,
  val rescueCount: Int,
  val rescuedCount: Int,
  val specialWeapon: SpecialWeapon,
  val weapons: List<WeaponDetail>
)

// Renamed from WaveResult to avoid clash
data class WaveResultDetail(
  val deliverNorm: Int,
  val eventWave: EventWave?,
  val goldenPopCount: Int,
  val specialWeapons: List<SpecialWeaponDetail>,
  val teamDeliverCount: Int,
  val waterLevel: Int,
  val waveNumber: Int
)

data class WeaponDetail(
  val image: Image,
  val name: String
)

data class Enemy(
  val id: String,
  val image: Image,
  val name: String
)

data class Player(
  val __isPlayer: String,
  val byname: String,
  val id: String,
  val name: String,
  val nameId: String,
  val nameplate: Nameplate,
  val species: String,
  val uniform: Uniform
)

data class SpecialWeapon(
  val image: Image,
  val name: String,
  val weaponId: Int
)

data class Nameplate(
  val background: Background,
  val badges: List<Badge>
)

data class Uniform(
  val id: String,
  val image: Image,
  val name: String
)

data class Background(
  val id: String,
  val image: Image,
  val textColor: TextColor
)

data class Badge(
  val id: String,
  val image: Image
)

data class TextColor(
  val a: Number,
  val b: Number,
  val g: Number,
  val r: Number
)

data class EventWave(
  val id: String,
  val name: String
)

data class SpecialWeaponDetail(
  val id: String,
  val image: Image,
  val name: String
)