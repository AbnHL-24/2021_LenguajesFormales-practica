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
        char[] charsTmp = cadenaRecibida.toCharArray();
        char[] chars = new char[charsTmp.length + 1];
        System.arraycopy(charsTmp, 0, chars, 0, charsTmp.length);
        chars[charsTmp.length] = ' ';

        for (char c : chars) {
            if (Estados.EMPEZAR == estadoGuardado) {
                estadoEmpezarOEspacio(c);
            } else if (Estados.ESPACIO == estadoGuardado) {
                estadoEmpezarOEspacio(c);
            } else if (Estados.CARACTER == estadoGuardado) {
                estadoCaracter(c);
            } else if (Estados.PUNTO == estadoGuardado) {
                estadoPunto(c);
            } else if (Estados.DIGITO == estadoGuardado) {
                estadoDigito(c);
            } else if (Estados.SIMBOLO == estadoGuardado) {
                estadoSimbolo(c);
            } else if (Estados.ERROR ==  estadoGuardado) {
                estadoError(c);
            }
        }
    }

    private void estadoError(char c) {
        if (Character.isSpaceChar(c)) {
            tockens.add(tmp);
            tmp = "";
            tipoTockens.add(TiposDeTokens.ERROR);
            estadoGuardado = Estados.ESPACIO;
        } else {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.ERROR;
        }
    }

    private void estadoSimbolo(char c) {
        if (isSimbolo(c)){
            tmp += String.valueOf(c);
            tockens.add(tmp);
            tmp = "";
            tipoTockens.add(TiposDeTokens.SIMBOLO);
            estadoGuardado = Estados.SIMBOLO;
        } else if (Character.isSpaceChar(c)) {
            tockens.add(tmp);
            tmp = "";
            tipoTockens.add(TiposDeTokens.SIMBOLO);
            estadoGuardado = Estados.ESPACIO;
        } else {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.ERROR;
        }
    }

    private void estadoDigito(char c) {
        if (Character.isDigit(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.DIGITO;
        } else if (isPunto(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.PUNTO;
        } else if (Character.isSpaceChar(c)) {
            tockens.add(tmp);
            if (tmp.contains(".")) {
                tipoTockens.add(TiposDeTokens.DECIMAL);
            } else {
                tipoTockens.add(TiposDeTokens.ENTERO);
            }
            tmp = "";
            estadoGuardado = Estados.ESPACIO;
        } else {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.ERROR;
        }
    }

    private void estadoPunto(char c) {
        if (Character.isSpaceChar(c)) {
            tockens.add(tmp);
            tmp = "";
            tipoTockens.add(TiposDeTokens.ERROR);
            estadoGuardado = Estados.ESPACIO;
        } else if (Character.isDigit(c)) {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.DIGITO;
        } else {
            tmp += String.valueOf(c);
            estadoGuardado = Estados.ERROR;
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

    private void estadoEmpezarOEspacio(char c) {
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
