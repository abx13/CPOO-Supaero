package Modelconso;

import java.awt.EventQueue;
import simulation.Temps;

public class Consommateurs {
    private String name;
    private String title;
    private double[] table;

    public Consommateurs(String nom) {

        if (nom.equals("cafe")) {

            setName("Machine a cafe");

            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();
            int periode = 3;
            double rapportCyclique = 0.2;
            double[] tableau_puissance = fonction.pwm(Param.p_max_cafe, periode, rapportCyclique);

            int index_troncage = periode * Param.nb_utilisation_jour_cafe;

            op.troncage_tempo(tableau_puissance, index_troncage);
            op.decalage_tempo(tableau_puissance, 300);

            setName("Machine a cafe");
            setTable(tableau_puissance);

            if (!Param.display_day) {

                double[] tableau_pui = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {

                    tableau_pui[j] = 0.01 * Param.p_max_cafe; // marche 1% du temps dans la journée

                }

                setName("Puissance d'une machine à cafe sur une année");
                setTable(tableau_puissance);

            }

        }





        if (nom.equals("radiateur")) {
            setName("radiateur");

            
            double p_Max_jour = 5;
           
        

            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();

            if (Param.display_day) {
                int mois = simulation.Temps.dayToMonth(Param.jour);
                String saison = simulation.Temps.monthToSeason(mois);
                if(saison=="summer"){
                     p_Max_jour = 0;
                }
                if(saison=="winter"){
                     p_Max_jour = Param.p_max_radiateur;
                }
                if(saison=="fall"){
                     p_Max_jour = 0.3*Param.p_max_radiateur;
                }
                if(saison=="spring"){
                     p_Max_jour = 0.3*Param.p_max_radiateur;
                }
                else{
                    throw new IllegalArgumentException("message");
                }
            
                double[] tableau_puissance = fonction.pwm(p_Max_jour , Temps.NBMINUTESJOUR, 0.5);


                op.decalage_tempo(tableau_puissance, 400);

                setName("Puissance d'un radiateur sur une journée");
                setTable(tableau_puissance);

            } else {
                double[] tableau_puissance = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {

                    int mois = simulation.Temps.dayToMonth(j);
                    String saison = simulation.Temps.monthToSeason(mois);
                    if(saison=="summer"){
                         p_Max_jour = 0;
                    }
                    if(saison=="winter"){
                         p_Max_jour = Param.p_max_radiateur;
                    }
                    if(saison=="fall"){
                         p_Max_jour = 0.3*Param.p_max_radiateur;
                    }
                    if(saison=="spring"){
                         p_Max_jour = 0.3*Param.p_max_radiateur;
                    }
                    
                    
                                                                                      
                    tableau_puissance[j] = 0.5*p_Max_jour; // 0.5 car on eteint la nuit

                }
                

                setName("Puissance d'un radiateur sur une année");
                setTable(tableau_puissance);
            }

        }
        if (nom.equals("frigo")) {
            setName("frigo");


            FonctionAbstraites fonction = new FonctionAbstraites();
            
            
            double[] tableau_puissance = fonction.constante(Param.p_frigo);


            setName("frigo");
            setTable(tableau_puissance);

            if (!Param.display_day) {
              

                setName("Consomation d'un frigo sur une année");
                setTable(tableau_puissance);

            }


            
        }
        if (nom.equals("industrie1")) {
            setName("industrie 1");

            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();
            int periode = Temps.NBMINUTESJOUR/7;
            double rapportCyclique = 0.5;
            

            if (Param.display_day) {
                double[] tableau_puissance = fonction.sinusoide(Param.p_max_industrie1, periode);

            op.decalage_tempo(tableau_puissance, 300);
            op.abs(tableau_puissance);
            op.bruitBlanc(tableau_puissance, 10);

            setName("Industrie Type 1");
            setTable(tableau_puissance);
            }
            else{

                double[] tableau_pui = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {

                    tableau_pui[j] = rapportCyclique * Param.p_max_industrie1; // marche pas toute la journee

                }

                setName("Consomation d'une industrie type 1 sur une année");
                setTable(tableau_pui);

            }

        }
    

        if (nom.equals("industrie2")) {
            setName("industrie 2");

            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();
            int periode = Temps.NBMINUTESJOUR;
            double rapportCyclique = 0.6;
            

            if (Param.display_day) {
                double[] tableau_puissance = fonction.pwm(Param.p_max_industrie2, periode, rapportCyclique);

            op.decalage_tempo(tableau_puissance, 800);

            setName("Industrie Type 2");
            setTable(tableau_puissance);
            }
            else{

                double[] tableau_pui = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {

                    tableau_pui[j] = rapportCyclique * Param.p_max_industrie2; // marche pas toute la journee

                }

                setName("Consomation d'une industrie type 2 sur une année");
                setTable(tableau_pui);

            }


        }
        if (nom.equals("industrie3")) {

            setName("industrie 3");
            FonctionAbstraites fonction = new FonctionAbstraites();
            Operations op = new Operations();
            int periode = Temps.NBMINUTESJOUR/4;
            double rapportCyclique = 0.7;
            if (Param.display_day) {
            double[] tableau_puissance = fonction.pwm(Param.p_max_industrie3, periode, rapportCyclique);

            op.decalage_tempo(tableau_puissance, 400);
            op.bruitBlanc(tableau_puissance, 50);

            setName("Industrie Type 3");
            setTable(tableau_puissance);
            }
            else{

            

                double[] tableau_pui = new double[Temps.NBJOURSANNEE];
                for (int j = 0; j < Temps.NBJOURSANNEE; j++) {

                    tableau_pui[j] = rapportCyclique * Param.p_max_industrie3; // marche pas toute la journee

                }

                setName("Consomation d'une indusytie type 3 sur une année");
                setTable(tableau_pui);

            }
        }


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

            var ex = new LineChartEx(tableau, legende_X, legende_Y, titre );
            ex.setVisible(true);

        });

    }

    public static void main(String[] args) {
        Consommateurs cons = new Consommateurs("industrie3");

        cons.display();

    }

}
