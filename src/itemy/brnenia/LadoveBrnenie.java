package itemy.brnenia;

/**
 * Trieda LadoveBrnenie je potomkom triedy Brnenie
 * Predstavuje jeden z typov brnení
 */
public class LadoveBrnenie extends Brnenie {

    /**
     * Konštruktor triedy LadoveBrnenie
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X brnenia na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y brnenia na mape
     */
    public LadoveBrnenie(int worldX, int worldY) {
        super("Brnenie3", 2, worldX, worldY, 30);
    }
}
