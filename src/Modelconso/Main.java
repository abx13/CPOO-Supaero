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
       
        
        


    }
}