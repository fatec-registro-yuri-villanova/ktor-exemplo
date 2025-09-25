package com.fatec.ktor.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.ktor.network.ApiResult
import com.fatec.ktor.repository.FeriadoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeriadosViewModel : ViewModel() {

    private val repository = FeriadoRepository()

    private val _uiState = MutableStateFlow<FeriadosScreenState>(FeriadosScreenState.Idle)

    val uiState:StateFlow<FeriadosScreenState> = _uiState

    fun getFeriados(ano: String) {
        viewModelScope.launch {
            _uiState.value = FeriadosScreenState.Loading
            when (val result = repository.getFeriados(ano)) {
                is ApiResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        _uiState.value = FeriadosScreenState.Success(result.data)
                    } else {
                        _uiState.value = FeriadosScreenState.Error("Nenhum feriado encontrado para o ano informado.")
                    }
                }
                is ApiResult.Error.HttpError -> {
                    val errorMessage = when (result.errorBody?.type) {
                        "feriados_range_error" -> result.errorBody.message
                        "feriados_error" -> result.errorBody.message
                        else -> "Erro na comunicação com o servidor (HTTP: ${result.code})"
                    }
                    _uiState.value = FeriadosScreenState.Error(errorMessage ?: "Erro desconhecido na resposta da API.")
                }
                is ApiResult.Error.NetworkError -> {
                    _uiState.value = FeriadosScreenState.Error("Erro de conexão. Verifique sua internet.")
                }
                is ApiResult.Error.UnknownError -> {
                    _uiState.value = FeriadosScreenState.Error("Ocorreu um erro inesperado.")
                }
            }
        }
    }


}