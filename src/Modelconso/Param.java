package Modelconso;

public class Param {

    protected static boolean display_day=true;


    protected static int jour = 100;
    protected static double rendement_sol=0.14 ;
    protected static double p_max_nucl = 1800;
    protected static double diametre = 80;
    protected static double p_max_eolienne = 2.2; //en MW

    


    protected static double p_max_cafe = 0.0005; //en MW
    protected static int nb_utilisation_jour_cafe = 3;
    protected static double p_frigo = 0.0005; //en MW
    protected static double p_max_Petite_Industrie = 1; //en MW
    protected static double p_max_Moyenne_Industrie = 10; //en MW
    protected static double p_max_Grande_Industrie = 100; //en MW
    protected static double p_max_radiateur = 0.0006; //en MW

    


    public void setBoolean(boolean display_day){
        this.display_day = display_day;
    }

    public static void setParam(int jour, double rendement_sol, double p_max_nucl, double diametre, double p_max_eolienne, double p_max_cafe, int nb_utilisation_jour_cafe, double p_frigo, double p_max_industrie1, double p_max_industrie2, double p_max_industrie3, double p_max_radiateur){
        Param.jour = jour;
        Param.rendement_sol = rendement_sol;
        Param.p_max_nucl = p_max_nucl;
        Param.p_frigo = p_frigo;
        Param.p_max_cafe = p_max_cafe;
        Param.p_max_eolienne = p_max_eolienne;
        Param.p_max_Petite_Industrie = p_max_industrie1;
        Param.p_max_Moyenne_Industrie = p_max_industrie2;
        Param.p_max_Grande_Industrie = p_max_industrie3;
        Param.diametre = diametre;
        Param.nb_utilisation_jour_cafe = nb_utilisation_jour_cafe;
        Param.p_max_radiateur = p_max_radiateur;
        
    


    }



}
