package nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles

import kotlinx.serialization.Serializable
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.MaskingImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Summary
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
    val bankaraBattleHistories: VsBattleHistories
)

@Serializable
data class RegularBattleHistoriesResponse(
    val regularBattleHistory: VsBattleHistories
)

@Serializable
data class VsBattleHistories(
    val historyGroups: HistoryGroups,
    val historyGroupsOnlyFirst: HistoryGroupsOnlyFirst,
    val summary: Summary
)

@Serializable
data class HistoryGroups(
    val nodes: List<Node>
) {
    @Serializable
    data class Node(
        val bankaraMatchChallenge: BankaraMatchChallenge?,
        val historyDetails: HistoryDetails
    ) {
        @Serializable
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

        @Serializable
        data class HistoryDetails(
            val nodes: List<Node>
        ) {
            @Serializable
            data class Node(
                val bankaraMatch: BankaraMatch,
                val id: String,
                val judgement: String,
                val knockout: String?,
                val myTeam: MyTeam,
                val nextHistoryDetail: NextHistoryDetail?,
                val player: Player,
                val previousHistoryDetail: PreviousHistoryDetail?,
                val udemae: String?,
                val vsMode: VsMode,
                val vsRule: VsRule,
                val vsStage: VsStage
            ) {
                @Serializable
                data class BankaraMatch(val earnedUdemaePoint: Int?)

                @Serializable
                data class MyTeam(val result: Result?) {
                    @Serializable
                    data class Result(val paintPoint: Int?, val score: Int?)
                }

                @Serializable
                data class NextHistoryDetail(val id: String)

                @Serializable
                data class PreviousHistoryDetail(val id: String)

                @Serializable
                data class Player(val id: String, val weapon: Weapon)
            }
        }
    }
}

@Serializable
data class HistoryGroupsOnlyFirst(
    val nodes: List<Node>
) {
    @Serializable
    data class Node(
        val historyDetails: HistoryDetails
    ) {
        @Serializable
        data class HistoryDetails(
            val nodes: List<Node>
        ) {
            @Serializable
            data class Node(
                val id: String,
                val player: Player
            ) {
                @Serializable
                data class Player(val id: String, val weapon: FirstWeapon) {
                    @Serializable
                    data class FirstWeapon(val id: String, val specialWeapon: SpecialWeapon) {
                        @Serializable
                        data class SpecialWeapon(val id: String, val maskingImage: MaskingImage)
                    }
                }
            }
        }
    }
}