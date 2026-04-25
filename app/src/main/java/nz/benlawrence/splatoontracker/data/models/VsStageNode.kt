package nz.benlawrence.splatoontracker.data.models

data class VsStageNode(
    val id: String,
    val name: String,
    val originalImage: OriginalImage,
    val stats: Any,
    val vsStageId: Int
)