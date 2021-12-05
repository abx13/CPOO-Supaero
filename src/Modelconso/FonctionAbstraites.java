package Modelconso;

import simulation.Temps;

public class FonctionAbstraites {


 
    public double[] affine(double a, double b) {

        int length;
        double[] tableau_Puissance;
        if (Param.display_day) {
            length = Temps.NBMINUTESJOUR;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                tableau_Puissance[t] = a * t + b;

            }
        } else {
            length = Temps.NBJOURSANNEE;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                tableau_Puissance[t] = a * t + b;

            }
        }

        return tableau_Puissance;

    }



    public double[] constante(double a) {
        return affine(0,a);

    }

/**
 * 
 * @param amplitude
 * @param periode
 * @param rapportCyclique
 * @return un tableau representant des creneaux
 */

    public double[] pwm(double amplitude, int periode, double rapportCyclique) {

        int length;
        double[] tableau_Puissance;
        if (Param.display_day) {
            length = Temps.NBMINUTESJOUR;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                int k = (int) ( t/periode); // keme periode
                if (t>=periode*k && t<(periode*k+periode*rapportCyclique)){
                    tableau_Puissance[t] = amplitude;
                }
                else{
                    tableau_Puissance[t] = 0;
                }
                

            }
        } else {
            length = Temps.NBJOURSANNEE;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                int k = (int) ( t/periode); // keme periode
                if (t>=periode*k && t<(periode*k+periode*rapportCyclique)){
                    tableau_Puissance[t] = amplitude;
                }
                else{
                    tableau_Puissance[t] = 0;
                }
                

            }
        }

        return tableau_Puissance;

    }




    public double[] creneauDouble(double amplitude1, double amplitude2, double periode, double rapportCyclique) {

        int length;
        double[] tableau_Puissance;
        if (Param.display_day) {
            length = Temps.NBMINUTESJOUR;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                int k = (int) ( t/periode); // keme periode
                if (t>=periode*k && t<(periode*k+periode*rapportCyclique)){
                    tableau_Puissance[t] = amplitude1;
                }
                else{
                    tableau_Puissance[t] = amplitude2;
                }
                

            }
        } else {
            length = Temps.NBJOURSANNEE;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                int k = (int) ( t/periode); // keme periode
                if (t>=periode*k && t<(periode*k+periode*rapportCyclique)){
                    tableau_Puissance[t] = amplitude1;
                }
                else{
                    tableau_Puissance[t] = amplitude2;
                }
                

            }
        }

        return tableau_Puissance;

    }



    /**
     * 
     * @param amplitude
     * @param periode     en minutes si en un jour, en jour pour l'annee
     * @param Param.display_day True: in a day, False in a year
     * @return une sinusoide
     */
    public double[] sinusoide(double amplitude, double periode) {

        int length;
        double[] tableau_Puissance;
        if (Param.display_day) {
            length = Temps.NBMINUTESJOUR;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                tableau_Puissance[t] = amplitude * Math.sin(2 * Math.PI / periode * t);

            }
        } else {
            length = Temps.NBJOURSANNEE;
            tableau_Puissance = new double[length];
            for (int t = 0; t < length; t++) {
                tableau_Puissance[t] = amplitude * Math.sin(2 * Math.PI / periode * t);

            }
        }

        return tableau_Puissance;

    }



    public static void main(String[] args) {
        FonctionAbstraites fonction = new FonctionAbstraites();
        double[] cos = fonction.creneauDouble(1.1,0.8, 30,0.2);
        for (int t = 0; t < 130; t++) {
            System.out.println(cos[t]);

        }
        

    }

}
