package modelo.procesar;

import lombok.*;
import modelo.estados.Estados;
import modelo.tokens.TiposDeTokens;

import java.util.ArrayList;

@Getter
@Setter
public class Analizador {
    String cadenaRecibida;
    Estados estadoGuardado = Estados.EMPEZAR;
    String tmp = "";
    ArrayList<String> tockens = new ArrayList<>();
    ArrayList<TiposDeTokens> tipoTockens = new ArrayList<>();

    public Analizador(String cadenaRecibida) {
        this.cadenaRecibida = cadenaRecibida;
    }

    public void analizar() {
        char[] chars = cadenaRecibida.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (i < chars.length - 1) {
                if (Estados.EMPEZAR == estadoGuardado) {
                    estadoEmpezar(chars[i]);
                } else if (Estados.CARACTER == estadoGuardado) {
                    estadoCaracter(chars[i]);
                } else if (Estados.PUNTO == estadoGuardado) {
                    estadoPunto(chars[i]);
                }
            } else {
                //Aca se agregara la logica para la ultima posicon del arreglo de char
            }
        }
    }

    private void estadoPunto(char c) {
        if (isPunto(c)) {

        }
    }

    private void estadoCaracter(char c) {
        if (Character.isSpaceChar(c)) {
            tockens.add(tmp);
            tmp = "";
            tipoTockens.add(TiposDeTokens.IDENTIFICADOR);
            estadoGuardado = Estados.ESPACIO;
        } else if (Character.isLetter(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.CARACTER;
        } else if (Character.isDigit(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.CARACTER;
            //Los identificadores pueden contener digitos, y estos digitos no son numeros sino caracteres.
        } else {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.ERROR;
        }
    }

    private void estadoEmpezar(char c) {
        if (Character.isLetter(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.CARACTER;
        } else if (Character.isDigit(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.DIGITO;
        } else if (Character.isSpaceChar(c)) {
            estadoGuardado = Estados.ESPACIO;
        } else if (isPunto(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.PUNTO;
        } else if (isSimbolo(c)) {
            tmp += String.valueOf(c);
            tockens.add(tmp);
            tmp = "";
            tipoTockens.add(TiposDeTokens.SIMBOLO);
            estadoGuardado = Estados.SIMBOLO;
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
