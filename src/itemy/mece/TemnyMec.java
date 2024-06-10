package itemy.mece;

/**
 * Trieda TemnyMec je potomkom triedy Mec
 * Predstavuje jeden z typov mečov
 */
public class TemnyMec extends Mec {

    /**
     * Konštruktor triedy TemnyMec
     * @param cisloSveta - Parameter cisloSveta predstavuje číslo Sveta, v ktorom sa táto inštancia objavuje
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X meča na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y meča na mape
     */
    public TemnyMec(int cisloSveta, int worldX, int worldY) {
        super("Mec3", cisloSveta, worldX, worldY, 8);
    }
}
