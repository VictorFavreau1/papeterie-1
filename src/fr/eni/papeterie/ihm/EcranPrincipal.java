package fr.eni.papeterie.ihm;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bll.CatalogueManager;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Stylo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Objects;

public class EcranPrincipal extends JFrame {
    /* Formulaire de saisie */
    private JTextField txtReference, txtDesignation, txtMarque, txtStock, txtPrix;
    private JLabel lblReference;
    private JRadioButton rbRamette, rbStylo;
    private JPanel panelType, panelGrammage;
    private JCheckBox chk80, chk100;
    private JComboBox<String> cboCouleur;

    /* Boutons */
    private JPanel panelBoutons;
    private JButton btnPrecedent;
    private JButton btnNouveau;
    private JButton btnEnregistrer;
    private JButton btnSupprimer;
    private JButton btnSuivant;

    private GridBagConstraints gbc;
    private JPanel panneauPrincipal;

    private List<Article> catalogue;
    private int index = 0;

    public EcranPrincipal() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Detail article");
        this.initIHM();
        this.pack();
        this.setVisible(true);
    }

    private void afficher(int index) {
        Article article = this.catalogue.get(index);
        getTxtReference().setText(article.getReference());
        if (article instanceof Stylo) {
            getRadioStylo().setSelected(true);
            getRadioRamette().setSelected(false);
        } else {
            getRadioStylo().setSelected(false);
            getRadioRamette().setSelected(true);
        }
    }

    private void initIHM() {
        try {
            // On récupère tout les articles en base de données
            this.catalogue = CatalogueManager.getInstance().getCatalogue();
            this.afficher(index);
        } catch (BLLException e) {
            e.printStackTrace();
        }
        this.panneauPrincipal = new JPanel();
        this.panneauPrincipal.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        this.gbc.gridy = 0;
        this.ligne1();
        this.gbc.gridy = 1;
        this.ligne2();
        this.gbc.gridy = 2;
        this.ligne3();
        this.gbc.gridy = 3;
        this.ligne4();
        this.gbc.gridy = 4;
        this.ligne5();
        this.gbc.gridy = 5;
        this.ligne6();
        this.gbc.gridy = 6;
        this.ligne7();
        this.gbc.gridy = 7;
        this.ligneCouleur();
        this.gbc.gridy = 8;
        this.ligneBoutons();
        this.setContentPane(panneauPrincipal);
    }

    public void ligneCouleur() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(new JLabel("Couleurs : "), gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getCboCouleur(), gbc);
    }

    public JComboBox<String> getCboCouleur() {
        if (cboCouleur == null) {
            String[] couleurs = {"", "Bleu", "Rouge", "Noir", "Vert"};
            cboCouleur = new JComboBox<>(couleurs);
        }
        return cboCouleur;
    }

    private void ligneBoutons() {
        this.gbc.gridx = 0;
        this.gbc.gridwidth = 2;
        this.panneauPrincipal.add(getPanelBoutons(), gbc);
    }

    private void ligne1() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(getLblReference(), this.gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getTxtReference(), this.gbc);
    }

    private void ligne2() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(new JLabel("Designation : "), gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getTxtDesignation(), gbc);
    }

    private void ligne3() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(new JLabel("Marque : "), gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getTxtMarque(), gbc);
    }

    private void ligne4() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(new JLabel("Stock : "), gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getTxtStock(), gbc);
    }

    private void ligne5() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(new JLabel("Prix : "), gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getTxtPrix(), gbc);
    }

    private void ligne6() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(new JLabel("Type : "), gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getPanelType(), gbc);
    }

    private void ligne7() {
        this.gbc.gridx = 0;
        this.panneauPrincipal.add(new JLabel("Grammage : "), gbc);
        this.gbc.gridx = 1;
        this.panneauPrincipal.add(getPanelGrammage(), gbc);
    }

    public JLabel getLblReference() {
        if (this.lblReference == null)
            this.lblReference = new JLabel("Reference : ");
        return this.lblReference;
    }

    public JTextField getTxtReference() {
        if (txtReference == null) {
            txtReference = new JTextField(30);
        }
        return txtReference;
    }

    public JTextField getTxtDesignation() {
        if (txtDesignation == null) {
            txtDesignation = new JTextField(30);
        }
        return txtDesignation;
    }

    public JTextField getTxtMarque() {
        if (txtMarque == null) {
            txtMarque = new JTextField(30);
        }
        return txtMarque;
    }

    public JTextField getTxtStock() {
        if (txtStock == null) {
            txtStock = new JTextField(30);
        }
        return txtStock;
    }

    public JTextField getTxtPrix() {
        if (txtPrix == null) {
            txtPrix = new JTextField(30);
        }
        return txtPrix;
    }

    public JPanel getPanelType() {
        if (panelType == null) {
            panelType = new JPanel();
            panelType.setLayout(new BoxLayout(panelType, BoxLayout.Y_AXIS));
            panelType.add(getRadioRamette());
            panelType.add(getRadioStylo());
            ButtonGroup bg = new ButtonGroup();
            bg.add(getRadioRamette());
            bg.add(getRadioStylo());
        }
        return panelType;
    }

    public JPanel getPanelGrammage() {
        if (panelGrammage == null) {
            panelGrammage = new JPanel();
            panelGrammage.setLayout(new BoxLayout(panelGrammage, BoxLayout.Y_AXIS));
            panelGrammage.add(getChk80());
            panelGrammage.add(getChk100());
            ButtonGroup bg = new ButtonGroup();
            bg.add(getChk80());
            bg.add(getChk100());
        }

        return panelGrammage;
    }

    public JRadioButton getRadioRamette() {
        if (rbRamette == null) {
            rbRamette = new JRadioButton("Ramette");
        }
        return rbRamette;
    }

    public JRadioButton getRadioStylo() {
        if (rbStylo == null) {
            rbStylo = new JRadioButton("Stylo");
        }

        return rbStylo;
    }

    public JCheckBox getChk80() {
        if (chk80 == null) {
            chk80 = new JCheckBox("80 grammes");
        }
        return chk80;
    }

    public JCheckBox getChk100() {
        if (chk100 == null) {
            chk100 = new JCheckBox("100 grammes");
        }
        return chk100;
    }

    public JPanel getPanelBoutons() {
        if (panelBoutons == null) {
            panelBoutons = new JPanel();
            panelBoutons.add(getBtnPrecedent());
            panelBoutons.add(getBtnNouveau());
            panelBoutons.add(getBtnEnregistrer());
            panelBoutons.add(getBtnSupprimer());
            panelBoutons.add(getBtnSuivant());
        }
        return panelBoutons;
    }

    public JButton getBtnPrecedent() {
        if (btnPrecedent == null) {
            btnPrecedent = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/Back24.gif")));
            btnPrecedent.setIcon(icone);
        }
        return btnPrecedent;
    }

    public JButton getBtnNouveau() {
        if (btnNouveau == null) {
            btnNouveau = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/New24.gif")));
            btnNouveau.setIcon(icone);
        }
        return btnNouveau;
    }

    public JButton getBtnEnregistrer() {
        if (btnEnregistrer == null) {
            btnEnregistrer = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/Save24.gif")));
            btnEnregistrer.setIcon(icone);
        }
        return btnEnregistrer;
    }

    public JButton getBtnSupprimer() {
        if (btnSupprimer == null) {
            btnSupprimer = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/Delete24.gif")));
            btnSupprimer.setIcon(icone);
        }
        return btnSupprimer;
    }

    public JButton getBtnSuivant() {
        if (btnSuivant == null) {
            btnSuivant = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/Forward24.gif")));
            btnSuivant.setIcon(icone);
            btnSuivant.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index < catalogue.size() - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                    afficher(index);
                }
            });
        }
        return btnSuivant;
    }
}
