package nz.benlawrence.splatoontracker.data.models.coral.splatnet

typealias UnknownScalar = Any?

data class Image(
  val url: String,
  val width: Int,
  val height: Int,
  val __typename: String = "Image",
)

data class DownloadImage(
  val url: String,
  val variant: String,
)

data class MaskingImage(
  val width: Int,
  val height: Int,
  val maskImageUrl: String,
  val overlayImageUrl: String,
)

data class PageInfo(
  val endCursor: String,
  val hasNextPage: Boolean,
)

data class Edge<T>(
  val node: T,
  val cursor: String,
)

data class Connection<T>(
  val nodes: List<T>,
  val edges: List<Edge<T>>,
  val pageInfo: PageInfo,
  val totalCount: Int,
)

data class Weapon(
  val id: String,
  val name: String,
  val image: Image,
  val __typename: String = "Weapon",
)

data class SubWeapon(
  val id: String,
  val name: String,
  val image: Image,
  val __typename: String = "SubWeapon",
)

data class SpecialWeapon(
  val id: String,
  val name: String,
  val image: Image,
  val maskingImage: MaskingImage,
  val __typename: String = "SpecialWeapon",
)

data class WallpaperReward(
  val id: String,
  val name: String,
  val sampleImage: Image,
  val downloadImages: List<DownloadImage>,
)
