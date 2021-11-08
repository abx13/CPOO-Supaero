package consommateurs;

import java.util.ArrayList;
import simulation.Temps;

/**
 * Cette classe permet de construire un Foyer puis de calculer sa consommation
 * journalière et annuelle. Un Foyer est composé d'un ensemble d'appareils. Cet
 * ensemble est différent selon le type du foyer (particulier, entreprise,
 * unknown)
 * 
 * @author Anna Barraqué
 */
public class Foyer implements Consommateur {
    /**
     * Cette classe a 2 attributs: une liste d'appareils un type définissant la
     * liste d'appareils du foyer
     */
    private ArrayList<Consommateur> appareils;
    private TypeConsommateur type;

    /**
     * Cette méthode est le constructeur de la classe.
     * 
     * @param type est le type de consommateur qui determine le nombre d'appareil
     *             qu'a le foyer. Ce type est défini dans l'énumération
     *             TypeConsommateur. A partir de ce type, la liste d'appareil est
     *             définie par défaut (pour un particulier ou une entreprise) ou
     *             aléatoirement (pour la type unknown)
     * 
     * @see consommateurs.TypeConsommateur
     */
    public Foyer(TypeConsommateur type) {
        this.type = type;
        this.appareils = new ArrayList<>();
        switch (this.type) {
        case PARTICULIER:
            this.appareils.add(new Frigo());
            this.appareils.add(new MachineCafe());
            for (int i = 0; i < 7; i++) {
                this.appareils.add(new Radiateur());
            }
            break;

        case ENTREPRISE:
            for (int i = 0; i < 10; i++) {
                this.appareils.add(new Frigo());
            }
            for (int i = 0; i < 100; i++) {
                this.appareils.add(new MachineCafe());
            }
            for (int i = 0; i < 500; i++) {
                this.appareils.add(new Radiateur());
            }
            break;

        case UNKNOWN:
            for (int i = 0; i < Math.random() * 100; i++) {
                this.appareils.add(new Frigo());
            }
            for (int i = 0; i < Math.random() * 100; i++) {
                this.appareils.add(new MachineCafe());
            }
            for (int i = 0; i < Math.random() * 100; i++) {
                this.appareils.add(new Radiateur());
            }
            break;
        }

    }

    /**
     * Cette classe est un autre constructeur avec une liste prédéfinie d'appareils
     * que possède le foyer.
     * 
     * @param type,      le type de consommateur (particulier, entreprise, unknown)
     *                   défini par Consommateur.TypeConsommateur
     * @param appareils, la liste d'appareils que possède le foyer.
     * 
     * @see consommateurs.TypeConsommateur
     */
    public Foyer(ArrayList<Consommateur> appareils, TypeConsommateur type) {
        this.type = type;
        this.appareils = appareils;
    }

    /**
     * Cette méthode permet d'obtenir un tableau de la consommation du foyer pour
     * chaque minute de la journée donnée. Elle prend en compte tous les appareils
     * que possède le foyer.
     * 
     * @param journée est le jour de l'année pour lequel on veut le rapport
     *                énergétique (valeur entière comprise entre 1 et 365)
     * @return puissanceMinute est un tableau de taille 1440 (nombre de minutes par
     *         jour) donnant la valeur de la puissance consommée par le foyer pour
     *         chaque minute de la journée.
     */
    public double[] computeMinute(int journee) {
        double[] puissanceMinute = new double[Temps.NBMINUTESJOUR];
        double[][] appareilsPuissanceMinute = new double[this.appareils.size()][Temps.NBMINUTESJOUR];

        for (int i = 0; i < this.appareils.size(); i++) {
            appareilsPuissanceMinute[i] = this.appareils.get(i).computeMinute(journee);
        }

        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            double puissancetmp = 0;
            for (int j = 0; j < this.appareils.size(); j++) {
                puissancetmp += appareilsPuissanceMinute[j][i];
            }
            puissanceMinute[i] = puissancetmp;
        }
        return puissanceMinute;

    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance consommée pour
     * chaque journée de l'année. Cette méthode appelle pour chaque journée la
     * méthode computeMinute qui retourne un tableau de la puissance consommée pour
     * chaque minute de la journée. Puis appelle la méthode cumulDay de la classe
     * Temps du Package simulation qui somme les valeurs de chaque minute d'une
     * journée pour obtenir la valeur de la production journalière cumulée
     * 
     * @return puissanceJour, un tableau de taille 365 (nombre de jours dans
     *         l'année) comprenant la valeur de la puissance consommée pour chaque
     *         journée.
     * 
     * @see consommateurs.AppareilFrequentiel.computeMinute
     * @see simulation.Temps
     */
    public double[] computeDay() {
        double[] puissanceJour = new double[Temps.NBJOURSANNEE];
        for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
            puissanceJour[i] = Temps.cumulDay(computeMinute(i));
        }
        return puissanceJour;
    }

    /**
     * Cette méthode est le getter du type de consommateur de foyer (particulier,
     * entreprise, unknown).
     * 
     * @return type, le type de consommateur du foyer
     */
    public TypeConsommateur getType() {
        return this.type;
    }

}
