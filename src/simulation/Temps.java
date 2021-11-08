package simulation;

/**
 * Cette classe permet de définir les variables temporelles constantes utilisées
 * dans tout le projet ainsi que de faire des conversions temporelles.
 * 
 * @author Anna Barraqué
 */
public class Temps {
    /**
     * Les attributs final static temporels nécessaire dans le projet
     */
    public final static int NBMINUTESJOUR = 1440;
    public final static int NBMINUTESHEURE = 60;
    public final static int NBHEURESJOUR = 24;
    public final static int NBJOURSANNEE = 365;

    /**
     * Cette methode permet de donner le mois auquel appartient une journee.
     * 
     * @param journee La journee est un entier compris entre 1 et 365.
     * @return mois Le mois est un entier compris entre 1 et 12.
     */
    public static int dayToMonth(int journee) {
        if (journee <= 31) {
            return 1;
        } else {
            if (journee <= 59) {
                return 2;
            } else {
                if (journee <= 90) {
                    return 3;
                } else {
                    if (journee <= 120) {
                        return 4;
                    } else {
                        if (journee <= 151) {
                            return 5;
                        } else {
                            if (journee <= 181) {
                                return 6;
                            } else {
                                if (journee <= 212) {
                                    return 7;
                                } else {
                                    if (journee <= 243) {
                                        return 8;
                                    } else {
                                        if (journee <= 273) {
                                            return 9;
                                        } else {
                                            if (journee <= 304) {
                                                return 10;
                                            } else {
                                                if (journee <= 334) {
                                                    return 11;
                                                } else {
                                                    return 12;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Cette methode permet de donner a quelle saison appartient un mois.
     * 
     * @param mois Le mois est un entier compris entre 1 et 12.
     * @return saison La saison est une String ("winter", "spring", "summer",
     *         "fall").
     */
    public static String monthToSeason(int mois) {
        if ((mois > 0 && mois < 4) || mois == 12) {
            return "winter";
        } else {
            if (mois > 3 && mois < 7) {
                return "spring";
            } else {
                if (mois > 6 && mois < 9) {
                    return "summer";
                } else {
                    return "fall";
                }
            }
        }
    }

    /**
     * Cette methode permet de sommer les valeurs de la puissance produite (ou
     * consomee) a chaque minute de la journee pour obtenir la valeur de la
     * puissance produite (ou consommee) sur une journee.
     * 
     * @param puissanceMinute est un tableau de double de taille 1440, comprenant la
     *                        puissance produite (ou consommee) a chaque minute de
     *                        la journee.
     * @return cumul est la valeur de la production (ou consommation) de puissance
     *         de la journee.
     */
    public static double cumulDay(double[] puissanceMinute) {
        double cumul = 0;
        for (int i = 0; i < puissanceMinute.length; i++) {
            cumul += puissanceMinute[i];
        }
        return cumul;
    }

}
