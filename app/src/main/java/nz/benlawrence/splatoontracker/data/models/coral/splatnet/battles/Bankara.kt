package nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles

import kotlinx.serialization.Serializable
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Image
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.MaskingImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsMode
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsRule
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsStage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Weapon

data class BankaraBattleHistoriesRequest(
    val battleDetailRequest: BattleDetailRequest
) {
    data class BattleDetailRequest(
        val historyId: String
    )
}

@Serializable
data class BankaraBattleHistoriesResponse(
    val bankaraBattleHistories: BankaraBattleHistories
)

data class BankaraBattleHistories(
    val historyGroups: HistoryGroups,
    val historyGroupsOnlyFirst: HistoryGroupsOnlyFirst,
    val summary: Summary
) {
    data class Summary(
        val assistAverage: Double,
        val deathAverage: Double,
        val killAverage: Double,
        val lose: Int,
        val perUnitTimeMinute: Int,
        val specialAverage: Double,
        val win: Int
    )
}

data class HistoryGroups(
    val nodes: List<Node>
) {
    data class Node(
        val bankaraMatchChallenge: BankaraMatchChallenge,
        val historyDetails: HistoryDetails
    ) {
        data class BankaraMatchChallenge(
            val earnedUdemaePoint: Int,
            val isPromo: Boolean,
            val isUdemaeUp: Boolean,
            val loseCount: Int,
            val maxLoseCount: Int,
            val maxWinCount: Int,
            val state: String,
            val udemaeAfter: String,
            val winCount: Int
        )

        data class HistoryDetails(
            val nodes: List<Node>
        ) {
            data class Node(
                val bankaraMatch: BankaraMatch,
                val id: String,
                val judgement: String,
                val knockout: String,
                val myTeam: MyTeam,
                val nextHistoryDetail: NextHistoryDetail,
                val player: Player,
                val previousHistoryDetail: PreviousHistoryDetail,
                val udemae: String,
                val vsMode: VsMode,
                val vsRule: VsRule,
                val vsStage: VsStage
            ) {
                data class BankaraMatch(val earnedUdemaePoint: Int)
                data class MyTeam(val result: Result) {
                    data class Result(val paintPoint: Any, val score: Int)
                }
                data class NextHistoryDetail(val id: String)
                data class PreviousHistoryDetail(val id: String)
                data class Player(val id: String, val weapon: Weapon)
            }
        }
    }
}

data class HistoryGroupsOnlyFirst(
    val nodes: List<Node>
) {
    data class Node(
        val historyDetails: HistoryDetails
    ) {
        data class HistoryDetails(
            val nodes: List<Node>
        ) {
            data class Node(
                val id: String,
                val player: Player
            ) {
                data class Player(val id: String, val weapon: Weapon) {
                    data class Weapon(val id: String, val specialWeapon: SpecialWeapon) {
                        data class SpecialWeapon(val id: String, val maskingImage: MaskingImage)
                    }
                }
            }
        }
    }
}