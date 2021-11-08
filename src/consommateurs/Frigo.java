package consommateurs;

/**
 * Cette classe est la modélisation d'un frigo, appareil constant.
 * 
 * @author Anna Barraqué
 */
public class Frigo extends AppareilConstant {

    /**
     * Cette méthode est le constructeur donnant un valeur par défaut au frigo.
     */
    public Frigo() {
        super(200);
    }

    /**
     * Cette méthode est le constructeur permettant de choisir la valeur de la
     * puissance maximale du frigo.
     * 
     * @param puissanceMax est la valeur de la puissance maximale du frigo.
     */
    public Frigo(double puissanceMax) {
        super(puissanceMax);
    }

}
