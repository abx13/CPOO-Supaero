package Modelconso;

import simulation.Temps;

public class Operations {

   

 


/**
 * 
 * @param tableau_Puissance
 * @param tableau_2 doit être de la meme taille que tableau de puissance
 */
    public void mini(double[] tableau_Puissance, double[] tableau_2){

        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                if (tableau_Puissance[t] >= tableau_2[t]) {
                    tableau_Puissance[t] = tableau_2[t];
                }

            }
        }

        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                if (tableau_Puissance[t] >= tableau_2[t]) {
                    tableau_Puissance[t] = tableau_2[t];
                }

            }
        }

    }

    /**
     * ecretage avec une constante
     * @param tableau_Puissance
     * @param crete
     */
    public void ecretage(double[] tableau_Puissance, double crete) {

        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                if (tableau_Puissance[t] >= crete) {
                    tableau_Puissance[t] = crete;
                }

            }
        }

        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                if (tableau_Puissance[t] >= crete) {
                    tableau_Puissance[t] = crete;
                }

            }
        }
       
    }

/**
 * 
 * @param tableau_Puissance
 */

    public void seulement_positif(double[] tableau_Puissance) {

        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                if (tableau_Puissance[t] <= 0) {
                    tableau_Puissance[t] = 0;
                }

            }
        }

        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                if (tableau_Puissance[t] <= 0) {
                    tableau_Puissance[t] = 0;
                }

            }
        }
       
    }


    public void abs(double[] tableau_Puissance) {

        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                if (tableau_Puissance[t] <= 0) {
                    tableau_Puissance[t] = -tableau_Puissance[t];
                }

            }
        }

        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                if (tableau_Puissance[t] <= 0) {
                    tableau_Puissance[t] = -tableau_Puissance[t];
                }

            }
        }
       
    }



    public void sum(double[] tableau_Puissance_Change, double[] tableau_Puissance2 ) {

        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                
                    tableau_Puissance_Change[t] += tableau_Puissance2[t];
                

            }
        }

        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                tableau_Puissance_Change[t] += tableau_Puissance2[t];

            }
        }
       
    }



    /**
     * 
     * @param tableau_Puissance
     * @param pourcentage       // pourcentage du bruit par rapport à la valeur max
     */
    public void bruitBlanc(double[] tableau_Puissance, double pourcentage) {
        // recherche du maximum
        double max = 0;
        for (double puissance : tableau_Puissance) {
            if (max < Math.abs(puissance)) {
                max = puissance;
            }
        }
        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {

                if(tableau_Puissance[t]==0){
                    tableau_Puissance[t] = 0;
                }
                else{
                tableau_Puissance[t] += Math.random() * pourcentage/100 * max;

            }}

        }

        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                if(tableau_Puissance[t]==0){
                    tableau_Puissance[t] = 0;
                }
                else{
                tableau_Puissance[t] += Math.random() * pourcentage/100 * max;
                }
            }

        }
        
    

    }

    public void decalage_tempo(double[] tableau_Puissance, int decalage){
        double [] table_stock = tableau_Puissance.clone();
        int dec = decalage-1;

        if (Param.display_day) {
            if(decalage>Temps.NBMINUTESJOUR){ 
                System.out.println("svp mettre un décalge inferieur à " + Temps.NBMINUTESJOUR + "minutes");
            }
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                if(t-dec>0){
                tableau_Puissance[t] = table_stock[t-dec];
                }
                else{
                    tableau_Puissance[t] = table_stock[t-dec+Temps.NBMINUTESJOUR-1];

                }

            }

        }
        if (!Param.display_day) {
            if(decalage>Temps.NBJOURSANNEE){ 
                System.out.println("svp mettre un décalge inferieur à 365 jour");
            }
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {

                if(t-dec>=0){
                    tableau_Puissance[t] = table_stock[t-dec];
                    }
                    else{
                        tableau_Puissance[t] = table_stock[t-dec+Temps.NBJOURSANNEE-1];
    
                    }

            }

        }


    }
    public void troncage_tempo(double[] tableau_Puissance, int index_troncage){
        
        int troncage_tempo = index_troncage-1;

        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                if(t-troncage_tempo>0){
                tableau_Puissance[t] = 0;
                }
               

            }

        }
        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                if(t-troncage_tempo>0){
                    tableau_Puissance[t] = 0;
                    }
                    

            }

        }


    }

  

    public static void main(String[] args) {
        FonctionAbstraites fonction = new FonctionAbstraites();
        Operations op = new Operations();
        double[] cren = fonction.creneauDouble(1.1, 0.8, 30, 0.2);
        
        op.ecretage(cren,0.9);
        for (int t = 0; t < 130; t++) {
            System.out.println(cren[t]);

        }
        System.out.println("OKKKKK");
        op.decalage_tempo(cren, 3);

        for (int t = 0; t < 130; t++) {
            System.out.println(cren[t]);

        }

    }
}
