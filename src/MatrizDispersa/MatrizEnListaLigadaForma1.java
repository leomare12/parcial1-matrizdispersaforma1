package MatrizDispersa;

public class MatrizEnListaLigadaForma1 {

    NodoDoble nodoCabezaMatriz;

    MatrizEnListaLigadaForma1(int numeroFilas, int numeroColumnas) {
        construyeNodosCabeza(numeroFilas, numeroColumnas);
    }

    private void construyeNodosCabeza(int numeroFilas, int numeroColumnas) {
        Tripleta tripletaConfiguracion = new Tripleta(numeroFilas, numeroColumnas, null);
        nodoCabezaMatriz = new NodoDoble(tripletaConfiguracion);

        int max = (numeroFilas > numeroColumnas) ? numeroFilas : numeroColumnas;

        NodoDoble ultimo = nodoCabezaMatriz;
        for (int i = 1; i <= max; i++) {
            NodoDoble nuevoNodoRegistroCabeza = new NodoDoble(new Tripleta(i, i, null));
            nuevoNodoRegistroCabeza.setLigaC(nuevoNodoRegistroCabeza);
            nuevoNodoRegistroCabeza.setLigaF(nuevoNodoRegistroCabeza);
            setLigaNodoCabeza(ultimo, nuevoNodoRegistroCabeza);
            ultimo = nuevoNodoRegistroCabeza;
        }
        setLigaNodoCabeza(ultimo, nodoCabezaMatriz);
    }

    private static void setLigaNodoCabeza(NodoDoble a, NodoDoble b) {
        a.getT().setV(b);
    }

    private NodoDoble getLigaNodoCabeza(NodoDoble a) {
        return (NodoDoble) a.getT().getV();
    }

    public void setCelda(int fila, int columna, double valor) {
        Tripleta nuevoTripletaRegistro = new Tripleta(fila, columna, valor);
        setCelda(nuevoTripletaRegistro);
    }

    public void setCelda(Tripleta t) {
        NodoDoble nuevoNodoRegistro = new NodoDoble(t);
        NodoDoble nodoCabezaDeRecorridoLocalizado = getLigaNodoCabeza(nodoCabezaMatriz);

        while (nodoCabezaDeRecorridoLocalizado != nodoCabezaMatriz && nodoCabezaDeRecorridoLocalizado != null) {
            if (nodoCabezaDeRecorridoLocalizado.getT().getF() == t.getF()) {
                conectaPorFilas(nodoCabezaDeRecorridoLocalizado, nuevoNodoRegistro);
                break;
            }
            nodoCabezaDeRecorridoLocalizado = getLigaNodoCabeza(nodoCabezaDeRecorridoLocalizado);
        }

        nodoCabezaDeRecorridoLocalizado = getLigaNodoCabeza(nodoCabezaMatriz);
        while (nodoCabezaDeRecorridoLocalizado != nodoCabezaMatriz && nodoCabezaDeRecorridoLocalizado != null) {
            if (nodoCabezaDeRecorridoLocalizado.getT().getC() == t.getC()) {
                conectaPorColumnas(nodoCabezaDeRecorridoLocalizado, nuevoNodoRegistro);
                break;
            }
            nodoCabezaDeRecorridoLocalizado = getLigaNodoCabeza(nodoCabezaDeRecorridoLocalizado);
        }
    }

    private void conectaPorFilas(NodoDoble nodoCabezaDeRecorridoLocalizado, NodoDoble nuevoNodoRegistro) {
        NodoDoble nodoRecorridoEnLaFila = nodoCabezaDeRecorridoLocalizado.getLigaF();
        NodoDoble ultimoNodoDeFila = nodoCabezaDeRecorridoLocalizado;
        boolean siDebeInsertar = true;
        while (nodoRecorridoEnLaFila != null && nodoRecorridoEnLaFila != nodoCabezaDeRecorridoLocalizado) {
            int columnaNuevoRegistro = nuevoNodoRegistro.getT().getC();
            int columnaNodoRestroRecorrido = nodoRecorridoEnLaFila.getT().getC();
            if (columnaNuevoRegistro > columnaNodoRestroRecorrido) {
                ultimoNodoDeFila = nodoRecorridoEnLaFila;
                nodoRecorridoEnLaFila = nodoRecorridoEnLaFila.getLigaF();
            } else if (columnaNuevoRegistro == columnaNodoRestroRecorrido) {
                siDebeInsertar = false;
                nodoRecorridoEnLaFila.getT().setV(nuevoNodoRegistro.getT().getV());
                break;
            } else {
                break;
            }
        }
        if (siDebeInsertar) {
            nuevoNodoRegistro.setLigaF(nodoRecorridoEnLaFila);
            ultimoNodoDeFila.setLigaF(nuevoNodoRegistro);
        }
    }

    private void conectaPorColumnas(NodoDoble nodoCabezaDeRecorridoLocalizado, NodoDoble nuevoNodoRegistro) {
        NodoDoble nodoRecorridoEnLaColumna = nodoCabezaDeRecorridoLocalizado.getLigaC();
        NodoDoble ultimoNodoDeColumna = nodoCabezaDeRecorridoLocalizado;
        boolean siDebeInsertar = true;

        while (nodoRecorridoEnLaColumna != null && nodoRecorridoEnLaColumna != nodoCabezaDeRecorridoLocalizado) {
            int filaNuevoRegistro = nuevoNodoRegistro.getT().getF();
            int filaNodoRecorrido = nodoRecorridoEnLaColumna.getT().getF();

            if (filaNuevoRegistro > filaNodoRecorrido) {
                ultimoNodoDeColumna = nodoRecorridoEnLaColumna;
                nodoRecorridoEnLaColumna = nodoRecorridoEnLaColumna.getLigaC();
            } else if (filaNuevoRegistro == filaNodoRecorrido) {
                siDebeInsertar = false;
                nodoRecorridoEnLaColumna.getT().setV(nuevoNodoRegistro.getT().getV());
                break;
            } else {
                break;
            }
        }
        if (siDebeInsertar) {
            nuevoNodoRegistro.setLigaC(nodoRecorridoEnLaColumna);
            ultimoNodoDeColumna.setLigaC(nuevoNodoRegistro);
        }
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        // Obtengo la configuración de la matriz, fr y cr
        Tripleta configuracion = nodoCabezaMatriz.getT();
        int cantidadFilasMatriz = configuracion.getF();
        int cantidadColumnasMatriz = configuracion.getC();
        // Imprimir una línea con encabezado de las columnas
        cadena.append("\t");
        for (int i = 1; i <= cantidadColumnasMatriz; i++) {
            cadena.append(i + "\t");
        }
        cadena.append("\n");

        NodoDoble nodoRecorridoCabeza = getLigaNodoCabeza(nodoCabezaMatriz);

        // Recorrido por una matriz virtual m x n
        for (int fv = 1; fv <= cantidadFilasMatriz; fv++) {
            cadena.append(fv + "\t");
            if (nodoRecorridoCabeza != null && nodoRecorridoCabeza != nodoCabezaMatriz) {
                NodoDoble nodoRecorridoCeldas = nodoRecorridoCabeza.getLigaF();
                for (int cv = 1; cv <= cantidadColumnasMatriz; cv++) {
                    if (nodoRecorridoCeldas != null && nodoRecorridoCeldas != nodoRecorridoCabeza) {
                        Tripleta triMo = nodoRecorridoCeldas.getT();
                        int ft = triMo.getF();
                        int ct = triMo.getC();
                        if (fv == ft) {
                            if (cv < ct) {
                                cadena.append("0\t");
                            } else if (cv == ct) {
                                Object vt = triMo.getV();
                                if (vt != null) {
                                    cadena.append(vt + "\t");
                                } else {
                                    cadena.append("ERROR x COLUMNAS!!!!");
                                }
                                nodoRecorridoCeldas = nodoRecorridoCeldas.getLigaF();
                            }
                        } else {
                            cadena.append("ERROR x FILAS !!!!");
                        }
                    } else {
                        cadena.append("0\t");
                    }
                }
            }
            nodoRecorridoCabeza = getLigaNodoCabeza(nodoRecorridoCabeza);
            cadena.append("\n");
        }
        return cadena.toString();
    }

    public int getFilas() {
        return nodoCabezaMatriz.getT().getF();
    }

    public int getCelda(int i, int j) {
        int valor = 0;

        // Obtengo un nodo cabeza para recorrer la lista de nodos cabeza
        NodoDoble nCDeRecorrido = getLigaNodoCabeza(nodoCabezaMatriz);

        /**
         * Buscar el nodo cabeza correspondiente a la Fila del registro que estamos
         * buscando y cuando lo encuentra buscar el registro en la lista de columnas de
         * esa fila
         */
        while (nCDeRecorrido != nodoCabezaMatriz && nCDeRecorrido != null) {
            // Cuando localice la fila busco la columna
            if (nCDeRecorrido.getT().getF() == i) {
                NodoDoble nodoRecorrido = nCDeRecorrido.getLigaF();
                NodoDoble cabezaRecorrido = (NodoDoble) nCDeRecorrido;
                while (nodoRecorrido != null && nodoRecorrido != cabezaRecorrido) {
                    if (j > nodoRecorrido.getT().getC()) {
                        nodoRecorrido = nodoRecorrido.getLigaF();
                    } else {
                        // Cuando no es mayor valido si estoy en la columna
                        if (j == nodoRecorrido.getT().getC()) {
                            valor = (int) nodoRecorrido.getT().getV();
                        }
                        break;
                    }
                }
            }

            nCDeRecorrido = getLigaNodoCabeza(nCDeRecorrido);
        }

        return valor;

    }

    public int getColumnas() {
        return nodoCabezaMatriz.getT().getC();
    }

    public static NodoDoble getCopiaListaFila(NodoDoble nCDeRecorrido) {

        NodoDoble copiaFila = new NodoDoble(nCDeRecorrido.getT().clonar());
        copiaFila.setLigaF(copiaFila);
        NodoDoble ultimoNodoDeFilaCopia = (NodoDoble) copiaFila;

        NodoDoble nodoRecorrido = nCDeRecorrido.getLigaF();
        NodoDoble cabezaRecorrido = (NodoDoble) nCDeRecorrido;
        while (nodoRecorrido != null && nodoRecorrido != cabezaRecorrido) {
            Tripleta tripletaCopia = nodoRecorrido.getT().clonar();
            NodoDoble nuevoNodocopia = new NodoDoble(tripletaCopia);
            ultimoNodoDeFilaCopia.setLigaF(nuevoNodocopia);
            ultimoNodoDeFilaCopia = nuevoNodocopia;
            nodoRecorrido = nodoRecorrido.getLigaF();
        }
        return copiaFila;
    }

    public NodoDoble getFila(int i) {
        NodoDoble nCDeRecorrido = getLigaNodoCabeza(nodoCabezaMatriz);
        while (nCDeRecorrido != nodoCabezaMatriz && nCDeRecorrido != null) {
            // Cuando localice la fila
            if (nCDeRecorrido.getT().getF() == i) {
                return nCDeRecorrido;
            }
        }
        return nCDeRecorrido;
    }

}
