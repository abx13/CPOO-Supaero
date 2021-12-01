package reseau;

import java.util.ArrayList;

import producteurs.*;
import consommateurs.*;
import simulation.Ville;
import simulation.Temps;

public class Cluster extends Ville {

    private int clusterNumber; // numéro associé à ce cluster consommateur
    private double x; // lattitude du cluster
    private double y; // longitude du cluster
    private int clusterProducteur; // numéro associé au cluster producteur de ce cluster consommateur
    private ArrayList<Cluster> route; // liste des clusters (possiblement vide) que doit emprunter l'énergie pour
                                      // aller du cluster producteur au cluster consommateur

    /**
     * 
     * @param clusterNumber     numéro associé à ce cluster consommateur
     * @param x                 lattitude du cluster
     * @param y                 longitude du cluster
     * @param clusterProducteur numéro associé au cluster producteur de ce cluster
     *                          consommateur
     * @param routes            liste des clusters (possiblement vide) que doit
     *                          emprunter l'énergie pour aller du cluster producteur
     *                          au cluster consommateur
     * 
     */
    public Cluster(int clusterNumber, double x, double y, int clusterProducteur, ArrayList<Cluster> routes) {
        super();
        this.clusterNumber = clusterNumber;
        this.x = x;
        this.y = y;
        this.clusterProducteur = clusterProducteur;
        this.route = routes;
    }

    /**
     * 
     * @return x la longitude du cluster
     */
    public double getX() {
        return x;
    }

    /**
     * 
     * @return y la lattitude du cluster
     */
    public double getY() {
        return y;
    }

    /**
     * 
     * @return la distance parcourue par l'energie entre le cluster producteur et le
     *         cluster consommateur
     */
    public double distance() {
        double dist = 0;
        for (int i = 0; i < this.route.size() - 1; i++) {
            double x1 = route.get(i).getX();
            double x2 = route.get(i + 1).getX();
            double y1 = route.get(i).getY();
            double y2 = route.get(i + 1).getY();
            dist += Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
            ;
        }
        return dist;
    }

    /**
     * 
     * @param coefPerte coefficient des pertes Joule
     * @return la perte energetique associée au trajet entre notre cluster
     *         consommateur et son producteur
     */
    public double calculPerte(double coefPerte) {
        double calcul = 0;
        calcul = distance() * coefPerte;
        return calcul;
    }

    public double[][] computeMinute(int journee, double coefPerte) {
        double[][] clusterMinute = new double[Temps.NBMINUTESJOUR][6];
        double[][] productionMinute = super.computeMinute(journee);
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            clusterMinute[i][0] = productionMinute[i][0];
            clusterMinute[i][1] = productionMinute[i][1];
            clusterMinute[i][2] = productionMinute[i][2];
            clusterMinute[i][3] = productionMinute[i][3];
            clusterMinute[i][4] = productionMinute[i][4];
            clusterMinute[i][5] = calculPerte(coefPerte);
        }

        return clusterMinute;
    }

    public double[][] computeDay(double coefPerte) {
        double[][] clusterDay = new double[Temps.NBJOURSANNEE][6];
        double[][] productionDay = super.computeDay();
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            clusterDay[i][0] = productionDay[i][0];
            clusterDay[i][1] = productionDay[i][1];
            clusterDay[i][2] = productionDay[i][2];
            clusterDay[i][3] = productionDay[i][3];
            clusterDay[i][4] = productionDay[i][4];
            clusterDay[i][5] = calculPerte(coefPerte);
        }

        return clusterDay;
    }

}
