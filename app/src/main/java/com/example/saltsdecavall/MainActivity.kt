package com.example.saltsdecavall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.saltsdecavall.ui.screens.Pantalla
import com.example.saltsdecavall.ui.screens.Pantalla1
import com.example.saltsdecavall.ui.screens.Pantalla2
import com.example.saltsdecavall.ui.screens.Pantalla3
import com.example.saltsdecavall.ui.screens.PaginViewModel
import com.example.saltsdecavall.ui.theme.SaltsDeCavallTheme

class MainActivity : ComponentActivity() {

    private val viewModel: PaginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SaltsDeCavallTheme {
                when (viewModel.pantallaActual) {
                    Pantalla.FORMULARI -> Pantalla1(viewModel)
                    Pantalla.RESOLENT  -> Pantalla2(viewModel)
                    Pantalla.RESULTATS -> Pantalla3(viewModel)
                }
            }
        }
    }
}
