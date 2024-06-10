package itemy.brnenia;

/**
 * Trieda BozskeBrnenie je potomkom triedy Brnenie
 * Predstavuje jeden z typov brnení
 */
public class BozskeBrnenie extends Brnenie {

    /**
     * Konštruktor triedy BozskeBrnenie
     * @param cisloSveta - Parameter cisloSveta predstavuje číslo Sveta, v ktorom sa táto inštancia objavuje
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X brnenia na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y brnenia na mape
     */
    public BozskeBrnenie(int cisloSveta, int worldX, int worldY) {
        super("Brnenie5", cisloSveta, worldX, worldY, 100);
    }
}
