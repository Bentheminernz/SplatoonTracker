package nz.benlawrence.splatoontracker.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class SplatoonWidgetReceiver : GlanceAppWidgetReceiver() {
  override val glanceAppWidget = SplatoonWidget()
}