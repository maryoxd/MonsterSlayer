package itemy.mece;

/**
 * Trieda ObycajnyMec je potomkom triedy Mec
 * Predstavuje jeden z typov mečov
 */
public class ObycajnyMec extends Mec {

    /**
     * Konštruktor triedy ObycajnyMec
     * @param cisloSveta - Parameter cisloSveta predstavuje číslo Sveta, v ktorom sa táto inštancia objavuje
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X meča na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y meča na mape
     */
    public ObycajnyMec(int cisloSveta, int worldX, int worldY) {
        super("Mec1", cisloSveta, worldX, worldY, 4.0);
    }
}
