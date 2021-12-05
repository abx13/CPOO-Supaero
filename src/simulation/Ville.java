package simulation;

import java.util.ArrayList;

import consommateurs.Consommateur;
import producteurs.Producteur;

/**
 * Cette classe permet de simuler une ville ayant des producteurs et des
 * consommateurs d'énergie. Elle calcule la production et consommation d'énergie
 * pour chaque minute de la journée donnée ainsi que pour chaque jour de
 * l'année.
 * 
 * @author Anna Barraqué
 */
public class Ville {
    /**
     * La ville est composée d'une liste de producteurs et d'une liste de
     * consommateurs.
     */
    private ArrayList<Producteur> producteurs;
    private ArrayList<Consommateur> consommateurs;

    /**
     * Cette méthode est le constructeur pour instancier la ville avec deux listes
     * de consommateurs et producteurs données.
     * 
     * @param producteurs   est une liste de producteurs d'énergie de la ville
     * @param consommateurs est une liste de consommateurs d'énergie de la ville
     */
     public Ville(ArrayList<Producteur> producteurs, ArrayList<Consommateur> consommateurs) {
        this.producteurs = producteurs;
        this.consommateurs = consommateurs;
    }

    /**
     * Cette méthode est le constructeur pour instancier les listes de producteurs et consommateurs
     */
    public Ville() {
        this.producteurs = new ArrayList<>();
        this.consommateurs = new ArrayList<>();
    }

    /**
     * Cette méthode est un setteur pour l'attribut producteurs 
     */
    public void setProducteurs(ArrayList<Producteur> producteursAdd){
        this.producteurs = producteursAdd;
    }

    /**
     * Cette méthode est un setteur pour l'attribut consommateurs 
     */
    public void setConsommateurs(ArrayList<Consommateur> consommateursAdd){
        this.consommateurs = consommateursAdd;
    }

    /**
     * Cette méthode est le getter de la liste des producteurs de la ville
     * 
     * @return producteurs, la liste des producteurs de la ville
     */
    public ArrayList<Producteur> getProducteurs() {
        return this.producteurs;
    }

    /**
     * Cette méthode est le getter de la liste des consommateurs de la ville
     * 
     * @return consommateurs, la liste des consommateurs de la ville
     */
    public ArrayList<Consommateur> getConsommateurs() {
        return this.consommateurs;
    }


    /**
     * Cette méthode permet de calculer la valeur de la puissance consommée dans la
     * ville pour chaque minute de la journée donnée. Cette méthode somme les
     * puissances consommées par l'ensemble des consommateurs sur une minute.
     * 
     * @param journee, journée pour laquelle on génère le rapport journalier.
     * @return puissanceConsoTot, un tableau à une dimension comprenant la somme des
     *         puissances consommées par tous les consommateurs pour chacune des
     *         1440 minutes de la journée.
     */
    public double[] getTableConsommateur() {
        // on get les puissances de tous les consommateurs
        double[][] puissConso = new double[consommateurs.size()][];
        for (int j = 0; j < consommateurs.size(); j++) {
            puissConso[j] = consommateurs.get(j).getTable();
        }

        // on calcule la somme de la consommation sur chaque minute
        double[] puissConsoTot = new double[puissConso[0].length];
        for (int i = 0; i < puissConso[0].length; i++) {
            puissConsoTot[i] = puissConso[0][i];
            for (int j = 1; j < consommateurs.size(); j++) {
                puissConsoTot[i] += puissConso[j][i];
            }
        }
        return puissConsoTot;

    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite dans la
     * ville pour chaque minute de la journée donnée. Cette méthode somme les
     * puissances produite par l'ensemble des producteur sur une minute.
     * 
     * @param journee, journée pour laquelle on génère le rapport journalier.
     * @return puissanceProdTot, un tableau à une dimension comprenant la somme des
     *         puissances produites par tous les producteurs pour chacune des 1440
     *         minutes de la journée.
     */
    public double[] getTableProducteur() {

        // on get les puissances de tous les prod
        double[][] puissProd = new double[producteurs.size()][];
        for (int j = 0; j < producteurs.size(); j++) {
            puissProd[j] = producteurs.get(j).getTable();
        }

        // on calcule la somme de la production sur chaque minute
        double[] puissProdTot = new double[ puissProd[0].length];
        for (int i = 0; i < puissProd[0].length; i++) {
            puissProdTot[i] = puissProd[0][i];
            for (int j = 1; j < producteurs.size(); j++) {
                puissProdTot[i] += puissProd[j][i];
            }
        }
        return puissProdTot;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite et
     * consommée dans la ville pour chaque minute de la journée donnée. Cette
     * méthode appelle pour des méthodes permettant de calculer les puissances
     * totales consommées et produites.
     * 
     * @return puissanceJour, un tableau de deux dimensions, 1440 lignes (nombre de
     *         minutes par jour) et - 5 colonnes pour le scenario integre - 3
     *         colonnes pour le scenario unitaire comprenant les valeurs de la
     *         puissance consommée et/ou produite pour chaque journée ainsi que les
     *         valeurs cumulées sur la journée.
     * 
     * @see producteurs
     * @see consommateurs
     * @see simulation.Temps
     */
    public double[][] getTable() {
        // on instancie ces tableaux plus tard pour qu'ils correspondent au format
        // demandé
        double[] puissConsoTot = null;
        double[] puissProdTot = null;
        double[][] puissance = null;
        // on instancie a zero pour pouvoir faire des incrementations plus tard
        double cumulConso = 0;
        double cumulProd = 0;

        // on instancie le tableau au format correspondant selon le nombre de
        // consommateurs et producteurs

        // format scenario unitaire: num min, puissconso min || puissprod min,
        // puissconso cumul || puissprod cumul
        if (producteurs.size() == 0 || consommateurs.size() == 0) {
            
            // format unitaire pour les producteurs
            if (consommateurs.size() == 0) {
                puissProdTot = this.getTableProducteur();
                puissance = new double[puissProdTot.length][3];
                for (int i = 0; i < puissProdTot.length; i++) {
                    puissance[i][0] = i;
                    puissance[i][1] = puissProdTot[i];
                    puissance[i][2] = cumulProd + (puissProdTot[i] / Temps.NBMINUTESHEURE);
                    cumulProd = puissance[i][2];
                }
            // format unitaire pour les consommateurs
            } else {
                puissConsoTot = this.getTableConsommateur();
                puissProdTot = this.getTableProducteur();
                puissance = new double[puissProdTot.length][3];
                for (int i = 0; i < puissProdTot.length; i++) {
                    puissance[i][0] = i;
                    puissance[i][1] = puissConsoTot[i];
                    puissance[i][2] = cumulConso + (puissConsoTot[i] / Temps.NBMINUTESHEURE);
                    cumulConso = puissance[i][2];
                }
            }
        // format scenario integre : num min, puissconso min, puissprod min, puissconso
        // cumul, puissprod cumul
        } else {
            puissConsoTot = this.getTableConsommateur();
            puissProdTot = this.getTableProducteur();

            puissance = new double[puissConsoTot.length][5];
            for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
                puissance[i][0] = i;
                puissance[i][1] = puissConsoTot[i];
                puissance[i][2] = puissProdTot[i];
                puissance[i][3] = cumulConso + (puissConsoTot[i] / Temps.NBMINUTESHEURE);
                puissance[i][4] = cumulProd + (puissProdTot[i] / Temps.NBMINUTESHEURE);
                cumulConso = puissance[i][3];
                cumulProd = puissance[i][4];
            }
        }

        return puissance;
    }




}
