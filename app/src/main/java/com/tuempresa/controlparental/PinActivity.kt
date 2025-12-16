package com.tuempresa.controlparental

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ArrayAdapter
import android.content.Context
import android.widget.Toast

class PinActivity : AppCompatActivity() {

    private var PIN_CORRECTO = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        val pinEdit = findViewById<EditText>(R.id.pinEditText)
        val btnAceptar = findViewById<Button>(R.id.btnAceptar)
        val listView = findViewById<ListView>(R.id.listView)

        btnAceptar.setOnClickListener {
            if (pinEdit.text.toString() == PIN_CORRECTO) {
                Toast.makeText(this, "Acceso permitido", Toast.LENGTH_SHORT).show()
                showHistory(listView)
            } else {
                Toast.makeText(this, "PIN incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showHistory(listView: ListView) {
        val prefs = getSharedPreferences("monitor", Context.MODE_PRIVATE)
        val allApps = prefs.all
        val displayList = allApps.map { (packageName, time) ->
            val elapsed = System.currentTimeMillis() - (time as Long)
            "$packageName - última vez abierta: ${elapsed/1000} seg atrás"
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
        listView.adapter = adapter
    }
}
