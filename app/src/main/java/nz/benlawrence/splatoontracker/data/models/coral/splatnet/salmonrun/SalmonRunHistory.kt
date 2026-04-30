package nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun

import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Image
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Weapon

data class CoopResultResponse(
    val coopHistory: CoopResultWrapper
)

data class CoopResultWrapper(
    val coopResult: CoopResult
)

data class CoopResult(
    val historyGroups: HistoryGroups,
    val historyGroupsOnlyFirst: HistoryGroupsOnlyFirst,
    val monthlyGear: MonthlyGear,
    val pointCard: PointCard,
    val regularAverageClearWave: Double,
    val regularGrade: RegularGrade,
    val regularGradePoint: Int,
    val scale: Scale
)

data class HistoryGroups(
    val nodes: List<HistoryGroupNode>
)

data class HistoryGroupsOnlyFirst(
    val nodes: List<HistoryGroupFirstNode>
)

data class MonthlyGear(
    val __typename: String,
    val image: Image,
    val name: String
)

data class PointCard(
    val defeatBossCount: Int,
    val deliverCount: Int,
    val goldenDeliverCount: Int,
    val limitedPoint: Any,
    val playCount: Int,
    val regularPoint: Int,
    val rescueCount: Int,
    val totalPoint: Int
)

data class RegularGrade(
    val id: String,
    val name: String
)

data class Scale(
    val bronze: Int,
    val gold: Int,
    val silver: Int
)

data class HistoryGroupNode(
    val endTime: String,
    val highestResult: HighestResult,
    val historyDetails: HistoryDetails,
    val mode: String,
    val playCount: Int,
    val rule: String,
    val startTime: String
)

data class HighestResult(
    val grade: Grade,
    val gradePoint: Int,
    val jobScore: Int,
    val trophy: Any
)

data class HistoryDetails(
    val nodes: List<HistoryDetailNode>
)

data class Grade(
    val id: String,
    val name: String
)

data class HistoryDetailNode(
    val afterGrade: AfterGrade,
    val afterGradePoint: Int,
    val bossResult: BossResult,
    val coopStage: CoopStage,
    val dangerRate: Double,
    val gradePointDiff: String,
    val id: String,
    val memberResults: List<MemberResult>,
    val myResult: MyResult,
    val nextHistoryDetail: NextHistoryDetail,
    val previousHistoryDetail: PreviousHistoryDetail,
    val resultWave: Int,
    val waveResults: List<WaveResult>,
    val weapons: List<Weapon>
)

data class AfterGrade(
    val id: String,
    val name: String
)

data class BossResult(
    val boss: Boss,
    val hasDefeatBoss: Boolean
)

data class CoopStage(
    val id: String,
    val name: String
)

data class MemberResult(
    val deliverCount: Int
)

data class MyResult(
    val deliverCount: Int
)

data class NextHistoryDetail(
    val id: String
)

data class PreviousHistoryDetail(
    val id: String
)

data class WaveResult(
    val teamDeliverCount: Int
)

data class Boss(
    val id: String,
    val name: String
)

data class HistoryGroupFirstNode(
    val historyDetails: HistoryGroupFirstDetails
)

data class HistoryGroupFirstDetails(
    val nodes: List<HistoryGroupFirstDetailsNode>
)

data class HistoryGroupFirstDetailsNode(
    val __typename: String,
    val id: String
)