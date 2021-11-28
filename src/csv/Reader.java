package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import consommateurs.*;
import producteurs.*;
import reseau.Cluster;

public class Reader {

    public static ArrayList<Cluster> readFiles(String filenameCluster, String filenameRoute,
            String filenameProdConstant,
            String filenameProdCondExt, String filenameAppConstant, String filenameAppCyclique,
            String filenameAppFreq) {

        // on lit le fichier decrivant l'indice, les coordonnes et le cluster producteur
        // de chaque cluster

        ArrayList<Cluster> clusters = Reader.readCluster(filenameCluster);

        // on lit le fichier route donnant le chemin pré déterminé entre chaque cluster
        // et son cluster producteur
        clusters = Reader.readRouteCluster(filenameRoute, clusters);

        // on lit les fichiers décrivant les producteurs
        clusters = Reader.readProducteurControle(filenameProdConstant, clusters);
        clusters = Reader.readProducteurCondExt(filenameProdCondExt, clusters);

        // on lit les fichiers décrivant les consommateurs
        clusters = Reader.readAppareilConstant(filenameAppConstant, clusters);
        clusters = Reader.readAppareilCyclique(filenameAppCyclique, clusters);
        clusters = Reader.readAppareilFrequentiel(filenameAppFreq, clusters);

        return clusters;
    }

    public static ArrayList<Cluster> readCluster(String filename) {

        ArrayList<Cluster> clusters = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");
                // indice du cluster
                int clusterNumber = Integer.parseInt(s[0]);

                // coordonnees du cluster
                double x = Double.parseDouble(s[1]);
                double y = Double.parseDouble(s[2]);

                // cluster qui produit de l'energie pour le cluster
                int clusterProducteur = Integer.parseInt(s[3]);

                clusters.add(new Cluster(clusterNumber, x, y, clusterProducteur));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;

    }

    public static ArrayList<Cluster> readRouteCluster(String filename, ArrayList<Cluster> clusters) {

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");

                // l'indice du cluster pour lequel on lit la route
                int clusterNumber = Integer.parseInt(s[0]);

                // la route de clusters
                String[] routeString = s[1].split(",");
                ArrayList<Cluster> route = new ArrayList<>();

                // on transforme la liste d'Integer en liste de Cluster pour former la route
                for (int i = 0; i < routeString.length; i++) {
                    // pour chaque valeur Integer du fichier csv route...
                    int indexClusterRoute = Integer.parseInt(routeString[i]);
                    // ...on cherche le cluster correspondant dans la liste de clusters
                    try{
                        int indexFound = Reader.findIndexCluster(indexClusterRoute, clusters);
                        route.add(clusters.get(indexFound));
                    } catch (IndexNotFoundException e) {
                        System.out.println("Index " +indexClusterRoute+ " Not Found in clusters.");
                    }
                    
                }
                // on ajoute la route au cluster correspondant
                try {
                    int clusterIndex = Reader.findIndexCluster(clusterNumber, clusters);
                    clusters.get(clusterIndex).setRoute(route);
                } catch (IndexNotFoundException e) {
                    System.out.println("Index " + clusterNumber + " Not Found in clusters.");
                }

            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;

    }

    public static ArrayList<Cluster> readProducteurControle(String filename, ArrayList<Cluster> clusters) {

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");

                ArrayList<Producteur> producteurs = new ArrayList<>();

                // l'indice du cluster pour lequel on lit la route
                int clusterNumber = Integer.parseInt(s[0]);

                int nb = Integer.parseInt(s[1]);
                double puissMax = Double.parseDouble(s[2]);

                for (int i = 0; i < nb; i++) {
                    producteurs.add(new ProducteurControle(puissMax));
                }
                try {
                    int clusterIndex = Reader.findIndexCluster(clusterNumber, clusters);
                    clusters.get(clusterIndex).addProducteurs(producteurs);
                } catch (IndexNotFoundException e) {
                    System.out.println("Index " + clusterNumber + " Not Found in clusters.");
                }

            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;
    }

    public static ArrayList<Cluster> readProducteurCondExt(String filename, ArrayList<Cluster> clusters) {
        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");

                ArrayList<Producteur> producteurs = new ArrayList<>();

                Double[] d = new Double[s.length];
                for (int i = 0; i < s.length; i++) {
                    d[i] = Double.parseDouble(s[i]);
                }

                int clusterNumber = d[0].intValue();
                int nb = d[1].intValue();

                double puissMax = d[2];
                HashMap<String, Double> moisActif = new HashMap<>(
                        Map.ofEntries(Map.entry("winter", d[3]), Map.entry("spring", d[4]), Map.entry("summer", d[5]),
                                Map.entry("fall", d[6])));
                Double[][] heureActif = new Double[][] {
                        { d[7], d[8], d[9], d[10], d[11], d[12], d[13], d[14], d[15], d[16], d[17], d[18] },
                        { d[19], d[20], d[21], d[22], d[23], d[24], d[25], d[26], d[27], d[28], d[29], d[30] } };

                for (int i = 0; i < nb; i++) {
                    producteurs.add(new ProducteurCondExt(puissMax, moisActif, heureActif));
                }

                try {
                    int clusterindex = Reader.findIndexCluster(clusterNumber, clusters);
                    clusters.get(clusterindex).addProducteurs(producteurs);
                } catch (IndexNotFoundException e) {
                    System.out.println("Index " + clusterNumber + " Not Found in clusters.");
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;
    }

    public static ArrayList<Cluster> readAppareilConstant(String filename, ArrayList<Cluster> clusters) {

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");
                ArrayList<Consommateur> appareils = new ArrayList<>();

                int clusterNumber = Integer.parseInt(s[0]);
                int nb = Integer.parseInt(s[1]);

                double puissMax = Double.parseDouble(s[2]);
                for (int i = 0; i < nb; i++) {
                    appareils.add(new AppareilConstant(puissMax));
                }

                try {
                    int clusterindex = Reader.findIndexCluster(clusterNumber, clusters);
                    clusters.get(clusterindex).addConsommateurs(appareils);
                } catch (IndexNotFoundException e) {
                    System.out.println("Index " + clusterNumber + " Not Found in clusters.");
                }

            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;
    }

    public static ArrayList<Cluster> readAppareilFrequentiel(String filename, ArrayList<Cluster> clusters) {
        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");

                ArrayList<Consommateur> appareils = new ArrayList<>();

                int clusterNumber = Integer.parseInt(s[0]);
                int nb = Integer.parseInt(s[1]);
                double puissMax = Double.parseDouble(s[2]);
                double frequence = Double.parseDouble(s[3]);
                double tempsUtilisation = Double.parseDouble(s[4]);
                Integer[] plageUtilisation = new Integer[] { Integer.parseInt(s[5]), Integer.parseInt(s[6]) };

                for (int i = 0; i < nb; i++) {
                    appareils.add(new AppareilFrequentiel(puissMax, frequence, tempsUtilisation, plageUtilisation));
                }

                try {
                    int clusterindex = Reader.findIndexCluster(clusterNumber, clusters);
                    clusters.get(clusterindex).addConsommateurs(appareils);
                } catch (IndexNotFoundException e) {
                    System.out.println("Index " + clusterNumber + " Not Found in clusters.");
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;
    }

    public static ArrayList<Cluster> readAppareilCyclique(String filename, ArrayList<Cluster> clusters) {
        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");

                ArrayList<Consommateur> appareils = new ArrayList<>();

                Double[] d = new Double[s.length];
                for (int i = 0; i < s.length; i++) {
                    d[i] = Double.parseDouble(s[i]);
                }

                int clusterNumber = d[0].intValue();
                int nb = d[1].intValue();

                double puissMax = d[2];
                HashMap<String, Double> moisActif = new HashMap<>(
                        Map.ofEntries(Map.entry("winter", d[3]), Map.entry("spring", d[4]), Map.entry("summer", d[5]),
                                Map.entry("fall", d[6])));
                Double[][] heureActif = new Double[][] {
                        { d[7], d[8], d[9], d[10], d[11], d[12], d[13], d[14], d[15], d[16], d[17], d[18] },
                        { d[19], d[20], d[21], d[22], d[23], d[24], d[25], d[26], d[27], d[28], d[29], d[30] } };

                for (int i = 0; i < nb; i++) {
                    appareils.add(new AppareilCyclique(puissMax, moisActif, heureActif));
                }

                int clusterindex;
                try {
                    clusterindex = Reader.findIndexCluster(clusterNumber, clusters);
                    clusters.get(clusterindex).addConsommateurs(appareils);
                } catch (IndexNotFoundException e) {
                    System.out.println("Index " + clusterNumber + " Not Found in clusters.");
                }

            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;
    }

    public static int findIndexCluster(int clusterNumber, ArrayList<Cluster> clusters) throws IndexNotFoundException {

        int index = 0;
        while (index < clusters.size()) {
            if (clusters.get(index).getClusterNumber() == clusterNumber) {
                return index;
            } else {
                index++;
            }
        }

        return -1;

    }

}
