package com.gpigoso.bebaagua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.gpigoso.bebaagua.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var txt_quilos: EditText
    private lateinit var txt_idade: EditText
    private lateinit var bt_calcular: AppCompatButton
    private lateinit var txt_resultado_ml: TextView
    private lateinit var btn_refresh: ImageView

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultadoMl = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        bt_calcular.setOnClickListener{

            if (txt_quilos.text.toString() .isEmpty()){
                Toast.makeText(this, R.string.toast_informe_peso,Toast.LENGTH_SHORT).show()
            }else if (txt_idade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade,Toast.LENGTH_SHORT).show()
            }else{
                val peso = txt_quilos.text.toString().toDouble()
                val idade = txt_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalMl(peso, idade)
                resultadoMl = calcularIngestaoDiaria.ResultadoMl()
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false
                txt_resultado_ml.text = formatar.format(resultadoMl) +" " + "ml"
            }
        }
        btn_refresh.setOnClickListener{

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_tittle)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("OK", {dialogInterface, i ->
                    txt_quilos.setText("")
                    txt_idade.setText("")
                    txt_resultado_ml.text = ""
                })
            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i ->
            })
            val dialog = alertDialog.create()
            dialog.show()
        }

    }
    private fun IniciarComponentes(){
        txt_quilos = findViewById(R.id.txt_quilos)
        txt_idade = findViewById(R.id.txt_idade)
        bt_calcular = findViewById(R.id.bt_calcular)
        txt_resultado_ml = findViewById(R.id.txt_resultado_ml)
        btn_refresh = findViewById(R.id.btn_refresh)
    }
}