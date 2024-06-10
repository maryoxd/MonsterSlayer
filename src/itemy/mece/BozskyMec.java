package itemy.mece;

/**
 * Trieda BozskyMec je potomkom triedy Mec
 * Predstavuje jeden z typov mečov
 */
public class BozskyMec extends Mec {

    /**
     * Konštruktor triedy BozskyMec
     * @param cisloSveta - Parameter cisloSveta predstavuje číslo Sveta, v ktorom sa táto inštancia objavuje
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X meča na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y meča na mape
     */
    public BozskyMec(int cisloSveta, int worldX, int worldY) {
        super("Mec5", cisloSveta, worldX, worldY, 25.5);
    }
}
