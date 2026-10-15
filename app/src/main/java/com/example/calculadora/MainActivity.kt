package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var pantalla by remember { mutableStateOf("0") }
            var numeroAnterior by remember { mutableStateOf(0.0) }
            var operacion by remember { mutableStateOf("") }
            var nuevoNumero by remember { mutableStateOf(true) }

            fun pulsarNumero(numero: String) {

                if (nuevoNumero || pantalla == "Error") {
                    pantalla = numero
                    nuevoNumero = false
                } else {
                    if (pantalla == "0") {
                        pantalla = numero
                    } else {
                        pantalla += numero
                    }
                }
            }

            fun pulsarOperacion(simbolo: String) {

                if (simbolo == "C") {

                    pantalla = "0"
                    numeroAnterior = 0.0
                    operacion = ""
                    nuevoNumero = true

                } else if (simbolo == "=") {

                    val numeroActual = pantalla.toDoubleOrNull()

                    if (numeroActual != null && operacion != "") {

                        var resultado = 0.0

                        when (operacion) {
                            "+" -> resultado =
                                numeroAnterior + numeroActual

                            "-" -> resultado =
                                numeroAnterior - numeroActual

                            "*" -> resultado =
                                numeroAnterior * numeroActual

                            "/" -> {
                                if (numeroActual == 0.0) {
                                    pantalla = "Error"
                                    operacion = ""
                                    nuevoNumero = true
                                    return
                                } else {
                                    resultado =
                                        numeroAnterior / numeroActual
                                }
                            }
                        }

                        pantalla = if (
                            resultado == resultado.toLong().toDouble()
                        ) {
                            resultado.toLong().toString()
                        } else {
                            resultado.toString()
                        }

                        numeroAnterior = resultado
                        operacion = ""
                        nuevoNumero = true
                    }

                } else {

                    val numeroActual = pantalla.toDoubleOrNull()

                    if (numeroActual != null) {

                        if (operacion != "" && !nuevoNumero) {

                            when (operacion) {
                                "+" -> numeroAnterior += numeroActual
                                "-" -> numeroAnterior -= numeroActual
                                "*" -> numeroAnterior *= numeroActual

                                "/" -> {
                                    if (numeroActual == 0.0) {
                                        pantalla = "Error"
                                        operacion = ""
                                        nuevoNumero = true
                                        return
                                    } else {
                                        numeroAnterior /= numeroActual
                                    }
                                }
                            }

                        } else if (operacion == "") {
                            numeroAnterior = numeroActual
                        }

                        pantalla = if (
                            numeroAnterior ==
                            numeroAnterior.toLong().toDouble()
                        ) {
                            numeroAnterior.toLong().toString()
                        } else {
                            numeroAnterior.toString()
                        }

                        operacion = simbolo
                        nuevoNumero = true
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pantalla,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 40.sp,
                    textAlign = TextAlign.End
                )

                Spacer(modifier = Modifier.height(20.dp))


                Row(modifier = Modifier.fillMaxWidth()) {

                    BotonCalculadora("7", Modifier.weight(1f)) {
                        pulsarNumero("7")
                    }

                    BotonCalculadora("8", Modifier.weight(1f)) {
                        pulsarNumero("8")
                    }

                    BotonCalculadora("9", Modifier.weight(1f)) {
                        pulsarNumero("9")
                    }

                    BotonCalculadora("/", Modifier.weight(1f)) {
                        pulsarOperacion("/")
                    }
                }

                // Segunda fila: 4, 5, 6 y multiplicación
                Row(modifier = Modifier.fillMaxWidth()) {

                    BotonCalculadora("4", Modifier.weight(1f)) {
                        pulsarNumero("4")
                    }

                    BotonCalculadora("5", Modifier.weight(1f)) {
                        pulsarNumero("5")
                    }

                    BotonCalculadora("6", Modifier.weight(1f)) {
                        pulsarNumero("6")
                    }

                    BotonCalculadora("*", Modifier.weight(1f)) {
                        pulsarOperacion("*")
                    }
                }

                // Tercera fila: 1, 2, 3 y resta
                Row(modifier = Modifier.fillMaxWidth()) {

                    BotonCalculadora("1", Modifier.weight(1f)) {
                        pulsarNumero("1")
                    }

                    BotonCalculadora("2", Modifier.weight(1f)) {
                        pulsarNumero("2")
                    }

                    BotonCalculadora("3", Modifier.weight(1f)) {
                        pulsarNumero("3")
                    }

                    BotonCalculadora("-", Modifier.weight(1f)) {
                        pulsarOperacion("-")
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()) {

                    BotonCalculadora("C", Modifier.weight(1f)) {
                        pulsarOperacion("C")
                    }

                    BotonCalculadora("0", Modifier.weight(1f)) {
                        pulsarNumero("0")
                    }

                    BotonCalculadora("=", Modifier.weight(1f)) {
                        pulsarOperacion("=")
                    }

                    BotonCalculadora("+", Modifier.weight(1f)) {
                        pulsarOperacion("+")
                    }
                }
            }
        }
    }
}


@androidx.compose.runtime.Composable
fun BotonCalculadora(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
        containerColor = Color.Magenta,
        contentColor = Color.White
    )
    ) {
        Text(
            text = texto,
            fontSize = 36.sp
        )
    }
}
