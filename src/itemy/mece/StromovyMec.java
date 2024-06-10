package itemy.mece;

/**
 * Trieda StromovyMec je potomkom triedy Mec
 * Predstavuje jeden z typov mečov
 */
public class StromovyMec extends Mec {

    /**
     * Konštruktor triedy StromovyMec
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X meča na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y meča na mape
     */
    public StromovyMec(int worldX, int worldY) {
        super("Mec4", 3, worldX, worldY, 15);
    }
}
