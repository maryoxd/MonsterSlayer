package itemy.brnenia;

/**
 * Trieda ObycajneBrnenie je potomkom triedy Brnenie
 * Predstavuje jeden z typov brnení
 */
public class ObycajneBrnenie extends Brnenie {

    /**
     * Konštruktor triedy ObycajneBrnenie
     * @param cisloSveta - Parameter cisloSveta predstavuje číslo Sveta, v ktorom sa táto inštancia objavuje
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X brnenia na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y brnenia na mape
     */
    public ObycajneBrnenie(int cisloSveta, int worldX, int worldY) {
        super("Brnenie1", cisloSveta, worldX, worldY, 10);
    }
}
