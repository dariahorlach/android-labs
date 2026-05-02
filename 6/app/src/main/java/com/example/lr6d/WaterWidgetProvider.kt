package com.example.lr6d

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.content.edit

class WaterWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_ADD_WATER = "com.example.lr6d.ADD_WATER"
        private const val PREFS_NAME = "WaterTrackerPrefs"
        private const val KEY_COUNT = "WATER_COUNT"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == ACTION_ADD_WATER) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            var count = prefs.getInt(KEY_COUNT, 0)
            count++
            prefs.edit { putInt(KEY_COUNT, count) }

            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, WaterWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)

            for (id in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, id)
            }
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val count = prefs.getInt(KEY_COUNT, 0)

        val views = RemoteViews(context.packageName, R.layout.widget_water_tracker)
        views.setTextViewText(R.id.tvWaterCount, "$count склянок")

        val intent = Intent(context, WaterWidgetProvider::class.java).apply {
            action = ACTION_ADD_WATER
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        views.setOnClickPendingIntent(R.id.btnAddWater, pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}