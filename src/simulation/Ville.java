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



}
