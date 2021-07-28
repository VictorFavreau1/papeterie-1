package fr.eni.papeterie.ihm;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bll.CatalogueManager;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
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
    private static final int GRAMMAGE_80 = 80;
    private static final int GRAMMAGE_100 = 100;
    /* Formulaire de saisie */
    private JTextField txtReference, txtDesignation, txtMarque, txtStock, txtPrix;
    private JLabel lblReference, lblException;
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

    public JLabel getLblException() {
        if (this.lblException == null) {
            this.lblException = new JLabel("");
        }
        return this.lblException;
    }

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
        getTxtDesignation().setText(article.getDesignation());
        getTxtMarque().setText(article.getMarque());
        getTxtPrix().setText(String.valueOf(article.getPrixUnitaire()));
        getTxtStock().setText(String.valueOf(article.getQteStock()));
        if (article instanceof Stylo) {
            getRadioStylo().setSelected(true);
            getRadioRamette().setSelected(false);
            getChk80().setSelected(false);
            getChk100().setSelected(false);
            getChk80().setEnabled(false);
            getChk100().setEnabled(false);
        } else if (article instanceof Ramette){
            getRadioStylo().setSelected(false);
            getRadioRamette().setSelected(true);
            getChk80().setEnabled(true);
            getChk100().setEnabled(true);
            getCboCouleur().setSelectedItem("");
            getCboCouleur().setEnabled(false);
            if (((Ramette) article).getGrammage() == GRAMMAGE_80) {
                getChk80().setSelected(true);
                getChk100().setSelected(false);
            } else {
                getChk80().setSelected(false);
                getChk100().setSelected(true);
            }
        }
    }

    private void initIHM() {
        try {
            // On récupère tout les articles en base de données
            this.catalogue = CatalogueManager.getInstance().getCatalogue();
            if (catalogue.size() != 0) {
                this.afficher(index);
            }
        } catch (BLLException e) {
            getLblException().setText(e.getMessage());
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
        this.gbc.gridy = 9;
        this.gbc.gridwidth = 2;
        panneauPrincipal.add(getLblException(), gbc);
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
        }
        return panelType;
    }

    public JPanel getPanelGrammage() {
        if (panelGrammage == null) {
            panelGrammage = new JPanel();
            panelGrammage.setLayout(new BoxLayout(panelGrammage, BoxLayout.Y_AXIS));
            panelGrammage.add(getChk80());
            panelGrammage.add(getChk100());
        }

        return panelGrammage;
    }

    public JRadioButton getRadioRamette() {
        if (rbRamette == null) {
            rbRamette = new JRadioButton("Ramette");
            rbRamette.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getCboCouleur().setSelectedItem("");
                    getCboCouleur().setEnabled(false);
                    getChk80().setEnabled(true);
                    getChk100().setEnabled(true);
                    getRadioStylo().setSelected(false);
                }
            });
        }
        return rbRamette;
    }

    public JRadioButton getRadioStylo() {
        if (rbStylo == null) {
            rbStylo = new JRadioButton("Stylo");
            rbStylo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getChk80().setSelected(false);
                    getChk80().setEnabled(false);
                    getChk100().setSelected(false);
                    getChk100().setEnabled(false);
                    getRadioRamette().setSelected(false);
                    getCboCouleur().setEnabled(true);
                }
            });
        }

        return rbStylo;
    }

    public JCheckBox getChk80() {
        if (chk80 == null) {
            chk80 = new JCheckBox("80 grammes");
            chk80.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getChk100().setSelected(false);
                }
            });
        }
        return chk80;
    }

    public JCheckBox getChk100() {
        if (chk100 == null) {
            chk100 = new JCheckBox("100 grammes");
            chk100.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getChk80().setSelected(false);
                }
            });
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
            btnPrecedent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index > 0) {
                        index--;
                    } else {
                        index = catalogue.size() - 1;
                    }
                    afficher(index);
                }
            });
        }
        return btnPrecedent;
    }

    public JButton getBtnNouveau() {
        if (btnNouveau == null) {
            btnNouveau = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/New24.gif")));
            btnNouveau.setIcon(icone);
            btnNouveau.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getTxtDesignation().setText("");
                    getTxtMarque().setText("");
                    getTxtPrix().setText("");
                    getTxtReference().setText("");
                    getTxtStock().setText("");
                    getRadioRamette().setSelected(false);
                    getRadioStylo().setSelected(false);
                    getRadioRamette().setEnabled(true);
                    getRadioStylo().setEnabled(true);
                    getChk80().setSelected(false);
                    getChk100().setSelected(false);
                    getChk80().setEnabled(true);
                    getChk100().setEnabled(true);
                    getCboCouleur().setSelectedItem("");
                    getCboCouleur().setEnabled(true);
                    index = Integer.MIN_VALUE;
                }
            });
        }
        return btnNouveau;
    }

    public JButton getBtnEnregistrer() {
        if (btnEnregistrer == null) {
            btnEnregistrer = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/Save24.gif")));
            btnEnregistrer.setIcon(icone);
            btnEnregistrer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Article article = null;
                    String designation = getTxtDesignation().getText();
                    String marque = getTxtMarque().getText();
                    String reference = getTxtReference().getText();
                    float prix = Float.parseFloat(getTxtPrix().getText());
                    int stock = Integer.parseInt(getTxtStock().getText());
                    // Soit c'est un stylo soit c'est une ramette
                    if (getRadioStylo().isSelected()) { // C'est un stylo
                        String couleur = String.valueOf(getCboCouleur().getSelectedItem());
                        article = new Stylo(marque, reference, designation, prix, stock, couleur);
                    } else if (getRadioRamette().isSelected()) { // C'est une ramette
                        int grammage;
                        if (getChk80().isSelected()) {
                            grammage = GRAMMAGE_80;
                        } else {
                            grammage = GRAMMAGE_100;
                        }
                        article = new Ramette(marque, reference, designation, prix, stock, grammage);
                    }
                    System.out.println(index);
                    try {
                        if (index == Integer.MIN_VALUE) {
                            CatalogueManager.getInstance().addArticle(article); // J'ajoute en BDD
                            catalogue.add(article); // J"ajoute dans mon catalogue
                        } else {
                            CatalogueManager.getInstance().updateArticle(article); // J'update en BDD
                            catalogue = CatalogueManager.getInstance().getCatalogue(); // J'update mon catalogue
                        }
                    } catch (BLLException exception) {
                        getLblException().setText(exception.getMessage());
                    }
                }
            });
        }
        return btnEnregistrer;
    }

    public JButton getBtnSupprimer() {
        if (btnSupprimer == null) {
            btnSupprimer = new JButton();
            ImageIcon icone = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/Delete24.gif")));
            btnSupprimer.setIcon(icone);
            btnSupprimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = catalogue.get(index).getIdArticle(); // Je récupère l'id de l'article courant
                        CatalogueManager.getInstance().removeArticle(id); // J'utilise le manager pour supprimer en BDD
                        // catalogue = CatalogueManager.getInstance().getCatalogue(); // Avec une requete SQL
                        catalogue.remove(index); // Mise a jour du catalogue sans faire de requete SQL
                        if (index == catalogue.size()) {
                            getBtnPrecedent().doClick();
                        } else if (index < catalogue.size()) {
                            getBtnSuivant().doClick();
                        } else {
                            getBtnNouveau().doClick();
                        }
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            });
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
                    if (index < catalogue.size() - 1 && index != Integer.MIN_VALUE) {
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
