package nz.benlawrence.splatoontracker.data.models.coral

import kotlinx.serialization.Serializable

@Serializable
data class AuthURL(
  val url: String,
  val state: String,
  val verifier: String
)