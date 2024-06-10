package itemy.brnenia;

/**
 * Trieda KovoveBrnenie je potomkom triedy Brnenie
 * Predstavuje jeden z typov brnení
 */
public class KovoveBrnenie extends Brnenie {

    /**
     * Konštruktor triedy KovoveBrnenie
     * @param cisloSveta - Parameter cisloSveta predstavuje číslo Sveta, v ktorom sa táto inštancia objavuje
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X brnenia na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y brnenia na mape
     */
    public KovoveBrnenie(int cisloSveta, int worldX, int worldY) {
        super("Brnenie4", cisloSveta, worldX, worldY, 40);
    }
}
