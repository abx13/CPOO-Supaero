package csv;

import java.util.ArrayList;

import consommateurs.Consommateur;

public class mainTest {
    public static void main(String[] args) {
        ArrayList<Consommateur> appareils = Reader.readAppareilCyclique("/src/consommateurs/csv/AppareilCyclique.csv");
        System.out.println(appareils.size());
    }
}
