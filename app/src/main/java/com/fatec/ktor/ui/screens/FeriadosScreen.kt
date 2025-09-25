package com.fatec.ktor.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fatec.ktor.ui.components.FeriadoItem

@Composable
fun FeriadosScreen(
    viewModel: FeriadosViewModel,
    modifier: Modifier = Modifier
) {
    var ano by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = ano,
            onValueChange = { newValue ->
                if (newValue.length <= 4 && newValue.all { it.isDigit() }) {
                    ano = newValue
                    localError = null
                }
            },
            label = {
                Text("Digite o ano (1900-2199)")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = localError != null
        )

        localError?.let { messageString ->
            Text(
                text = messageString,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val anoInt = ano.toIntOrNull()
                if(anoInt == null || anoInt < 1900|| anoInt >2199){
                    localError = "Digite um ano válido entre 1900 e 2199"

                }else{
                    localError = null
                    viewModel.getFeriados(ano)
                }
            }
        ) {
            Text("Buscar Feriados")
        }
        Spacer(modifier = Modifier.height(8.dp))

        when(val state = uiState){
            is FeriadosScreenState.Idle->{}
            is FeriadosScreenState.Loading->{
                CircularProgressIndicator()
            }
            is FeriadosScreenState.Success->{
                LazyColumn {
                    items (state.feriados) { feriado ->
                        FeriadoItem(feriado = feriado)
                    }
                }
            }
            is FeriadosScreenState.Error->{
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}