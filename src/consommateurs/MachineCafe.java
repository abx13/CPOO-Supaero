package consommateurs;

/**
 * Cette classe est la modélisation d'une machine à café, appareil fréquentiel.
 * 
 * @author Anna Barraqué
 */
public class MachineCafe extends AppareilFrequentiel {

   /**
    * Cette méthode est le constructeur initialisant la machine à café avec des
    * valeurs par défaut (puissance maximale, fréquence, temps d'utilisation et
    * plage d'utilsation).
    */
   public MachineCafe() {
      super(800, 0.008, // 1 cafe toutes les 120 min (2h)
            2, // 2 min pour faire un cafe
            new Integer[] { 9, 18 } // prend des cafes de 9h a 18h
      );
   }

   /**
    * Cette méthode est le constructeur permettant de choisir les valeurs
    * d'initilisation de la machine à café.
    * 
    * @param puissanceMax     est la valeur de la puissance maximale de la machine
    *                         à café.
    * @param frequence        est la fréquence d'utilisation de la machine à café
    * @param tempsUtilisation est le temps pendant lequel la machine à café
    *                         consomme de l'énergie à chaque utilisation.
    * @param plageUtilisation est l'intervalle horaire pendant lequel la machine à
    *                         café peut être utilisée.
    */
   public MachineCafe(double puissanceMax, double frequence, double tempsUtilisation, Integer[] plageUtilisation) {
      super(puissanceMax, frequence, tempsUtilisation, plageUtilisation);
   }

}
