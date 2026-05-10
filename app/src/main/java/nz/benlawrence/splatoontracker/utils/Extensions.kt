package nz.benlawrence.splatoontracker.utils

fun String.toTitleCase() = split(" ").joinToString(" ") {
  it.lowercase().replaceFirstChar { c -> c.titlecase() }
}