package nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles

import kotlinx.serialization.Serializable
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Gear
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Image
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.MaskingImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Nameplate
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.SubWeapon
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsMode
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsRule
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsStage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.PreviousHistoryDetail

@Serializable
data class VsBattleDetail(
    val battleDetail: BattleDetail
)

data class BattleDetail(
    val vsHistoryDetail: VsHistoryDetail
)

data class VsHistoryDetail(
    val __typename: String,
    val awards: List<Award>,
    val bankaraMatch: BankaraMatch,
    val duration: Int,
    val festMatch: Any,
    val id: String,
    val judgement: String,
    val knockout: String,
    val leagueMatch: Any,
    val myTeam: MyTeam,
    val nextHistoryDetail: Any,
    val otherTeams: List<OtherTeam>,
    val playedTime: String,
    val player: BattleDetailPlayer,
    val previousHistoryDetail: PreviousHistoryDetail,
    val vsMode: VsMode,
    val vsRule: VsRule,
    val vsStage: VsStage,
    val xMatch: Any
)

data class Award(
    val name: String,
    val rank: String
)

data class BankaraMatch(
    val bankaraPower: BankaraPower,
    val earnedUdemaePoint: Int,
    val mode: String,
    val weaponPower: Any
)

interface VsTeam {
    val order: Int
    val players: List<Player>
    val judgement: String
}

data class MyTeam(
    val color: Color,
    val festStreakWinCount: Any,
    val festTeamName: Any,
    val festUniformBonusRate: Any,
    val festUniformName: Any,
    override val judgement: String,
    override val order: Int,
    override val players: List<Player>,
    val result: ResultX,
    val tricolorRole: Any
) : VsTeam

data class OtherTeam(
    val color: Color,
    val festStreakWinCount: Any,
    val festTeamName: Any,
    val festUniformName: Any,
    override val judgement: String,
    override val order: Int,
    override val players: List<Player>,
    val result: ResultX,
    val tricolorRole: Any
) : VsTeam

data class BattleDetailPlayer(
    val __isPlayer: String,
    val byname: String,
    val clothingGear: Gear,
    val headGear: Gear,
    val id: String,
    val name: String,
    val nameId: String,
    val nameplate: Nameplate,
    val paint: Int,
    val shoesGear: Gear
)

data class BankaraPower(
    val power: Any
)

data class Color(
    val a: Int,
    val b: Double,
    val g: Double,
    val r: Double
)

data class Player(
    val __isPlayer: String,
    val byname: String,
    val callSign: Any,
    val clothingGear: Gear,
    val crown: Boolean,
    val festDragonCert: String,
    val headGear: Gear,
    val id: String,
    val isMyself: Boolean,
    val name: String,
    val nameId: String,
    val nameplate: Nameplate,
    val paint: Int,
    val result: Result?,
    val shoesGear: Gear,
    val species: String,
    val weapon: Weapon
)

data class ResultX(
    val noroshi: Any,
    val paintRatio: Any,
    val score: Int
)

data class Result(
    val assist: Int,
    val death: Int,
    val kill: Int,
    val noroshiTry: Any,
    val special: Int
)

// Kept local — richer shape than shared Weapon
data class Weapon(
    val id: String,
    val image: Image,
    val image2d: Image2d,
    val image2dThumbnail: Image2dThumbnail,
    val image3d: Image2d,
    val image3dThumbnail: Image2dThumbnail,
    val name: String,
    val specialWeapon: SpecialWeapon,
    val subWeapon: SubWeapon   // shared
)

data class AdditionalGearPower(
    val image: Image,
    val name: String
)

data class Brand(
    val id: String,
    val image: Image,
    val name: String,
    val usualGearPower: UsualGearPower
)

data class OriginalImage(
    val url: String
)

data class PrimaryGearPower(
    val image: Image,
    val name: String
)

data class UsualGearPower(
    val desc: String,
    val image: Image,
    val isEmptySlot: Boolean,
    val name: String
)

data class Image2d(
    val url: String
)

data class Image2dThumbnail(
    val url: String
)

data class SpecialWeapon(
    val id: String,
    val image: Image,
    val maskingImage: MaskingImage,
    val name: String
)