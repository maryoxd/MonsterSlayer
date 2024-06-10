package policka;
import java.awt.image.BufferedImage;

/**
 * Trieda Policko predstavuje samotné políčko mapy, po ktorom sa Hráč a Nepriatelia môžu pohybovať
 */
public class Policko {
    private BufferedImage obrazok;
    private boolean kolizia;
    private boolean teleportuje;
    private boolean poskodzuje;
    private double poskodenie;
    private int teleportacneCislo;

    /**
     * Konštruktor triedy Policko
     * Od začiatku má každé políčko nastavené boolean hodnoty kolízie, teleportácie a poškodenia na false
     */
    public Policko() {
        this.kolizia = false;
        this.teleportuje = false;
        this.poskodzuje = false;
    }

    /**
     * Metóda getObrazok() slúži na vrátenie hodnoty obrázka daného políčka
     * @return - Samotnou návratovou hodnotou je BufferedImage, teda obrázok
     */
    public BufferedImage getObrazok() {
        return this.obrazok;
    }

    /**
     * Metóda getKolizia() slúži na vrátenie hodnoty toho, či políčko spôsobuje kolíziu Hráčovi a Nepriatelom
     * @return - Samotnou návratovou hodnotou je boolean hodnota toho, či má políčko kolíziu
     */
    public boolean getKolizia() {
        return this.kolizia;
    }

    /**
     * Metóda setObrazok() slúži na nastavenie nového obrázku danému políčku
     * @param obrazok - Parameter obrazok slúži na nastavenie novej hodnoty obrázku (BufferedImage) danému políčku
     */
    public void setObrazok(BufferedImage obrazok) {
        this.obrazok = obrazok;
    }

    /**
     * Metóda setKolizia() slúži na nastavenie novej hodnoty kolízie danému políčku
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej boolean hodnoty kolízie
     */
    public void setKolizia(boolean novaHodnota) {
        this.kolizia = novaHodnota;
    }

    /**
     * Metóda getTeleportacia() slúži na zistenie boolean hodnoty toho, či je políčko schopné teleportovať Hráča
     * @return - Samotnou návratovou hodnootu je boolean hodnota toho, či dané políčko teleportuje
     */
    public boolean getTeleportacia() {
        return this.teleportuje;
    }

    /**
     * Metóda setTeleportacia() slúži na nastavenie novej hodnoty toho, či má / nemá políčko Hráča teleportovať
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej boolean hodnoty teleportácie
     */
    public void setTeleportacia(boolean novaHodnota) {
        this.teleportuje = novaHodnota;
    }

    /**
     * Metóda setTeleportacneCislo() slúži na nastavenie novej hodnoty teleportačného čísla, ktorým potom vieme zistiť do ktorého Sveta sa má Hráč teleportovať
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie nového čísla Sveta
     */
    public void setTeleportacneCislo(int novaHodnota) {
        this.teleportacneCislo = novaHodnota;
    }

    /**
     * Metóda getTeleportacneCislo() slúži na vrátenie hodnoty teleportačného čísla
     * @return - Samotnou návratovou hodnotou je hodnota teleportačného čísla
     */
    public int getTeleportacneCislo() {
        return this.teleportacneCislo;
    }

    /**
     * Metóda getPoskodzuje() slúži na vrátenie hodnoty toho, či je políčko schopné poškodiť Entitu
     * @return - Samotnou návratovou hodnotou je boolean hodnota toho, či políčko vytvára nejaké poškodenie
     */
    public boolean getPoskodzuje() {
        return this.poskodzuje;
    }

    /**
     * Metóda setPoskodzuje slúži na nastavenie novej hodnoty toho, aby políčko bolo / nebolo poškodzujúce
     * @param poskodzuje - Parameter poskodzuje slúži na nastavenie novej boolean hodnoty poškodenia
     */
    public void setPoskodzuje(boolean poskodzuje) {
        this.poskodzuje = poskodzuje;
    }

    /**
     * Metóda getPoskodenie() slúži na vrátenie hodnoty toho, koľko poškodenia políčko tvorí
     * @return - Samotnou návratovou hodnotou je hodnota poškodenia, ktoré má políčko
     */
    public double getPoskodenie() {
        return this.poskodenie;
    }

    /**
     * Metóda setPoskodenie() slúži na nastavenie novej hodnoty poškodenia ktoré políčko tvorí
     * @param poskodenie - Parameter poskodenie slúži na nastavenie novej hodnoty poškodenia
     */
    public void setPoskodenie(double poskodenie) {
        this.poskodenie = poskodenie;
    }
}
