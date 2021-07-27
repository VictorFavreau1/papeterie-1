package fr.eni.papeterie.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionSurBoutons implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("J'ai clique sur un bouton.");
    }
}
