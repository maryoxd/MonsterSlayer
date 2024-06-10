package itemy.brnenia;

/**
 * Trieda PekelneBrnenie je potomkom triedy Brnenie
 * Predstavuje jeden z typov brnení
 */
public class PekelneBrnenie extends Brnenie {

    /**
     * Konštruktor triedy PekelneBrnenie
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X brnenia na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y brnenia na mape
     */
    public PekelneBrnenie(int worldX, int worldY) {
        super("Brnenie6", 4, worldX, worldY, 50);
    }
}
