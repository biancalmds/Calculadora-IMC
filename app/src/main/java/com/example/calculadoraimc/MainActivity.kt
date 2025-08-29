package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraIMCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculadoraIMCScreen()
                }
            }
        }
    }
}

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val poppins = GoogleFont("Poppins")

val fontFamily = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = poppins,
        fontProvider = provider
    )
)

@Composable
fun CalculadoraIMCScreen() {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFD7D5D5), Color(0xDC63834C))))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calculadora de IMC",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                modifier = Modifier.padding(bottom = 32.dp),
                color = Color(0xFF445E30)
            )
            OutlinedTextField(
               value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                leadingIcon = { Icon(Icons.Default.MonitorWeight, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLabelColor = Color(0xFF333030),
                    focusedLabelColor = Color(0xFF445E30),
                    focusedBorderColor =Color(0xFF445E30),
                    focusedTextColor = Color(0xFC000000),
                    unfocusedTextColor = Color(0xFC000000)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = altura,
                onValueChange = { altura = it },
                label = { Text("Altura (m)") },
                leadingIcon = {Icon(Icons.Default.EmojiPeople, contentDescription= null)},
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLabelColor = Color(0xFF333030),
                    focusedLabelColor = Color(0xFF445E30),
                    focusedBorderColor =Color(0xFF445E30),
                    focusedTextColor = Color(0xFC000000),
                    unfocusedTextColor = Color(0xFC000000)
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val pesoConvertido = peso.replace(',', '.').toFloatOrNull()
                    val alturaConvertida = altura.replace(',', '.').toFloatOrNull()

                    if (pesoConvertido == null || alturaConvertida == null) {
                        resultado = "Digite valores numéricos válidos!"
                    } else if (pesoConvertido <= 0 || alturaConvertida <= 0) {
                        resultado = "Os valores devem ser maiores que zero!"
                    } else {
                        val imc = pesoConvertido / (alturaConvertida * alturaConvertida)
                        resultado = "Seu IMC: %.2f\n%s".format(imc, classificarIMC(imc))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF445E30)
                )
            ) {
                Text(
                    text = "Calcular IMC",
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(10.dp)
                )

            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = resultado,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color(0xFF445E30)
            )
        }
    }
}

private fun classificarIMC(imc: Float): String {
    return when {
        imc < 18.5 -> "Abaixo do peso"
        imc < 24.9 -> "Peso normal"
        imc < 29.9 -> "Sobrepeso"
        imc < 34.9 -> "Obesidade Grau I"
        imc < 39.9 -> "Obesidade Grau II"
        else -> "Obesidade Grau III"
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculadoraIMC() {
    CalculadoraIMCTheme {
        CalculadoraIMCScreen()
    }
}
