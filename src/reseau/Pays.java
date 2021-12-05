package reseau;

import java.util.ArrayList;

import simulation.Ville;
import simulation.Temps;
import reseau.Cluster;

/**
 * Cette classe permet de simuler un pays composé de plusieurs clusters
 * consommateurs d'énergie et ayant un producteur chacun. Elle calcule la
 * production et consommation d'énergie pour chaque minute de la journée donnée
 * ainsi que pour chaque jour de l'année.
 * 
 * @author MG
 */
public class Pays {

    private String nom; // nom du pays
    ArrayList<Cluster> clusters; // liste des villes de ce pays

    public Pays(ArrayList<Cluster> clusters) {
        this.clusters = clusters;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite et
     * consommée dans le pays pour chaque minute de la journée donnée.
     * 
     * @param journee
     * @param coefPerte
     * @return un tableau par cluster de tableau de 6 colonnes de puissance
     *         consommée et/ou produite pour chaque minute ainsi que les pertes dues
     *         au transport de l'énergie
     */
    public double[][] computeMinute(int journee, double coefPerte) {
        double[][][] puissanceMinuteClusters = new double[this.clusters.size()][Temps.NBMINUTESJOUR][6];
        
        // on calcule les puissancesMinute de tous les clusters, qu'on sauvegarde dans
        // un grand tableau : [Cluster][Temps][Colonnes demandées]
        for (int j = 0; j < this.clusters.size(); j++) {
            for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
                puissanceMinuteClusters[j][i][0] = this.clusters.get(j).computeMinute(journee, coefPerte)[i][0];
                puissanceMinuteClusters[j][i][1] = this.clusters.get(j).computeMinute(journee, coefPerte)[i][1];
                puissanceMinuteClusters[j][i][2] = this.clusters.get(j).computeMinute(journee, coefPerte)[i][2];
                puissanceMinuteClusters[j][i][3] = this.clusters.get(j).computeMinute(journee, coefPerte)[i][3];
                puissanceMinuteClusters[j][i][4] = this.clusters.get(j).computeMinute(journee, coefPerte)[i][4];
                puissanceMinuteClusters[j][i][5] = this.clusters.get(j).computeMinute(journee, coefPerte)[i][5];
            }
        }

        /*for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 10; i++) {
                for (int k = 0; i < 6; i++) {
                    System.out.println(puissanceMinuteClusters[j][i][k]);
                }
            }
            System.out.println('\n');
        }
        */

        // on somme pour chaque cluster
        double[][] clustersMinute = new double[Temps.NBMINUTESJOUR][6];
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            for (int j = 0; j < this.clusters.size(); i++) {
                clustersMinute[i][0] = puissanceMinuteClusters[j][i][0];
                clustersMinute[i][1] += puissanceMinuteClusters[j][i][1];
                clustersMinute[i][2] += puissanceMinuteClusters[j][i][2];
                clustersMinute[i][3] += puissanceMinuteClusters[j][i][3];
                clustersMinute[i][4] += puissanceMinuteClusters[j][i][4];
                clustersMinute[i][5] += puissanceMinuteClusters[j][i][5];
            }

        }

        return clustersMinute;

    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite et
     * consommée dans le cluster pour chaque jour de l'année.
     * 
     * @param coefPerte
     * @return un tableau par cluster de tableau de 6 colonnes de puissance
     *         consommée et/ou produite pour chaque journée ainsi que les valeurs
     *         cumulées sur la journée et les pertes dues au transport de l'énergie
     */
    public double[][] computeDay(double coefPerte) {
        double[][][] puissanceJourClusters = new double[this.clusters.size()][Temps.NBJOURSANNEE][6];
        // on calcule les puissancesJour de tous les clusters, qu'on sauvegarde dans un
        // grand tableau : [Cluster][Temps][Colonnes demandées]
        for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
            for (int j = 0; j < this.clusters.size(); j++) {
                puissanceJourClusters[j][i][0] = this.clusters.get(j).computeDay(coefPerte)[i][0];
                puissanceJourClusters[j][i][1] = this.clusters.get(j).computeDay(coefPerte)[i][1];
                puissanceJourClusters[j][i][2] = this.clusters.get(j).computeDay(coefPerte)[i][2];
                puissanceJourClusters[j][i][3] = this.clusters.get(j).computeDay(coefPerte)[i][3];
                puissanceJourClusters[j][i][4] = this.clusters.get(j).computeDay(coefPerte)[i][4];
                puissanceJourClusters[j][i][5] = this.clusters.get(j).computeDay(coefPerte)[i][5];
            }
        }
        // on somme pour chaque cluster
        double[][] clustersJour = new double[Temps.NBMINUTESJOUR][6];
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            for (int j = 0; j < this.clusters.size(); i++) {
                clustersJour[i][0] = puissanceJourClusters[j][i][0];
                clustersJour[i][1] += puissanceJourClusters[j][i][1];
                clustersJour[i][2] += puissanceJourClusters[j][i][2];
                clustersJour[i][3] += puissanceJourClusters[j][i][3];
                clustersJour[i][4] += puissanceJourClusters[j][i][4];
                clustersJour[i][5] += puissanceJourClusters[j][i][5];
            }

        }

        return clustersJour;

    }

}
