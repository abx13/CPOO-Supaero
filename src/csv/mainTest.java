package csv;

import java.util.ArrayList;

import reseau.Cluster;
import simulation.SimulationVille;

public class mainTest {
    public static void main(String[] args) {
        ArrayList<Cluster> clusters = Reader.readFiles("src/csv/Cluster.csv", "src/csv/ClusterRoute.csv",
                "src/csv/Producteurs.csv",  "src/csv/Consommateurs.csv");
        for (int i = 0; i < clusters.size(); i++) {
            System.out.print(clusters.get(i).getClusterNumber()+" ");
            System.out.print(clusters.get(i).getProducteurs().size()+" ");
            System.out.println(clusters.get(i).getConsommateurs().size());
           
            

        }

        SimulationVille.displayVille(clusters.get(4).computeMinute(22, 0.5)); 
        //SimulationVille.displayVille(clusters.get(0).computeDay(0.5)); 
        
        


    }
}
