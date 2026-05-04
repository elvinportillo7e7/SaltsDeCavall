package com.example.saltsdecavall.component

//component principal que estableix la llogica del joc
class Joc(
    private val mida: Int,          // mida del taulell, sempre quadrat
    private val maxSolucions: Int,  // quantes solucions volem trobar abans de parar
    private val startX: Int,        // columna inicial del cavall
    private val startY: Int         // fila inicial del cavall
) {
    // Taulell de joc: taulell[fila][columna]. A cada cel·la se li asigna un numero en funcio de en quin moviment ha passat per aquesta
    // les que el cavall encara pot visitar s'asignen amb 0.
    var taulell: Array<IntArray> = emptyArray()

    // Llista on s'acumulen les solucions trobades (còpies del taulell complet)
    val solucions = mutableListOf<Array<IntArray>>()

   //aquestes dos variables de manera junta guarden els moviments que pot fer el cavall sent, fila i columna
    private val dx = intArrayOf( 2,  1, -1, -2, -2, -1,  1,  2)
    private val dy = intArrayOf( 1,  2,  2,  1, -1, -2, -2, -1)

    private val movIndex = IntArray(mida * mida + 1) { -1 }

    // px[nivel] i py[nivel]: columna i fila del cavall quan es troba al nivell 'nivel'.
    // Exemple: px[1]=startX, py[1]=startY és la posició inicial (pas 1).
    private val px = IntArray(mida * mida + 1)
    private val py = IntArray(mida * mida + 1)

    // Posició candidata que s'està avaluant en cada iteració del bucle.
    private var candidatX = 0
    private var candidatY = 0

    // Nivell actiu en cada moment del bucle (necessari per a hihaSeguentNodo i seguentNodo).
    private var nivelActual = 0

    init {
        taulell = initTab(mida)
        taulell[startY][startX] = 1  // col·loquem el cavall a la posició inicial (pas 1)
        px[1] = startX
        py[1] = startY
    }

    // Algorisme de backtracking iteratiu.
    // 'nivel' representa quants passos ja té el cavall sobre el taulell.
    // S'ha de cridar amb resolver(1) perquè el primer pas ja és al taulell.
    fun resolver(nivel: Int) {
        var nivel = nivel
        inicialitzarNivell(nivel)

        while (nivel > 0 && solucions.size < maxSolucions) {
            nivelActual = nivel
            if (hihaSeguentNodo()) {
                seguentNodo()               // calcula el següent candidat (ordre Warnsdorff)
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
                        inicialitzarNivell(nivel)  // calculem l'ordre Warnsdorff del nou nivell
                    }
                }
            } else {
                // Esgotats tots els moviments en aquest nivell → backtrack
                if (nivel > 1) taulell[py[nivel]][px[nivel]] = 0  // desfer el pas actual
                nivel--  // tornem al nivell anterior
            }
        }
    }

    // Inicialitza el nivell 'nivel': posa movIndex a -1.
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

    // Retorna true si hi ha solució (s'ha de cridar després de resolver()).
    fun teSolucio(): Boolean = solucions.isNotEmpty()

    // Retorna true si encara queden moviments per provar en el nivell actual
    // (els moviments es numeren de 0 a 7).
    fun hihaSeguentNodo(): Boolean = movIndex[nivelActual] < 7

    // Avança al següent moviment candidat del nivell actual (ordre fix 0–7)
    // i calcula la posició resultant (candidatX, candidatY).
    fun seguentNodo() {
        movIndex[nivelActual]++
        candidatX = px[nivelActual] + dx[movIndex[nivelActual]]
        candidatY = py[nivelActual] + dy[movIndex[nivelActual]]
    }

    // Un candidat és vàlid si:
    //   1. La posició (candidatX, candidatY) és dins del taulell.
    //   2. La cel·la no ha estat visitada encara (valor == 0).
    fun nodo_valid(): Boolean =
        candidatX in 0 until mida &&
        candidatY in 0 until mida &&
        taulell[candidatY][candidatX] == 0

    // Guarda una còpia profunda del taulell actual com a nova solució.
    fun tractarSolucio(taulell: Array<IntArray>) {
        solucions.add(taulell.map { it.clone() }.toTypedArray())
    }
}
