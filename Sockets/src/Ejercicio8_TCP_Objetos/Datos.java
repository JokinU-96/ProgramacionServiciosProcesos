package Ejercicio8_TCP_Objetos;

import java.io.Serializable;

public class Datos implements Serializable {
    private int operador1;
    private int operador2;
    private int resultado;

    public int getOperador1() {
        return operador1;
    }

    public int getOperador2() {
        return operador2;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public int getResultado() {
        return resultado;
    }

    public Datos(int operador1, int operador2) {
        this.operador1 = operador1;
        this.operador2 = operador2;
    }
}
