package controlador;

import modelo.procesar.Analizador;
import vista.InterfazGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InterfazCTRL implements ActionListener {
    InterfazGUI interfazGui;

    public InterfazCTRL(InterfazGUI interfazGui) {
        this.interfazGui = interfazGui;

        interfazGui.getBtnLimpiar().addActionListener(this);
        interfazGui.getBtnProcesar().addActionListener(this);
    }

    public void iniciar() {
        interfazGui.setResizable(false);
        interfazGui.setLocationRelativeTo(null);
        interfazGui.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interfazGui.getBtnLimpiar()) {
            interfazGui.getTxaTexto().setText("");
        } else if (e.getSource() == interfazGui.getBtnProcesar()) {
            SeparadorLineasCTRL separadorLineasCTRL = new SeparadorLineasCTRL(interfazGui.getTxaTexto());
            List<String> lineas = separadorLineasCTRL.separarLineas();
            for (String s : lineas) {
                Analizador analizador = new Analizador(s);
                analizador.analizar();
                for (int i = 0; i < analizador.getTockens().size(); i++) {
                    System.out.println(analizador.getTockens().get(i) +
                            ", " + analizador.getTipoTockens().get(i).name());
                }
            }
        }
    }
}
