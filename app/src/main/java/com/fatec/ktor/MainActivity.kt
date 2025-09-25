package com.fatec.ktor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.fatec.ktor.ui.screens.FeriadosScreen
import com.fatec.ktor.ui.screens.FeriadosViewModel
import com.fatec.ktor.ui.theme.KtorTheme

class MainActivity : ComponentActivity() {
    private val viewModel: FeriadosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KtorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FeriadosScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

