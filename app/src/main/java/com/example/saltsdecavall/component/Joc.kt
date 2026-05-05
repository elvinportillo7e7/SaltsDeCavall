package com.example.saltsdecavall.component

//component principal que estableix la llogica del joc
class Joc(
    private var mida: Int,          // mida del taulell, sempre quadrat
    private var maxSolucions: Int,  // quantes solucions volem trobar abans de parar
    private var startX: Int,        // columna inicial del cavall
    private var startY: Int         // fila inicial del cavall
) {

    // Taulell de joc: taulell[fila][columna]. A cada cel·la se li asigna un numero en funcio de en quin moviment ha passat per aquesta
    // les que el cavall encara pot visitar s'asignen amb 0.
    var taulell: Array<IntArray> = emptyArray()

    // Llista on s'acumulen les solucions trobades (còpies del taulell complet)
    val solucions = mutableListOf<Array<IntArray>>()

   //aquestes dos variables de manera junta guarden els moviments que pot fer el cavall sent, fila i columna
    private val dx = intArrayOf( 2,  1, -1, -2, -2, -1,  1,  2)
    private val dy = intArrayOf( 1,  2,  2,  1, -1, -2, -2, -1)

    private var movIndex = IntArray(mida * mida + 1) { -1 }

    // px[nivel] i py[nivel]: columna i fila del cavall quan es troba al nivell 'nivel'.
    // Exemple: px[1]=startX, py[1]=startY és la posició inicial (pas 1).
    private var px = IntArray(mida * mida + 1)
    private var py = IntArray(mida * mida + 1)

    // Posició candidata que s'està avaluant en cada iteració del bucle.
    private var candidatX = 0
    private var candidatY = 0

    // Nivell actiu en cada moment del bucle (necessari per a hihaSeguentNodo i seguentNodo).
    private var nivelActual = 0

    init {
        if (mida > 0) initializeState()
    }

    constructor() : this(0, 0, 0, 0)

    private fun initializeState() {
        taulell = initTab(mida)
        taulell[startY][startX] = 1  // col·loquem el cavall a la posició inicial (pas 1)
        movIndex = IntArray(mida * mida + 1) { -1 }
        px = IntArray(mida * mida + 1)
        py = IntArray(mida * mida + 1)
        px[1] = startX
        py[1] = startY
    }

    // Inicialitza el joc amb els paràmetres donats. midaTauler s'accepta com a String.
    fun pedirInfo(midaTauler: String, numMaxResolucions: Int, CoordXCavall: Int, CoordYCavall: Int) {
        mida = midaTauler.toInt()
        maxSolucions = numMaxResolucions
        startX = CoordXCavall
        startY = CoordYCavall
        solucions.clear()
        candidatX = 0
        candidatY = 0
        nivelActual = 0
        initializeState()
    }

    // Retorna el número de solucions trobades fins ara.
    val numSolucions: Int get() = solucions.size

    // Retorna el taulell de la solució indicada (índex 1-basat), o null si no existeix.
    fun mostrarMenu(numeroSolucióABuscar: Int): Array<IntArray>? =
        solucions.getOrNull(numeroSolucióABuscar - 1)

    // Algorisme de backtracking
    // 'nivel' representa quants passos ja té el cavall sobre el taulell.
    // S'ha de cridar amb resolver(1) perquè el primer pas ja és al taulell.
    fun resolver(nivel: Int) {
        var nivel = nivel
        inicialitzarNivell(nivel)

        while (nivel > 0 && solucions.size < maxSolucions) {
            nivelActual = nivel
            if (hihaSeguentNodo()) {
                seguentNodo()               // calcula el següent candidat
                if (nodo_valid()) {
                    // Col·loquem el cavall: el nou pas és nivel+1
                    taulell[candidatY][candidatX] = nivel + 1
                    px[nivel + 1] = candidatX
                    py[nivel + 1] = candidatY

                    if (ultimNivell(taulell)) {
                        tractarSolucio(taulell)            // taulell complet → guardem
                        taulell[candidatY][candidatX] = 0  // desfem per seguir buscant
                    } else {
                        nivel++
                        inicialitzarNivell(nivel)
                    }
                }
            } else {
                // se han hecho tots els moviments en aquest nivell → backtrack
                if (nivel > 1) taulell[py[nivel]][px[nivel]] = 0  // deshacer nivel actual
                nivel--  // tornem al nivell anterior
            }
        }
    }

    // inicialitza el nivell 'nivel': posa movIndex a -1.
    private fun inicialitzarNivell(nivel: Int) {
        movIndex[nivel] = -1
    }

    // Crea i retorna un taulell de mida×mida inicialitzat a zeros.
    fun initTab(mida: Int): Array<IntArray> {
        return Array(mida) { IntArray(mida) }
    }

    // Retorna true si totes les cel·les del taulell han estat visitades (valor ≥ 1).
    private fun ultimNivell(taulell: Array<IntArray>): Boolean {
        return taulell.all { fila -> fila.all { cel -> cel >= 1 } }
    }

    // Retorna true si hi ha solució.
    fun teSolucio(): Boolean = solucions.isNotEmpty()

    // retorna true si encara queden moviments per provar en el nivell actual
    // (els moviments es numeren de 0 a 7).
    fun hihaSeguentNodo(): Boolean = movIndex[nivelActual] < 7

    // Avança al següent moviment candidat del nivell actual (ordre fix 0–7)
    // i calcula la posició resultant (candidatX, candidatY).
    fun seguentNodo() {
        movIndex[nivelActual]++
        candidatX = px[nivelActual] + dx[movIndex[nivelActual]]
        candidatY = py[nivelActual] + dy[movIndex[nivelActual]]
    }

    // Un candidat es valid si:
    //    la posició (candidatX, candidatY) es dins del taulell.
    //   la cel·la no ha estat visitada encara (valor == 0).
    fun nodo_valid(): Boolean =
        candidatX in 0 until mida &&
        candidatY in 0 until mida &&
        taulell[candidatY][candidatX] == 0

    var onSolutionFound: ((Int) -> Unit)? = null

    // guarda una còpia del taulell com a solucio
    fun tractarSolucio(taulell: Array<IntArray>) {
        solucions.add(taulell.map { it.clone() }.toTypedArray())
        onSolutionFound?.invoke(solucions.size)
    }
}
