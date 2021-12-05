package Modelconso;

import java.util.ArrayList;

import consommateurs.Foyer;
import csv.Reader;
import reseau.Cluster;
import simulation.SimulationVille;

public class Main {
    public static void main(String[] args) {
        ArrayList<Cluster> clusters = Reader.readFiles("src/csv/Cluster.csv", "src/csv/ClusterRoute.csv",
                "src/csv/Parametres.csv",  "src/csv/ProducteursConsommateurs.csv");
        //System.out.println(Param.jour);   

        double[] puissance = null;
        /*for(int i=0; i<clusters.get(0).getConsommateurs().size(); i++){
            System.out.println(i);
             
        }*/

        Foyer foyer = (Foyer) clusters.get(0).getConsommateurs().get(6);
        
         

        System.out.println(foyer.getAppareils().get(2).getName());
        puissance = foyer.getAppareils().get(2).getTable();

        for(int i=0; i<puissance.length; i++){
           // System.out.println(puissance[i]);
        }
        
        //double[][] puissanceVille = clusters.get(0).getTable();
        //SimulationVille.displayVille(puissanceVille);
       
        
        


    }
}