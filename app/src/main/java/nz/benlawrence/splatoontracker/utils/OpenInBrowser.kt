package nz.benlawrence.splatoontracker.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri

fun openInBrowser(context: Context, url: String) {
  val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  }
  try {
    context.startActivity(intent)
  } catch (e: Exception) {
    Toast.makeText(context, "No browser found to open link", Toast.LENGTH_SHORT).show()
  }
}