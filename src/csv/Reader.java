package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Modelconso.Param;
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
            String filenameParametres, String filenameProducteursConsommateurs) {

        // on lit le fichier decrivant l'indice, les coordonnes et le cluster producteur
        // de chaque cluster

        ArrayList<Cluster> clusters = Reader.readCluster(filenameCluster);

        // on lit le fichier route donnant le chemin pré déterminé entre chaque cluster
        // et son cluster producteur
        clusters = Reader.readRouteCluster(filenameRoute, clusters);

        // on lit les fichiers décrivant les producteurs
        Reader.readParametre(filenameParametres);

        // on lit les fichiers décrivant les consommateurs
        clusters = Reader.readProducteursConsommateurs(filenameProducteursConsommateurs, clusters);

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
     * cluster producteur associé, et d'ajouter cette route au cluster.
     * Cette méthode est appelée après la méthode pour lire les clusters car elle a
     * besoin d'avoir une liste préalablement établie de clusters, pour pouvoir les
     * identifier à un chemin.
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

    public static void readParametre(String filename){
        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");
                
                int jour = Integer.parseInt(s[0]);
                
                double rendement = Double.parseDouble(s[1]);
                double p_max_nucleaire = Double.parseDouble(s[2]);
                double diametre = Double.parseDouble(s[3]);
                double p_max_eolienne = Double.parseDouble(s[4]);
                double p_max_cafe = Double.parseDouble(s[5]);
                int nb_utilisation_jour_cafe = Integer.parseInt(s[6]);
                double p_frigo = Double.parseDouble(s[7]);
                double p_max_industrie1 = Double.parseDouble(s[8]);
                double p_max_industrie2 = Double.parseDouble(s[9]);
                double p_max_industrie3 = Double.parseDouble(s[10]);
                double p_max_radiateur = Double.parseDouble(s[11]);

                
                Param.setParam(jour, rendement, p_max_nucleaire, diametre, p_max_eolienne, p_max_cafe, nb_utilisation_jour_cafe,p_frigo, p_max_industrie1, p_max_industrie2, p_max_industrie3, p_max_radiateur);

            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cluster> readProducteursConsommateurs(String filename, ArrayList<Cluster> clusters) {

        try {
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()) {
                String res = sc.next();
                String[] s = res.split(";");
               

                ArrayList<Producteur> producteurs = new ArrayList<>();
                ArrayList<Consommateur> consommateurs = new ArrayList<>();

                int clusterNumber = Integer.parseInt(s[0]);

                for (int i = 1; i < s.length; i++) {
                    String[] element = s[i].split("/");
                    String name = element[0];
                    int nombre = Integer.parseInt(element[1]);

                    
                    if (name.equals("nucleaire") || name.equals("eolien") || name.equals("solaire")) {
                        for (int j = 0; j < nombre; j++) {
                            producteurs.add(new producteurs.Producteur(name));
                        }
                    } else {
                        if (name.equals("Petite_Industrie") || name.equals("Moyenne_Industrie") || name.equals("Grande_Industrie")) {
                            for (int j = 0; j < nombre; j++) {
                                consommateurs.add(new consommateurs.Consommateur(name));
                            }
                        }else{
                            if(name.equals("Foyer1")||name.equals("Foyer2")){
                                int nbFrigo = Integer.parseInt(element[2]);
                                int nbMachineCafe = Integer.parseInt(element[3]);
                                int nbRadiateur = Integer.parseInt(element[4]);
                            
                                for (int j = 0; j < nombre; j++) {
                                    consommateurs.add(new consommateurs.Foyer(name, nbFrigo, nbMachineCafe, nbRadiateur));
                                }
                            }
                        }

                    }
                }
                try {
                    int clusterIndex = Reader.findIndexCluster(clusterNumber, clusters);
                    clusters.get(clusterIndex).setProducteurs(producteurs);
                    clusters.get(clusterIndex).setConsommateurs(consommateurs);
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
     * Cette méthode permet de trouver l'indice dans l'arraylist de clusters d'un
     * cluster à partir de son numéro de cluster (attribut clusterNumber).
     * 
     * @param clusterNumber, le numéro du cluster cherché
     * @param clusters,      l'array comportant tous les cluster
     * @return index, l'index du cluster cherché dans l'arraylist
     * @throws IndexNotFoundException, exception levée si jamais le numéro du
     *                                 cluster n'est pas trouvé dans la liste de
     *                                 cluster.
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
     * Cette méthode permet de convertir chaque élément d'un tableau de String en un
     * élément Double dans un tableau de Double.
     * 
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
