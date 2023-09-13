package br.com.povengenharia.sortebr

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.Random
import android.widget.Toast


class MainActivity : Activity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.et_number)
        val txtResult: TextView = findViewById(R.id.tv_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result",null)

        if (result != null){
            txtResult.text = "Seus números da sorte são: $result"

        }

        btnGenerate.setOnClickListener {

            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        val qtd = text.toIntOrNull()

        if (text.isEmpty() || qtd == null || qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }
        txtResult.text = numbers.sorted().joinToString("-")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

    }
}
