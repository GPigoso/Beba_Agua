package com.gpigoso.bebaagua

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.renderscript.ScriptGroup.Binding
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.gpigoso.bebaagua.databinding.ActivityMainBinding
import com.gpigoso.bebaagua.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var txt_quilos: EditText
    private lateinit var txt_idade: EditText
    private lateinit var bt_calcular: AppCompatButton
    private lateinit var txt_resultado_ml: TextView
    private lateinit var btn_refresh: ImageView
    private lateinit var button_lembrete: Button
    private lateinit var button_alarme: Button
    private lateinit var text_hora: TextView
    private lateinit var text_minuto: TextView

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultadoMl = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendar: Calendar
    var horaAtual = 0
    var minutosAtuais = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        //IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        binding.btCalcular.setOnClickListener{

            if (binding.txtQuilos.text.toString() .isEmpty()){
                Toast.makeText(this, R.string.toast_informe_peso,Toast.LENGTH_SHORT).show()
            }else if (binding.txtIdade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade,Toast.LENGTH_SHORT).show()
            }else{
                val peso = binding.txtQuilos.text.toString().toDouble()
                val idade = binding.txtIdade.text.toString().toInt()
                //val peso = txt_quilos.text.toString().toDouble()
                //val idade = txt_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalMl(peso, idade)
                resultadoMl = calcularIngestaoDiaria.ResultadoMl()
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false
                binding.txtResultadoMl.text = formatar.format(resultadoMl) +" " + "ml"
            }
        }
        binding.btnRefresh.setOnClickListener{

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_tittle)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("OK", {dialogInterface, i ->
                    binding.txtQuilos.setText("")
                    binding.txtIdade.setText("")
                    binding.txtResultadoMl.text = ""
                })
            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i ->
            })
            val dialog = alertDialog.create()
            dialog.show()
        }

        binding.buttonLembrete.setOnClickListener {

            calendar = Calendar.getInstance()
            horaAtual = calendar.get(Calendar.HOUR_OF_DAY)
            minutosAtuais = calendar.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, { timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.textHora.text = String.format("%02d", hourOfDay)
                binding.textMinuto.text = String.format("%02d", minutes)
            },horaAtual, minutosAtuais, true)
            timePickerDialog.show()
        }

        binding.buttonAlarme.setOnClickListener {

            if (!binding.textHora.text.toString().isEmpty() && !binding.textMinuto.text.toString().isEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, binding.textHora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, binding.textMinuto.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_mensagem))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
            }
        }

    }
    /*private fun IniciarComponentes(){
        txt_quilos = findViewById(R.id.txt_quilos)
        txt_idade = findViewById(R.id.txt_idade)
        bt_calcular = findViewById(R.id.bt_calcular)
        txt_resultado_ml = findViewById(R.id.txt_resultado_ml)
        btn_refresh = findViewById(R.id.btn_refresh)
        button_lembrete = findViewById(R.id.button_lembrete)
        button_alarme = findViewById(R.id.button_alarme)
        text_hora = findViewById(R.id.text_hora)
        text_minuto = findViewById(R.id.text_minuto)
    }*/
}