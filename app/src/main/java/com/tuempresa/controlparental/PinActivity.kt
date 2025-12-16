package com.tuempresa.controlparental

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PinActivity : AppCompatActivity() {

    private val PIN_CORRECTO = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        val pinEdit = findViewById<EditText>(R.id.pinEditText)
        val btnAceptar = findViewById<Button>(R.id.btnAceptar)

        btnAceptar.setOnClickListener {
            if (pinEdit.text.toString() == PIN_CORRECTO) {
                Toast.makeText(this, "Acceso permitido", Toast.LENGTH_SHORT).show()
                // Aquí abrir panel de monitoreo real
            } else {
                Toast.makeText(this, "PIN incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
