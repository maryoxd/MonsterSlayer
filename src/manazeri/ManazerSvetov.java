package manazeri;

/**
 * Trieda ManazerSvetov slúži na samotné manažovanie Svetov
 */
public class ManazerSvetov {
    private int cisloSveta;

    /**
     * Konštruktor triedy ManazerSvetov
     * @param svet - Parameter Enumu Svet, vďaka tomu vieme meniť Svety v samotnej hre
     */
    public ManazerSvetov(Svety svet) {
        this.cisloSveta = svet.getCisloSveta();
    }

    /**
     * Metóda getCisloSveta() slúži na vrátenie hodnoty čísla Sveta momentálneho hracieho Sveta
     * @return - Samotnou návratovou hodnotou je hodnota čísla Sveta, teda cisloSveta
     */
    public int getCisloSveta() {
        return this.cisloSveta;
    }

    /**
     * Metóda setSvet() slúži na nastavenie nového hracieho Sveta
     * @param novySvet - Paramete novySvet slúži na nastavenie nového hracieho Sveta pomocou parametra Enumu
     */
    public void setSvet(Svety novySvet) {
        this.cisloSveta = novySvet.getCisloSveta();
    }
}
