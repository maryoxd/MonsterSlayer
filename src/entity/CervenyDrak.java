package entity;
import main.GamePanel;
import manazeri.ManazerKolizie;
import manazeri.ManazerSvetov;
import java.awt.Rectangle;

/**
 * Trieda CervenyDrak je potomkom triedy Nepriatel, predstavuje nepriateľa, ktorý spôsobuje hráčovi poškodenie a hráč naňho môže útočiť
 */
public class CervenyDrak extends Nepriatel {
    private final Rectangle koliznaArea;

    /**
     * Konštruktor triedy CervenyDrak predstavuje všetky parametre ktoré si žiada jeho predok, trieda Nepriatel.
     * Parameter TypEntity (ENUM), je zadaný "napevno", keďže táto trieda predstavuje samotný TypEntity daného typu.
     * @param gamePanel - Parameter GamePanel, posúvame ďalej predkovi
     * @param manazerSvetov - Parameter ManazerSvetov, posúvame ďalej predkovi
     * @param manazerKolizie - Parameter ManazerKolizie, posúvame ďalej predkovi
     * @param worldX - Parameter ktorý určuje samotnú X hodnotu inštancie na mape - posúvame predkovi
     * @param worldY - Parameter ktorý určuje samotnú Y hodnotu inštancie na mape - posúvame predkovi
     */
    public CervenyDrak(GamePanel gamePanel, ManazerSvetov manazerSvetov, ManazerKolizie manazerKolizie, int worldX, int worldY) {
        super(TypEntity.CERVENYDRAK, gamePanel, manazerSvetov, manazerKolizie, 4, worldX, worldY);
        this.koliznaArea = new Rectangle();
        this.setKoliznaArea();
        this.setSmer("vpravo");
    }

    /**
     * Metóda getSolidArea() slúži na vrátenie obdĺžnika (Rectangle), pomocou ktorého sa overuje kolízia
     * @return - return kolíznejArea, teda obdĺžnika (Rectangle)
     */
    @Override
    public Rectangle getSolidArea() {
        return this.koliznaArea;
    }

    /**
     * Metóda setKoliznaArea() slúži na nastavenie hodnôt obdĺžnika - X, Y, WIDTH, HEIGHT
     */
    @Override
    public void setKoliznaArea() {
        this.koliznaArea.x = 15;
        this.koliznaArea.y = 30;
        this.koliznaArea.width = 40;
        this.koliznaArea.height = 40;
    }
}
