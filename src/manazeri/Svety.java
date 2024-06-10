package manazeri;

/**
 * ENUM Svety slúži na identifikáciu a vytvorenie hracích Svetov
 */
public enum Svety {
    LOBBY( 1),
    ZAKLADNY(2),
    RAJ(3),
    PEKLO(4);
    private final int cisloSveta;

    /**
     * Konštruktor Enumu Svety
     * @param cisloSveta - Parameter CisloSveta predstavuje číslo Sveta ktorým daný Svet disponuje
     */
    Svety(int cisloSveta) {
        this.cisloSveta = cisloSveta;
    }

    /**
     * Metóda getCisloSveta() slúži na vrátenie hodnoty čísla Sveta v závislosti od daného typu Sveta
     * @return - Samotnou návratovou hodnotou je číslo daného Sveta
     */
    public int getCisloSveta() {
        return switch (this) {
            case LOBBY -> LOBBY.cisloSveta;
            case ZAKLADNY -> ZAKLADNY.cisloSveta;
            case RAJ -> RAJ.cisloSveta;
            case PEKLO -> PEKLO.cisloSveta;
        };
    }
}
