package nz.benlawrence.splatoontracker.utils

import nz.benlawrence.splatoontracker.BuildConfig

fun String.toTitleCase() = split(" ").joinToString(" ") {
  it.lowercase().replaceFirstChar { c -> c.titlecase() }
}

inline fun debugOnly(block: () -> Unit) {
  if (BuildConfig.DEBUG) {
    block()
  }
}