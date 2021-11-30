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

/**
 * Cette classe permet de lire les fichiers CSV qui paramètrent notre modèle de
 * production et consommation d'énergie.
 * La lecture de ces fichiers est découpée en plusieurs étapes.
 * 
 * @author Anna Barraqué
 */

public class Reader {

    /**
     * Cette méthode est la méthode permettant de lire les fichiers des décrivant
     * les clusteurs, et leurs consommateurs et producteurs respectifs et crée ainsi
     * une liste de clusters complets.
     * 
     * @param filenameCluster,       fichier décrivant les clusters (numéro du
     *                               cluster, coordonnées x et y, cluster producteur
     *                               associé, )
     * @param filenameRoute,         fichier décrivant la route pour aller du
     *                               cluster à son cluster producteur
     * @param filenameProducteurs,   fichier décrivant tous les producteurs - ils
     *                               sont associés à un cluster
     * @param filenameConsommateurs, fichier décrivant tous les consommateurs - ils
     *                               sont associés à un cluster
     * @return clusters, une liste de clusters initialisés.
     */
    public static ArrayList<Cluster> readFiles(String filenameCluster, String filenameRoute,
            String filenameProducteurs, String filenameConsommateurs) {

        // on lit le fichier decrivant l'indice, les coordonnes et le cluster producteur
        // de chaque cluster

        ArrayList<Cluster> clusters = Reader.readCluster(filenameCluster);

        // on lit le fichier route donnant le chemin pré déterminé entre chaque cluster
        // et son cluster producteur
        clusters = Reader.readRouteCluster(filenameRoute, clusters);

        // on lit les fichiers décrivant les producteurs
        clusters = Reader.readProducteurs(filenameProducteurs, clusters);

        // on lit les fichiers décrivant les consommateurs
        clusters = Reader.readConsommateurs(filenameConsommateurs, clusters);

        return clusters;
    }

    /**
     * Cette méthode permet de lire le fichier décrivant les clusters (numéro du
     * cluster, coordonnées x et y, cluster producteur associé)
     * 
     * @param filename, le fichier csv correspondant
     * @return clusters, la liste de clusters créée avec ces paramètres.
     */
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

    /**
     * Cette méthode permet de lire le fichier décrivant la route d'un cluster à son
     * cluster producteur associé, et d'ajouter cette route au cluster
     * 
     * @param filename, le fichier csv correspondant
     * @param clusters, la liste de clusters préalablement établie avec le numéro du
     *                  cluster, ses coordonnées x et y et son cluster producteur
     *                  associé
     * @return clusters, la liste de clusters comptant également la route des
     *         clusters à leurs cluster producteurs associés
     */
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
                    try {
                        int indexFound = Reader.findIndexCluster(indexClusterRoute, clusters);
                        route.add(clusters.get(indexFound));
                    } catch (IndexNotFoundException e) {
                        System.out.println("Index " + indexClusterRoute + " Not Found in clusters.");
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

    /**
     * Cette méthode permet de lire le fichier de producteurs. Cette méthode appelle
     * ensuite d'autres méthodes qui sont spécifique à la lecture des différents
     * types de producteurs existants. Elle ajoute ensuite les producteurs lus au
     * cluster auquel ils sont associés.
     * 
     * @param filename, le fichier csv correspondant
     * @param clusters, la liste de clusters préalablement établie
     * @return clusters, la liste de clusters à laquelle on a rajouté les producteurs.
     */
    public static ArrayList<Cluster> readProducteurs(String filename, ArrayList<Cluster> clusters) {
        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");

                switch (s[0]) {
                    case "Nucleaire":
                        clusters = Reader.readProducteurControle(s, clusters);
                        break;
                    case "Eolien":
                        clusters = Reader.readProducteurCondExt(s, clusters);
                        break;
                    case "PV":
                        clusters = Reader.readProducteurCondExt(s, clusters);
                        break;
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;

    }
    /**
     * Cette méthode est la méthode spécifique à la lecture des producteurs dont la production est constante (Nucléaire).
     * @param s, le tableau de String lus par la méthode ReadProducteurs comportant les paramètres de ce producteur
     * @param clusters, la liste de clusters prélablement établie.
     * @return clusters, la liste de cluster à laquelle on a rajouté les producteurs constants.
     */
    public static ArrayList<Cluster> readProducteurControle(String[] s, ArrayList<Cluster> clusters) {

        ArrayList<Producteur> producteurs = new ArrayList<>();

        // l'indice du cluster pour lequel on lit la route
        int clusterNumber = Integer.parseInt(s[1]);

        int nb = Integer.parseInt(s[2]);

        double puissMax = Double.parseDouble(s[3]);

        for (int i = 0; i < nb; i++) {
            producteurs.add(new ProducteurControle(puissMax));
        }
        try {
            int clusterIndex = Reader.findIndexCluster(clusterNumber, clusters);
            clusters.get(clusterIndex).addProducteurs(producteurs);
        } catch (IndexNotFoundException e) {
            System.out.println("Index " + clusterNumber + " Not Found in clusters.");
        }

        return clusters;
    }
    /**
     * Cette méthode est la méthode spécifique à la lecture des producteurs dont la production dépend des conditions extérieures (Eolien ou PV).
     * @param s, le tableau de String lus par la méthode ReadProducteurs comportant les paramètres de ce producteur
     * @param clusters, la liste de clusters prélablement établie.
     * @return clusters, la liste de cluster à laquelle on a rajouté les producteurs dépendant des conditions extérieures.
     */
    public static ArrayList<Cluster> readProducteurCondExt(String[] s, ArrayList<Cluster> clusters) {

        ArrayList<Producteur> producteurs = new ArrayList<>();

        int clusterNumber = Integer.parseInt(s[1]);
        int nb = Integer.parseInt(s[2]);
        String[] paramString = s[3].split("/");

        double puissMax = Double.parseDouble(paramString[0]);
        Double[] saisons = Reader.toDoubles(paramString[1].split(","));
        Double[] heures = Reader.toDoubles(paramString[2].split(","));

        HashMap<String, Double> moisActif = new HashMap<>(
                Map.ofEntries(Map.entry("winter", saisons[0]), Map.entry("spring", saisons[1]),
                        Map.entry("summer", saisons[2]),
                        Map.entry("fall", saisons[3])));
        Double[][] heureActif = new Double[][] {
                { heures[0], heures[1], heures[2], heures[3], heures[4], heures[5], heures[6], heures[7], heures[8],
                        heures[9], heures[10], heures[11], heures[12] },
                { heures[13], heures[14], heures[15], heures[16], heures[17], heures[18], heures[19], heures[20],
                        heures[21], heures[22], heures[23] } };

        for (int i = 0; i < nb; i++) {
            producteurs.add(new ProducteurCondExt(puissMax, moisActif, heureActif));
        }

        try {
            int clusterindex = Reader.findIndexCluster(clusterNumber, clusters);
            clusters.get(clusterindex).addProducteurs(producteurs);
        } catch (IndexNotFoundException e) {
            System.out.println("Index " + clusterNumber + " Not Found in clusters.");
        }

        return clusters;
    }
    /**
     * Cette méthode permet de lire le fichier de consommateurs. Cette méthode appelle
     * ensuite d'autres méthodes qui sont spécifique à la lecture des différents
     * types de consommateurs existants. Elle ajoute ensuite les consommateurs lus au
     * cluster auquel ils sont associés.
     * 
     * @param filename, le fichier csv correspondant
     * @param clusters, la liste de clusters préalablement établie
     * @return clusters, la liste de clusters à laquelle on a rajouté les consommateurs.
     */
    public static ArrayList<Cluster> readConsommateurs(String filename, ArrayList<Cluster> clusters) {
        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");

                switch (s[0]) {
                    case "Frigo":
                        clusters = Reader.readAppareilConstant(s, clusters);
                        break;
                    case "Radiateur":
                        clusters = Reader.readAppareilCyclique(s, clusters);
                        break;
                    case "MachineCafe":
                        clusters = Reader.readAppareilFrequentiel(s, clusters);
                        break;
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return clusters;

    }
    /**
     * Cette méthode est la méthode spécifique à la lecture des consommateurs dont la consommation est constante (Frigo).
     * @param s, le tableau de String lus par la méthode ReadProducteurs comportant les paramètres de ce consommateur
     * @param clusters, la liste de clusters prélablement établie.
     * @return clusters, la liste de cluster à laquelle on a rajouté les consommateurs constants.
     */
    public static ArrayList<Cluster> readAppareilConstant(String[] s, ArrayList<Cluster> clusters) {

        ArrayList<Consommateur> appareils = new ArrayList<>();

        int clusterNumber = Integer.parseInt(s[1]);
        int nb = Integer.parseInt(s[2]);

        double puissMax = Double.parseDouble(s[3]);
        for (int i = 0; i < nb; i++) {
            appareils.add(new AppareilConstant(puissMax));
        }

        try {
            int clusterindex = Reader.findIndexCluster(clusterNumber, clusters);
            clusters.get(clusterindex).addConsommateurs(appareils);
        } catch (IndexNotFoundException e) {
            System.out.println("Index " + clusterNumber + " Not Found in clusters.");
        }

        return clusters;
    }

    /**
     * Cette méthode est la méthode spécifique à la lecture des consommateurs dont la consommation est fréquentielle (Machine à Café).
     * @param s, le tableau de String lus par la méthode ReadProducteurs comportant les paramètres de ce consommateur
     * @param clusters, la liste de clusters prélablement établie.
     * @return clusters, la liste de cluster à laquelle on a rajouté les consommateurs fréquentiels.
     */
    public static ArrayList<Cluster> readAppareilFrequentiel(String[] s, ArrayList<Cluster> clusters) {

        ArrayList<Consommateur> appareils = new ArrayList<>();

        int clusterNumber = Integer.parseInt(s[1]);
        int nb = Integer.parseInt(s[2]);
        String[] paramString = s[3].split("/");

        double puissMax = Double.parseDouble(paramString[0]);
        double frequence = Double.parseDouble(paramString[1]);
        double tempsUtilisation = Double.parseDouble(paramString[2]);
        String[] plageString = paramString[3].split(",");
        Integer[] plageUtilisation = new Integer[] { Integer.parseInt(plageString[0]),
                Integer.parseInt(plageString[1]) };

        for (int i = 0; i < nb; i++) {
            appareils.add(new AppareilFrequentiel(puissMax, frequence, tempsUtilisation, plageUtilisation));
        }

        try {
            int clusterindex = Reader.findIndexCluster(clusterNumber, clusters);
            clusters.get(clusterindex).addConsommateurs(appareils);
        } catch (IndexNotFoundException e) {
            System.out.println("Index " + clusterNumber + " Not Found in clusters.");
        }

        return clusters;
    }

    /**
     * Cette méthode est la méthode spécifique à la lecture des consommateurs dont la consommation est cyclique (Radiateur).
     * @param s, le tableau de String lus par la méthode ReadProducteurs comportant les paramètres de ce consommateur
     * @param clusters, la liste de clusters prélablement établie.
     * @return clusters, la liste de cluster à laquelle on a rajouté les consommateurs cycliques.
     */
    public static ArrayList<Cluster> readAppareilCyclique(String[] s, ArrayList<Cluster> clusters) {

        ArrayList<Consommateur> appareils = new ArrayList<>();

        int clusterNumber = Integer.parseInt(s[1]);
        int nb = Integer.parseInt(s[2]);
        String[] paramString = s[3].split("/");

        double puissMax = Double.parseDouble(paramString[0]);
        Double[] saisons = Reader.toDoubles(paramString[1].split(","));
        Double[] heures = Reader.toDoubles(paramString[2].split(","));

        HashMap<String, Double> moisActif = new HashMap<>(
                Map.ofEntries(Map.entry("winter", saisons[0]), Map.entry("spring", saisons[1]),
                        Map.entry("summer", saisons[2]),
                        Map.entry("fall", saisons[3])));
        Double[][] heureActif = new Double[][] {
                { heures[0], heures[1], heures[2], heures[3], heures[4], heures[5], heures[6], heures[7], heures[8],
                        heures[9], heures[10], heures[11], heures[12] },
                { heures[13], heures[14], heures[15], heures[16], heures[17], heures[18], heures[19], heures[20],
                        heures[21], heures[22], heures[23] } };
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

        return clusters;
    }

    /**
     * Cette méthode permet de trouver l'indice dans l'arraylist de clusters d'un cluster à partir de son numéro de cluster (attribut clusterNumber).
     * @param clusterNumber, le numéro du cluster cherché
     * @param clusters, l'array comportant tous les cluster
     * @return index, l'index du cluster cherché dans l'arraylist
     * @throws IndexNotFoundException, exception levée si jamais le numéro du cluster n'est pas trouvé dans la liste de cluster.
     */
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

    /**
     * Cette méthode permet de convertir chaque élément d'un tableau de String en un élément Double dans un tableau de Double.
     * @param s, le tableau de String que l'on veut convertir
     * @return doubles, le tableau converti en Double 
     */
    public static Double[] toDoubles(String[] s) {
        Double[] doubles = new Double[s.length];
        for (int i = 0; i < s.length; i++) {
            doubles[i] = Double.parseDouble(s[i]);
        }
        return doubles;

    }

}
