package com.gpigoso.bebaagua.model

class CalcularIngestaoDiaria {

    private var ML_JOVEM = 40.0
    private var ML_ADULTO = 35.0
    private var ML_IDOSO = 30.0
    private var ML_MAIS_DE_66 = 25.0

    private var resultadoML = 0.0
    private var resultado_total_ml = 0.0

    fun CalcularTotalMl(peso: Double, idade: Int){

        if(idade <= 17){
            resultadoML = peso * ML_JOVEM
            resultado_total_ml = resultadoML
        }else if(idade <= 55) {
            resultadoML = peso * ML_ADULTO
            resultado_total_ml = resultadoML
        }else if(idade <= 66) {
            resultadoML = peso * ML_IDOSO
            resultado_total_ml = resultadoML
        }else {
            resultadoML = peso * ML_MAIS_DE_66
            resultado_total_ml = resultadoML
        }
    }
    
    fun ResultadoMl (): Double{
        return resultado_total_ml
    }

}