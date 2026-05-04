package com.example.saltsdecavall.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saltsdecavall.component.Joc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class Pantalla { FORMULARI, RESOLENT, RESULTATS }

class PaginViewModel : ViewModel() {

    var midaTauler by mutableStateOf("")
        private set
    var numMaxResolucions by mutableStateOf("")
        private set
    var coordXCavall by mutableStateOf("")
        private set
    var coordYCavall by mutableStateOf("")
        private set

    var errorMidaTauler by mutableStateOf<String?>(null)
        private set
    var errorNumMaxResolucions by mutableStateOf<String?>(null)
        private set
    var errorCoordX by mutableStateOf<String?>(null)
        private set
    var errorCoordY by mutableStateOf<String?>(null)
        private set

    var pantallaActual by mutableStateOf(Pantalla.FORMULARI)
        private set

    var solucions by mutableStateOf<List<Array<IntArray>>>(emptyList())
        private set
    var hiHaSolucio by mutableStateOf<Boolean?>(null)  // null = encara no s'ha resolt
        private set
    var solucioSeleccionadaIndex by mutableStateOf(0)
        private set
    var dropdownExpanded by mutableStateOf(false)
        private set

    fun onMidaTaulerChange(v: String) {
        midaTauler = v; errorMidaTauler = null
    }

    fun onNumMaxResolucionsChange(v: String) {
        numMaxResolucions = v; errorNumMaxResolucions = null
    }

    fun onCoordXChange(v: String) {
        coordXCavall = v; errorCoordX = null
    }

    fun onCoordYChange(v: String) {
        coordYCavall = v; errorCoordY = null
    }

    fun onDropdownExpanded(exp: Boolean) {
        dropdownExpanded = exp
    }

    fun onSolucioSelected(i: Int) {
        solucioSeleccionadaIndex = i; dropdownExpanded = false
    }

    fun iniciarJuego() {
        val mida = midaTauler.toIntOrNull()
        val maxRes = numMaxResolucions.toIntOrNull()
        val coordX = coordXCavall.toIntOrNull()
        val coordY = coordYCavall.toIntOrNull()
        var valid = true

        when {
            mida == null || mida < 5 -> { errorMidaTauler = "Ha de ser un enter ≥ 5"; valid = false }
            maxRes == null || maxRes < 1 -> { errorNumMaxResolucions = "Ha de ser un enter ≥ 1"; valid = false }
            coordX == null || (coordX < 0 || coordX >= mida) -> { errorCoordX = "Ha d'estar entre 0 i ${mida - 1}"; valid = false }
            coordY == null || (coordY < 0 || coordY >= mida) -> { errorCoordY = "Ha d'estar entre 0 i ${mida - 1}"; valid = false }
        }
        if (!valid) return

        pantallaActual = Pantalla.RESOLENT
        viewModelScope.launch(Dispatchers.Default) {
            val joc = Joc(mida!!, maxRes!!, coordX!!, coordY!!)
            joc.resolver(1)
            withContext(Dispatchers.Main) {
                solucions = joc.solucions
                hiHaSolucio = joc.teSolucio()
                solucioSeleccionadaIndex = 0
                pantallaActual = Pantalla.RESULTATS
            }
        }
    }

    fun tornarAlFormulari() {
        pantallaActual = Pantalla.FORMULARI
        solucions = emptyList()
        hiHaSolucio = null
        solucioSeleccionadaIndex = 0
    }
}
