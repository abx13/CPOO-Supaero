package Modelconso;

import java.awt.EventQueue;

import simulation.Temps;

public class Producteurs {

    private String name;
    private String title;
    private double[] table;
    
    
     public Producteurs(){
        
    }

    /**
     * 
     * @param nom eolien nucleaire solaire
     */
    public Producteurs(String nom) {

        if (nom.equals("eolien")) {

            setName("eolien");

            double limit_Betz = 0.59; // limite theorique du rendement

            double vit_vent = Ventjournee.vit_Ventjournee(Param.jour); // en m/s
            double rendement = 0.8 * limit_Betz;
            double p_Max = rendement * 0.5 * 1.2 * 3.14 * Param.diametre * Param.diametre / 4 * vit_vent * vit_vent
                    * vit_vent / 1000000; // on definit la puissance de l'eolienne en MW

            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();
            if (Param.display_day) {
                double[] tableau_puissance = fonction.sinusoide(p_Max * 1.1, Temps.NBMINUTESJOUR);

                op.ecretage(tableau_puissance, p_Max);
                op.ecretage(tableau_puissance, Param.p_max_eolienne);
                op.decalage_tempo(tableau_puissance, 400);
                op.bruitBlanc(tableau_puissance, 5);
                op.seulement_positif(tableau_puissance);
                op.ecretage(tableau_puissance, p_Max); // on refait les ecretage pour que le bruit blanc ne depasse pas
                                                       // la puissance max autorisé
                op.ecretage(tableau_puissance, Param.p_max_eolienne);

                setTitle("Puissance d'une Eolienne sur une journée");
                setTable(tableau_puissance);
            } else {
                double[] tableau_puissance = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {
                    double vit_vent_jour = Ventjournee.vit_Ventjournee(j);
                    double p_max_jour = rendement * 0.5 * 1.2 * 3.14 * Param.diametre * Param.diametre / 4
                            * vit_vent_jour * vit_vent_jour * vit_vent_jour / 1000000; // on definit la puissance de
                                                                                       // l'eolienne
                    tableau_puissance[j] = 0.3 * p_max_jour;// marche à peu près 30% du temps d'où le facteur 0.3: très
                                                            // approximatif

                }
                op.ecretage(tableau_puissance, Param.p_max_eolienne);

                setTitle("Puissance d'une Eolienne sur une année");
                setTable(tableau_puissance);
            }

        }

        if (nom.equals("nucleaire")) {

            setName("nucleaire");

            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();
            double[] tableau_puissance = fonction.creneauDouble(Param.p_max_nucl, 0.8, 30, 0.4);

            op.ecretage(tableau_puissance, 999);

            setTitle("nucleaire");
            setTable(tableau_puissance);

            if (!Param.display_day) {

                double[] tableau_pui = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {

                    tableau_pui[j] = Param.p_max_nucl;

                }

                setTitle("Puissance d'une centrale sur une année");
                setTable(tableau_puissance);
            }
        }

        if (nom.equals("solaire")) {

            setName("solaire");

            double ensoleillement = Soleil.ensoleillement(Param.jour); // en m/s

            double p_Max = Param.rendement_sol * ensoleillement; // definition de la puissance max d'un metre carré de
                                                                 // panneau solaire

            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();

            if (Param.display_day) {
                double[] tableau_puissance = fonction.sinusoide(p_Max * 1.15, Temps.NBMINUTESJOUR);

                op.seulement_positif(tableau_puissance);
                op.ecretage(tableau_puissance, p_Max);
                op.decalage_tempo(tableau_puissance, 400);

                setTitle("Puissance d'un mètre carré de panneau sur une journée");
                setTable(tableau_puissance);
            } else {
                double[] tableau_puissance = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {
                    double ensoleillement_jour = Soleil.ensoleillement(j);
                    double p_max_jour = Param.rendement_sol * ensoleillement_jour / 1000000;// on definit la puissance
                                                                                            // sur
                                                                                            // une journée d'un metre
                                                                                            // carré
                                                                                            // de panneau
                    tableau_puissance[j] = 0.4 * p_max_jour;// marche à peu près 40% du temps d'où le facteur 0.4: très
                                                            // approximatif

                }

                setTitle("Puissance d'un metre carré de panneau solaire sur une année");
                setTable(tableau_puissance);

            }
        }

    }

    public void display() {

        String legende_x = "temps en min";
        if (!Param.display_day) {
            legende_x = "temps en jour";

        }

        String legende_Y = "Puissance en MW";
        String legende_X = legende_x;

        EventQueue.invokeLater(() -> {

            var ex = new LineChartEx(table, legende_X, legende_Y, title);
            ex.setVisible(true);

        });

    }

    public void display(double[] tableau, String titre) {

        String legende_x = "temps en min";
        if (!Param.display_day) {
            legende_x = "temps en jour";

        }

        String legende_Y = "Puissance en MW";
        String legende_X = legende_x;

        EventQueue.invokeLater(() -> {

            var ex = new LineChartEx(tableau, legende_X, legende_Y, titre);
            ex.setVisible(true);

        });

    }

    public String getName() {
        return this.title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double[] getTable() {
        return this.table;
    }

    public void setTable(double[] table) {
        this.table = table;
    }

    public static void main(String[] args) {
        Producteurs eolienne = new Producteurs("eolien");

        eolienne.display();

    }

}
