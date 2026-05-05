package com.example.saltsdecavall.ui.screens

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ChessLight = Color(0xFFEECF9D)
private val ChessDark = Color(0xFFB58863)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla1(viewModel: PaginViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Salts de Cavall") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "♞",
                fontSize = 72.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Configura la partida",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.midaTauler,
                        onValueChange = viewModel::onMidaTaulerChange,
                        label = { Text("Mida del tauler") },
                        placeholder = { Text("ex: 8") },
                        isError = viewModel.errorMidaTauler != null,
                        supportingText = viewModel.errorMidaTauler?.let { { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = viewModel.numMaxResolucions,
                        onValueChange = viewModel::onNumMaxResolucionsChange,
                        label = { Text("Núm. màxim de resolucions") },
                        placeholder = { Text("ex: 3") },
                        isError = viewModel.errorNumMaxResolucions != null,
                        supportingText = viewModel.errorNumMaxResolucions?.let { { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = viewModel.coordXCavall,
                        onValueChange = viewModel::onCoordXChange,
                        label = { Text("Coordenada X del cavall (columna)") },
                        placeholder = { Text("ex: 0") },
                        isError = viewModel.errorCoordX != null,
                        supportingText = viewModel.errorCoordX?.let { { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = viewModel.coordYCavall,
                        onValueChange = viewModel::onCoordYChange,
                        label = { Text("Coordenada Y del cavall (fila)") },
                        placeholder = { Text("ex: 0") },
                        isError = viewModel.errorCoordY != null,
                        supportingText = viewModel.errorCoordY?.let { { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = viewModel::iniciarJuego,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Resoldre  ♞", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun Pantalla2(viewModel: PaginViewModel) {
    val infiniteTransition = rememberInfiniteTransition(label = "knight_bounce")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -24f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "♞",
                fontSize = 80.sp,
                modifier = Modifier.offset(y = offsetY.dp)
            )
            CircularProgressIndicator(modifier = Modifier.size(40.dp))
            Text(
                text = "Resolent el problema...",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Solucions trobades: ${viewModel.solucionsEncontrades} / ${viewModel.maxSolucionsActual}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "En queden: ${viewModel.maxSolucionsActual - viewModel.solucionsEncontrades}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Això pot trigar uns moments...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla3(viewModel: PaginViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resultats") },
                navigationIcon = {
                    TextButton(onClick = viewModel::tornarAlFormulari) {
                        Text("← Tornar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (viewModel.hiHaSolucio == true)
                        "S'han trobat ${viewModel.numSolucions} solucions"
                    else
                        "No s'ha trobat cap solució",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (viewModel.hiHaSolucio == true)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            if (viewModel.solucions.isNotEmpty()) {
                item {
                    ExposedDropdownMenuBox(
                        expanded = viewModel.dropdownExpanded,
                        onExpandedChange = viewModel::onDropdownExpanded,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = "Solució ${viewModel.solucioSeleccionadaIndex + 1}",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Selecciona una solució") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(viewModel.dropdownExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = viewModel.dropdownExpanded,
                            onDismissRequest = { viewModel.onDropdownExpanded(false) }
                        ) {
                            viewModel.solucions.forEachIndexed { index, _ ->
                                DropdownMenuItem(
                                    text = { Text("Solució ${index + 1}") },
                                    onClick = { viewModel.onSolucioSelected(index) }
                                )
                            }
                        }
                    }
                }

                item {
                    TaulerVisual(
                        board = viewModel.mostrarTaulerSeleccionat() ?: emptyArray()
                    )
                }
            } else {
                item {
                    Text(
                        text = "Cap solució trobada per als paràmetres donats.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 24.dp)
                    )
                }
            }

            item {
                Button(
                    onClick = viewModel::tornarAlFormulari,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Tornar al formulari")
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TaulerVisual(board: Array<IntArray>) {
    val n = board.size
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val onPrimaryContainer = MaterialTheme.colorScheme.onPrimaryContainer
    val onSurface = MaterialTheme.colorScheme.onSurface

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(8.dp)) {
            for (fila in 0 until n) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until n) {
                        val moviment = board[fila][col]
                        val esInici = moviment == 1
                        val colorFons = when {
                            esInici -> primaryContainer
                            (fila + col) % 2 == 0 -> ChessLight
                            else -> ChessDark
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .background(colorFons)
                        ) {
                            Text(
                                text = if (esInici) "♞" else moviment.toString(),
                                fontSize = if (n <= 6) 14.sp else if (n <= 8) 11.sp else 8.sp,
                                fontWeight = if (esInici) FontWeight.Bold else FontWeight.Normal,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
