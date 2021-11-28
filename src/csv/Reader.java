package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import consommateurs.*;
import producteurs.*;


public class Reader {


    public static ArrayList<Cluster> readFile(String filename){
        
        ArrayList<Cluster> clusters = new ArrayList<>(); 
        int clusterIndex = 0;

        try{
            Scanner sc = new Scanner(new File(filename)); 
            String res = sc.next();
            String[] s = res.split(";");

            while (sc.hasNext()) {  

                switch (s[0]){

                    case "Cluster": 
                        clusters.add(Reader.readCluster(Arrays.copyOfRange(s, 1, s.length))); 
                        clusterIndex ++;
                        break;
                    
                    //Producteurs 
                    case "Nucleaire" : 
                        clusters.get(clusterIndex).setProducteur(Reader.readProducteurControle(Arrays.copyOfRange(s, 1, s.length)); 
                        break;

                    case "Eolien":
                        clusters.get(clusterIndex).setProducteur(Reader.readProducteurCondExt(Arrays.copyOfRange(s, 1, s.length)); 
                        break;

                    case "PV":
                        clusters.get(clusterIndex).setProducteur(Reader.readProducteurCondExt(Arrays.copyOfRange(s, 1, s.length)); 
                        break;

                    //Consommateurs
                    case "Frigo":
                        clusters.get(clusterIndex).setConsommateur(Reader.readAppareilConstant(Arrays.copyOfRange(s, 1, s.length)); 
                        break;

                    case "Radiateur":
                        clusters.get(clusterIndex).setConsommateur(Reader.readAppareilCyclique(Arrays.copyOfRange(s, 1, s.length)); 
                        break;

                    case "MachineCafe":
                        clusters.get(clusterIndex).setConsommateur(Reader.readAppareilFrequentiel(Arrays.copyOfRange(s, 1, s.length)); 
                        break;

                }

            }   
            sc.close(); 
            

        }catch(FileNotFoundException e){
            e.printStackTrace();
        } 
        
        return clusters; 

    }

    public static Cluster readCluster(String[] s){
        //indice du cluster
        int clusterNumber = Integer.parseInt(s[0]);
        
        //coordonnees du cluster
        double x = Double.parseDouble(s[1]);
        double y = Double.parseDouble(s[2]);
        
        //cluster qui produit de l'energie pour le cluster
        int clusterProducteur = Integer.parseInt(s[3]);
       
        //route pour arriver au cluster producteur
        // si le cluster est aussi le cluster producteur il n'y a pas de route, sinon il en a une
        ArrayList<Cluster> route = null;
        if(s.length == 5){
            String[] routeString = s[4].split(",");
            for (int i=0; i<routeString.length; i++){
                route.add(Integer.parseInt(routeString[i]));
            }
        }
        
        return new Cluster(clusterNumber, x, y, clusterProducteur, route);

    }


    public static ArrayList<Producteur> readProducteurControle(String[] s){
        ArrayList<Producteur> producteurs = new ArrayList<>(); 
        
        int nb = Integer.parseInt(s[0]);
        double puissMax = Double.parseDouble(s[1]);
        
        for(int i=0; i<nb; i++){
            producteurs.add(new ProducteurControle(puissMax));
        }
             
        return producteurs; 
    }

    public static ArrayList<Producteur> readProducteurCondExt(String[] s){
        ArrayList<Producteur> producteurs = new ArrayList<>(); 
        
        Double[] d = new Double[s.length];
        for(int i = 0; i<s.length; i++){
            d[i] = Double.parseDouble(s[i]);
        }

        int nb = d[0].intValue();

        double puissMax = d[1];
        HashMap<String, Double> moisActif = new HashMap<>(Map.ofEntries(Map.entry("winter", d[2] ), Map.entry("spring", d[3]), Map.entry("summer", d[4]),
            Map.entry("fall", d[5])));
        Double[][] heureActif = new Double[][] { { d[6], d[7], d[8], d[9], d[10], d[11], d[12], d[13], d[14], d[15], d[16], d[17] },
            { d[18], d[19], d[20], d[21], d[22], d[23], d[24], d[25], d[26], d[27], d[28], d[29] } };
        
               
        for(int i=0; i<nb; i++){
            producteurs.add(new ProducteurCondExt(puissMax, moisActif, heureActif));
        }    
        return producteurs; 
    }
    
    
    public static ArrayList<Consommateur> readAppareilConstant(String[] s){
        ArrayList<Consommateur> appareils = new ArrayList<>();
        
        int nb = Integer.parseInt(s[0]);   
        
        double puissMax = Double.parseDouble(s[1]);
        for(int i=0; i<nb ; i++){
            appareils.add(new AppareilConstant(puissMax));
        }
             
        return appareils; 
    }
    
    public static ArrayList<Consommateur> readAppareilFrequentiel(String[] s){
        ArrayList<Consommateur> appareils = new ArrayList<>(); 
        
        int nb = Integer.parseInt(s[0]);
        double puissMax = Double.parseDouble(s[1]); 
        double frequence = Double.parseDouble(s[2]);
        double tempsUtilisation = Double.parseDouble(s[3]);
        Integer[] plageUtilisation = new Integer[]{Integer.parseInt(s[4]), Integer.parseInt(s[5])};
        
        for (int i=0; i<nb ;i++){
            appareils.add(new AppareilFrequentiel(puissMax, frequence, tempsUtilisation, plageUtilisation));
        }
        
        return appareils; 
    }


    public static ArrayList<Consommateur> readAppareilCyclique(String[] s){
        ArrayList<Consommateur> appareils = new ArrayList<>(); 
        
        Double[] d = new Double[s.length];
        for(int i = 0; i<s.length; i++){
            d[i] = Double.parseDouble(s[i]);
        }

        int nb = d[0].intValue();

        double puissMax = d[1];
        HashMap<String, Double> moisActif = new HashMap<>(Map.ofEntries(Map.entry("winter", d[2] ), Map.entry("spring", d[3]), Map.entry("summer", d[4]),
            Map.entry("fall", d[5])));
        Double[][] heureActif = new Double[][] { { d[6], d[7], d[8], d[9], d[10], d[11], d[12], d[13], d[14], d[15], d[16], d[17] },
            { d[18], d[19], d[20], d[21], d[22], d[23], d[24], d[25], d[26], d[27], d[28], d[29] } };
        
        for (int i = 0; i<nb; i++){
            appareils.add(new AppareilCyclique(puissMax, moisActif, heureActif));
        }
              
        
        return appareils; 
    }

    

   

    

    /*public static ArrayList<Cluster> setDistanceCluster(ArrayList<Cluster> clusters, String filenameDistance){
        double[][] distanceCluster = Reader.readDistanceCluster(filenameDistance);
        //pour ameliorer faire avec clusterNumber plus que ordre de l'ArrayList
        for (int i = 0; i<clusters.length; i++){
            Cluster cluster = clusters.get(i);
            int producteur = cluster.getProducteur();
            int[] route = cluster.getRoute();
            double distance = 0;
            for(int j=0; j<route.length-1; j++){
                distance += distanceCluster[j][j+1];
            }
            clusters.get(i).setDistance(distance);
        }
    }*/

    /*public static double[][] readDistanceCluster(String filename){
        double[][] distanceCluster = null; 
        try{
            Scanner sc = new Scanner(new File(filename)); 
            String res = sc.next();
            String[] s = res.split(";");

            int nbCluster = s.length;
            distanceCluster = new double[nbCluster][nbCluster];  
            
            int i = 0;
            for(int j = 0; j<nbCluster; j++){
                distanceCluster[i][j] = Double.parseDouble(s[i]);
            }
            
            while (sc.hasNext()) {  
                res = sc.next();
                s = res.split(";");
                
                i++;
                for(int j = 0; j<nbCluster; j++){
                    distanceCluster[i][j] = Double.parseDouble(s[i]);
                }
            }          
            sc.close(); 

        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
            e.printStackTrace();
        }  
        return distanceCluster; 
    }*/
    

}

