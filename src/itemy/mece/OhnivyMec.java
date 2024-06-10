package itemy.mece;

/**
 * Trieda OhnivyMec je potomkom triedy Mec
 * Predstavuje jeden z typov mečov
 */
public class OhnivyMec extends Mec {

    /**
     * Konštruktor triedy OhnivyMec
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X meča na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y meča na mape
     */
    public OhnivyMec(int worldX, int worldY) {
        super("Mec2", 4, worldX, worldY, 12);
    }
}
