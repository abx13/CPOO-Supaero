package simulation;

/**
 * Cette classe est la classe executable principale. Elle lance un scenario ou
 * l'utilisateur peut entrer les appareils ou foyers consommateurs de la ville
 * ainsi que les producteurs d'energie de la ville. L'utilisateur peut ensuite
 * choisir de générer le rapport annuel et/ou journalier de la ville.
 * 
 * @author Anna Barraqué
 */
public class Main {
    public static void main(String[] args) {
        SimulationScenario.play();
    }
}
