package csv;

import java.util.ArrayList;

import reseau.Cluster;
import simulation.SimulationScenario;
import simulation.SimulationVille;

public class mainTest {
    public static void main(String[] args) {
        ArrayList<Cluster> clusters = Reader.readFiles("src/csv/Cluster.csv", "src/csv/ClusterRoute.csv",
                "src/csv/Producteurs.csv",  "src/csv/Consommateurs.csv");
        for (int i = 0; i < clusters.size(); i++) {
            System.out.print(clusters.get(i).getClusterNumber()+" ");
            System.out.print(clusters.get(i).getProducteurs().size()+" ");
            System.out.println(clusters.get(i).getConsommateurs().size());
            SimulationVille.writeToCsvVille("Cluster"+(i+1)+"Day22", clusters.get(i).computeMinute(22, 0.5));
            SimulationVille.writeToCsvVille("Cluster"+(i+1), clusters.get(i).computeDay(0.5));
            

        }

        SimulationScenario.playUnitaireConsommateur(clusters.get(4).getConsommateurs().get(0), 22);

        
       
        
        


    }
}
