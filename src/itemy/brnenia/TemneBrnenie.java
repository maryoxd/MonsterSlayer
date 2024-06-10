package itemy.brnenia;

/**
 * Trieda TemneBrnenie je potomkom triedy Brnenie
 * Predstavuje jeden z typov brnení
 */
public class TemneBrnenie extends Brnenie {

    /**
     * Konštruktor triedy TemneBrnenie
     * @param cisloSveta - Parameter cisloSveta predstavuje číslo Sveta, v ktorom sa táto inštancia objavuje
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X brnenia na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y brnenia na mape
     */
    public TemneBrnenie(int cisloSveta, int worldX, int worldY) {
        super("Brnenie2", cisloSveta, worldX, worldY, 20);
    }
}
