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
    protected static double p_max_industrie1 = 10; //en MW
    protected static double p_max_industrie2 = 100; //en MW
    protected static double p_max_industrie3 = 1; //en MW
    protected static double p_max_radiateur = 0.0006; //en MW

    


    public void setBoolean(boolean display_day){
        this.display_day = display_day;
    }

    public void setParam(int jour, double rendement_sol, double p_max_nucl, double diametre, double p_max_eolienne, double p_max_cafe, int nb_utilisation_jour_cafe, double p_frigo, double p_max_industrie1, double p_max_industrie2, double p_max_industrie3, double p_max_radiateur){
        this.jour = jour;
        this.rendement_sol = rendement_sol;
        this.p_max_nucl = p_max_nucl;
        this.p_frigo = p_frigo;
        this.p_max_cafe = p_max_cafe;
        this.p_max_eolienne = p_max_eolienne;
        this.p_max_industrie1 = p_max_industrie1;
        this.p_max_industrie2 = p_max_industrie2;
        this.p_max_industrie3 = p_max_industrie3;
        this.diametre = diametre;
        this.nb_utilisation_jour_cafe = nb_utilisation_jour_cafe;
        this.p_max_radiateur = p_max_radiateur;
        
    


    }



}
