package com.example.saltsdecavall.component

class Joc {
    var taulell: Array<IntArray> = emptyArray()
    val solucions = mutableListOf<Array<IntArray>>()

    constructor() {}
    //midaTaules → Es para pedir la medida exacta del tablero

    //numMaxResolucions → Es para pedir el numero maximo de resoluciones a hacer

    //CoordX y CoordY -> Serán las coordenadas fila-columna del tablero donde empieza el caballo


    fun resolver(nivel: Int) {

        var nivel = nivel

        while (nivel > 0) {
            if (hihaSeguentNodo()) {
                seguentNodo()
                if (nodo_valid()) {
                    if (ultimNivell(taulell)) {
                        tractarSolucio(taulell)
                    } else {
                        nivel += 1

                        resolver(nivel)
                    }
                }

            } else {
                nivel -= 1
            }
        }

    }

    fun initTab(mida: Int): Array<IntArray> {
        var taulell = Array(mida) { IntArray(mida) { 0 } }

        return taulell
    }

    private fun ultimNivell(taulell: Array<IntArray>): Boolean {
        return taulell.all { fila -> fila.all { cel -> cel >= 1 } }
    }

    fun hihaSeguentNodo(): Boolean {

    }

    fun seguentNodo() {

    }

    fun nodo_valid(): Boolean {
        //TODO
        /**
         * Un nodo es válido si cumple dos condiciones:
         *
         *   1. Está dentro del tablero — la posición (x, y) candidata no sale fuera de los límites (0 ≤ x < n y 0 ≤ y < n)
         *   2. No ha sido visitada — la celda está a 0 en el tablero (el caballo no ha pasado antes por ahí)
         */
    }

    fun tractarSolucio(taulell: Array<IntArray>) {
        solucions.add(taulell.map { it.clone() }.toTypedArray())
    }

}