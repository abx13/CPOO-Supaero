package Modelconso;



// Modelise le vent sur une journée
public class Ventjournee {

    public static double vit_Ventjournee( int jour) {
        
        
        int mois = simulation.Temps.dayToMonth(jour);
        String saison = simulation.Temps.monthToSeason(mois);

        
        double vitMoySaison = 0;
        double vent_jour=0;
        double coef = 4; // coef pour prendre en compte l'exposition des eoliennes, placée dans les endroits venteux
        
        if (saison == "summer"){
            vitMoySaison = 12.6/3.6; //source: https://fr.weatherspark.com/y/47913/M%C3%A9t%C3%A9o-moyenne-%C3%A0-Paris-France-tout-au-long-de-l'ann%C3%A9e
            vent_jour = coef*(vitMoySaison/2 + Math.random()*vitMoySaison/2);
        }
        if (saison == "fall"){
            vitMoySaison = 15.7/3.6; //Conversion en m/s
            vent_jour = coef*(vitMoySaison/2 + Math.random()*vitMoySaison/2);

        }
        if (saison == "winter"){
            vitMoySaison = 18.8/3.6; 
            vent_jour = coef*(vitMoySaison/2 + Math.random()*vitMoySaison/2);

        }
        if (saison == "spring"){
            vitMoySaison = 15.7/3.6; 
            vent_jour = coef*(vitMoySaison/2 + Math.random()*vitMoySaison/2);

        }
        
        return vent_jour;

    }
    
}


