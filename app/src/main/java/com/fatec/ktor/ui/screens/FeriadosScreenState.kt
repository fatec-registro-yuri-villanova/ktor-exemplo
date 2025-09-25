package com.fatec.ktor.ui.screens

import com.fatec.ktor.data.model.Feriado

sealed class FeriadosScreenState{
    object Idle:FeriadosScreenState()
    object Loading:FeriadosScreenState()
    data class Success(val feriados:List<Feriado>): FeriadosScreenState()
    data class Error(val message:String): FeriadosScreenState()
}