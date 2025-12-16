package com.tuempresa.controlparental

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class AccessibilityMonitor : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return
            // Aquí registramos el inicio de app
            Toast.makeText(this, "App abierta: $packageName", Toast.LENGTH_SHORT).show()
            // Aquí podrías disparar notificación al tutor
        }
    }

    override fun onInterrupt() {}
}
