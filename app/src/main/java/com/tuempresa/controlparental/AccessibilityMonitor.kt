package com.tuempresa.controlparental

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import androidx.core.app.NotificationCompat
import android.content.Context

class AccessibilityMonitor : AccessibilityService() {

    private val CHANNEL_ID = "ControlParentalNotificaciones"

    override fun onServiceConnected() {
        super.onServiceConnected()
        createNotificationChannel()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return

            // Registrar app abierta
            logApp(packageName)

            // Notificar al tutor
            sendNotification(packageName)

            // Revisar tiempo de uso y alertar si se excede
            checkUsageLimit(packageName)

            // Bloquear solo si es app restringida
            if (isBlockedApp(packageName)) {
                blockApp()
            }
        }
    }

    override fun onInterrupt() {}

    private fun logApp(packageName: String) {
        val prefs = getSharedPreferences("monitor", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(packageName, System.currentTimeMillis())
        editor.apply()
    }

    private fun sendNotification(packageName: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Control Parental")
            .setContentText("Se inició la app: $packageName")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(packageName.hashCode(), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Control Parental Notificaciones",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun isBlockedApp(packageName: String): Boolean {
        val blocked = listOf("com.tuempresa.airplanegame")
        return blocked.contains(packageName)
    }

    private fun blockApp() {
        performGlobalAction(GLOBAL_ACTION_HOME)
    }

    // --- ALERTAS DE TIEMPO DE USO ---
    private fun checkUsageLimit(packageName: String) {
        val prefs = getSharedPreferences("monitor", Context.MODE_PRIVATE)
        val startTime = prefs.getLong(packageName, System.currentTimeMillis())
        val elapsed = System.currentTimeMillis() - startTime

        // Limite ejemplo: 10 min
        val limitMillis = 10 * 60 * 1000
        if (elapsed >= limitMillis) {
            sendUsageAlert(packageName, elapsed)
        }
    }

    private fun sendUsageAlert(packageName: String, elapsed: Long) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Control Parental")
            .setContentText("La app $packageName ha sido usada ${elapsed/60000} minutos")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(packageName.hashCode() + 999, notification)
    }
}
