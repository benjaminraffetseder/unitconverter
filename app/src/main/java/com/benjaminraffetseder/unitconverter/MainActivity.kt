package com.benjaminraffetseder.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.benjaminraffetseder.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter(){

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }

    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    val inputConversionFactor = remember { mutableDoubleStateOf(1.00) }
    val outputConversionFactor = remember { mutableDoubleStateOf(1.00) }

    fun convertUnits () {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * inputConversionFactor.doubleValue * 100.0 / outputConversionFactor.doubleValue).roundToInt() / 100.0
        outputValue = result.toString()
    }

    fun handleDropdownItemClick(unit: String, isInput: Boolean) {
        if (isInput) {
            iExpanded = false
            inputUnit = unit

            when (inputUnit) {
                "Millimeters" -> inputConversionFactor.doubleValue = 0.001
                "Centimeters" -> inputConversionFactor.doubleValue = 0.01
                "Meters" -> inputConversionFactor.doubleValue = 1.0
                "Feet" -> inputConversionFactor.doubleValue = 0.3048
            }

        } else {
            oExpanded = false
            outputUnit = unit

            when (outputUnit) {
                "Millimeters" -> outputConversionFactor.doubleValue = 0.001
                "Centimeters" -> outputConversionFactor.doubleValue = 0.01
                "Meters" -> outputConversionFactor.doubleValue = 1.0
                "Feet" -> outputConversionFactor.doubleValue = 0.3048
            }

        }
        convertUnits()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Unit Converter", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter value") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = { handleDropdownItemClick("Millimeters", true) })
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = { handleDropdownItemClick("Centimeters", true) })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = { handleDropdownItemClick("Meters", true) })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = { handleDropdownItemClick("Feet", true)})
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { oExpanded = true}) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = { handleDropdownItemClick("Millimeters", false)})
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = { handleDropdownItemClick("Centimeters", false)})
                    DropdownMenuItem(text = { Text("Meters") }, onClick = { handleDropdownItemClick("Meters", false)})
                    DropdownMenuItem(text = { Text("Feet") }, onClick = { handleDropdownItemClick("Feet", false)})
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (inputValue.isNotEmpty()) {
            Text(text = "Result: $outputValue $outputUnit", style = MaterialTheme.typography.bodyLarge)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}
