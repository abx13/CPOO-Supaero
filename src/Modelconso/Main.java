package Modelconso;

import java.util.ArrayList;

import csv.Reader;
import reseau.Cluster;
import simulation.SimulationVille;

public class Main {
    public static void main(String[] args) {
        ArrayList<Cluster> clusters = Reader.readFiles("src/csv/Cluster.csv", "src/csv/ClusterRoute.csv",
                "src/csv/Parametres.csv",  "src/csv/ProducteursConsommateurs.csv");
        System.out.println(Param.jour);   

        double[] puissance = clusters.get(0).getConsommateurs().get(0).getTable();

        if(puissance == null){
            System.out.println(true);
        }

        for(int i=0; i<puissance.length; i++){
            //System.out.println(puissance[i]);
        }
        
        //double[][] puissance = clusters.get(0).getTable();
        //SimulationVille.displayVille(puissance);
       
        
        


    }
}