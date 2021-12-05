package Modelconso;



// Modelise le vent sur une journée
public class Soleil {

    public static double ensoleillement( int jour) {
        
        
        int mois = simulation.Temps.dayToMonth(jour);
        String saison = simulation.Temps.monthToSeason(mois);

        double ensoleillement = 0;
        double ensoleillement_max = 1000;
        
        if (saison == "summer"){
            ensoleillement = ensoleillement_max/2 + Math.random()*ensoleillement_max/2; //en W/m2
            

        }
        if (saison == "fall"){
            ensoleillement = (ensoleillement_max/2 + Math.random()*ensoleillement_max/2)/1.6; //source oubliée

        }
        if (saison == "winter"){
            ensoleillement = (ensoleillement_max/2 + Math.random()*ensoleillement_max/2)/2.8;

        }
        if (saison == "spring"){
            ensoleillement = (ensoleillement_max/2 + Math.random()*ensoleillement_max/2)/1.6; 

        }
        
        if (ensoleillement>=ensoleillement_max){
            ensoleillement = ensoleillement_max;
        }

        return ensoleillement;

    }
    
}
