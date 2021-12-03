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

    public Cluster(int clusterNumber, double x, double y, int clusterProducteur) {
        super();
        this.clusterNumber = clusterNumber;
        this.x = x;
        this.y = y;
        this.clusterProducteur = clusterProducteur;
        this.route = new ArrayList<Cluster>();
    }

    public void setRoute(ArrayList<Cluster> route) {
        this.route = route;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getClusterNumber() {
        return this.clusterNumber;
    }

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
        for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
            clusterDay[i][0] = productionDay[i][0];
            clusterDay[i][1] = productionDay[i][1];
            clusterDay[i][2] = productionDay[i][2];
            clusterDay[i][3] = productionDay[i][3];
            clusterDay[i][4] = productionDay[i][4];
            clusterDay[i][5] = calculPerte(coefPerte);
        }

        return clusterDay;
    }

    public double[][] computeMinute(int journee, int coef) {
        double[][] puissanceMinuteClusters = new double[this.clusters.length][6];
        //on calcule les puissancesMinute de tous les clusters, qu'on sauvegarde dans un tableau
        for (int i = 0; i < this.clusters.length; i++) {
            puissanceMinuteClusters[i] = this.clusters.get(i).computeMinute(journee, coef);
        }
        //on somme pour chaque cluster
        double[][] clustersMinute = new double[Temps.NBMINUTESJOUR][6];
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            for (int j = 0; j < this.clusters.length; i++) {
                clustersMinute[i][0] += puissanceMinuteClusters[j][0];
                clustersMinute[i][1] += puissanceMinuteClusters[j][1];
                clustersMinute[i][2] += puissanceMinuteClusters[j][2];
                clustersMinute[i][3] += puissanceMinuteClusters[j][3];
                clustersMinute[i][4] += puissanceMinuteClusters[j][4];
                clustersMinute[i][5] += puissanceMinuteClusters[j][5];
            }

        }

        return clustersMinute;

    }

    public double[][] computeDay(int coef) {
        double[][] puissanceDayClusters = new double[this.clusters.length][6];
        //on calcule les puissancesDay de tous les clusters, qu'on sauvegarde dans un tableau
        for (int i = 0; i < this.clusters.length; i++) {
            puissanceDayClusters[i] = this.clusters.get(i).computeDay(coef);
        }
        //on somme pour chaque cluster
        double[][] clustersDay = new double[Temps.NBJOURSANNEE][6];
        for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
            for (int j = 0; j < this.clusters.length; i++) {
                clustersDay[i][0] += puissanceDayClusters[j][0];
                clustersDay[i][1] += puissanceDayClusters[j][1];
                clustersDay[i][2] += puissanceDayClusters[j][2];
                clustersDay[i][3] += puissanceDayClusters[j][3];
                clustersDay[i][4] += puissanceDayClusters[j][4];
                clustersDay[i][5] += puissanceDayClusters[j][5];
            }

        }

        return clustersDay;

    }

}
