package com.fatec.ktor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.ktor.data.model.Feriado
import com.fatec.ktor.ui.theme.KtorTheme

@Composable
fun FeriadoItem(
    modifier: Modifier = Modifier,
    feriado: Feriado
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Data: ${feriado.date}")
            Text("Nome: ${feriado.name}")
            Text("Tipo: ${feriado.type}")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeriadoItemPreview() {
    KtorTheme {
        val feriadoPreview = Feriado(
            date = "2025-01-01",
            name = "Ano Novo",
            type = "Feriado Nacional"
        )
        FeriadoItem(feriado = feriadoPreview)
    }
}