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
                if (Character.isLetter(c)) {
                    tmp += String.valueOf(c);
                    estadoGuardado = Estados.LETRA;
                } else if (Character.isDigit(c)) {
                    tmp += String.valueOf(c);
                    estadoGuardado = Estados.DIGITO;
                }
            }
        }
    }
}
