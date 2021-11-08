package simulation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Locale;

import consommateurs.Consommateur;
import consommateurs.Foyer;
import producteurs.Producteur;

/**
 * Cette classe contient des méthodes permettant d'afficher dans la console et
 * sauvegarder dans des fichiers CSV les rapports énergétiques journaliers et
 * annuels.
 */

public class SimulationVille {
    /**
     * Cette méthode permet de générer le bon nom de fichier pour le fichier CSV du
     * consommateur.
     * 
     * @param c le consommateur dont on veut retrouver le nom
     * @return "rapport"+nom du consommateur.
     */
    public static String filenameConsommateur(Consommateur c) {
        String name = c.getClass().getName().substring("consommateur.".length() + 1, c.getClass().getName().length());
        if (c.getClass() == Foyer.class) {
            Foyer f = (Foyer) c;
            name = f.getType().toString();
        }
        return "rapport." + name;
    }

    /**
     * Cette méthode permet de générer le bon nom de fichier pour le fichier CSV du
     * producteur.
     * 
     * @param p le producteur dont on veut retrouver le nom
     * @return "rapport"+nom du producteur.
     */
    public static String filenameProducteur(Producteur p) {
        String name = p.getClass().getName().substring("producteur.".length() + 1, p.getClass().getName().length());
        return "rapport." + name;
    }

    /**
     * Cette méthode permet d'afficher les rapports énergétiques dans la console.
     * 
     * @param data est le tableau comprenant les valeurs des rapports journaliers ou
     *             annuels.
     */
    public static void displayVille(double[][] data) {
        Locale.setDefault(new Locale("en", "US"));
        DecimalFormat df = new DecimalFormat("##0.0");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (j == 0) {
                    System.out.print(Integer.toString((int) data[i][j]));
                } else {
                    System.out.print(df.format(data[i][j]));
                }
                if (j + 1 < data[i].length) {
                    System.out.print(" ; ");
                } else {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Cette méthode permet de sauvegarder les rapports énérgétiques dans des
     * fichiers CSV.
     * 
     * @param filename est le nom que l'on veut donner au fichier csv généré.
     * @param data     est le tableau comprenant les valeurs des rapports
     *                 journaliers ou annuels.
     */
    public static void writeToCsvVille(String filename, double[][] data) {
        Locale.setDefault(new Locale("en", "US"));
        DecimalFormat df = new DecimalFormat("##0.0");
        try {
            PrintWriter writer = new PrintWriter(new File(filename));
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (j == 0) {
                        writer.write(Integer.toString((int) data[i][j]));
                    } else {
                        writer.write(df.format(data[i][j]));
                    }

                    if (j + 1 < data[i].length) {
                        writer.write(" ; ");
                    } else {
                        writer.println();
                    }
                }

            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
