package modelo.procesar;

import lombok.*;
import modelo.estados.Estados;

import java.util.ArrayList;

@Builder
@Getter
@Setter
public class Analizador {
    String cadenaRecibida;
    Estados estadoGuardado = Estados.EMPEZAR;
    String tmp = "";
    ArrayList<String> tockens = new ArrayList<>();

    public void analizar() {
        char[] chars = cadenaRecibida.toCharArray();

        for (char c : chars) {
            if (Estados.EMPEZAR == estadoGuardado) {
                estadoEmpezar(c);
            } else if (Estados.LETRA == estadoGuardado) {
                estadoLetra(c);
            }
        }
    }

    private void estadoLetra(char c) {
        
    }

    private void estadoEmpezar(char c) {
        if (Character.isLetter(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.LETRA;
        } else if (Character.isDigit(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.DIGITO;
        } else if (Character.isSpaceChar(c)) {
            estadoGuardado = Estados.ESPACIO;
        } else if (isPunto(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.PUNTO;
            tockens.add(tmp);
            tmp = "";
        } else if (isSimbolo(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.SIMBOLO;
            tockens.add(tmp);
            tmp = "";
        }
    }

    private boolean isSimbolo(char c) {
        if ("[".equals(String.valueOf(c))) {
            return true;
        } else if ("]".equals(String.valueOf(c))) {
            return true;
        } else if ("{".equals(String.valueOf(c))) {
            return true;
        } else if ("}".equals(String.valueOf(c))) {
            return true;
        } else if (";".equals(String.valueOf(c))) {
            return true;
        } else if (",".equals(String.valueOf(c))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPunto(char c) {
        if (".".equals(String.valueOf(c))) {
            return true;
        } else {
            return false;
        }
    }
}
