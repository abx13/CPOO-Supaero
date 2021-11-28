package csv;

import java.util.ArrayList;

import consommateurs.Consommateur;
import reseau.Cluster;

public class mainTest {
    public static void main(String[] args) {
        ArrayList<Cluster> clusters = Reader.readFiles("src/csv/Cluster.csv", "src/csv/ClusterRoute.csv",
                "src/csv/ProducteurControle.csv", "src/csv/ProducteurCondExt.csv", "src/csv/AppareilConstant.csv",
                "src/csv/AppareilCyclique.csv", "src/csv/AppareilFrequentiel.csv");
        for (int i = 0; i < clusters.size(); i++) {
            System.out.print(clusters.get(i).getClusterNumber()+" ");
            System.out.print(clusters.get(i).getProducteurs().size()+" ");
            System.out.println(clusters.get(i).getConsommateurs().size());
        }
    }
}
