package entity;
import main.GamePanel;
import manazeri.ManazerKolizie;
import manazeri.ManazerSvetov;
import java.awt.Rectangle;

/**
 * Trieda Skeleton je potomkom triedy Nepriatel, predstavuje nepriateľa, ktorý spôsobuje hráčovi poškodenie a hráč naňho môže útočiť
 */
public class Skeleton extends Nepriatel {
    private final Rectangle koliznaArea;

    /**
     * Konštruktor triedy Skeleton
     * @param gamePanel - Parameter GamePanel, posúvame ďalej predkovi
     * @param manazerSvetov - Parameter ManazerSvetov, posúvame ďalej predkovi
     * @param manazerKolizie - Parameter ManazerKolizie, posúvame ďalej predkovi
     * @param cisloSveta - Parameter ktorý určuje, v akom Svete sa má samotná inštancia tejto triedy nachádzať
     * @param worldX - Parameter ktorý určuje samotnú X hodnotu inštancie na mape - posúvame predkovi
     * @param worldY - Parameter ktorý určuje samotnú Y hodnotu inštancie na mape - posúvame predkovi
     */
    public Skeleton(GamePanel gamePanel, ManazerSvetov manazerSvetov, ManazerKolizie manazerKolizie, int cisloSveta, int worldX, int worldY) {
        super(TypEntity.SKELETON, gamePanel, manazerSvetov, manazerKolizie, cisloSveta, worldX, worldY);
        this.koliznaArea = new Rectangle();
        this.setKoliznaArea();
    }

    /**
     * Metóda setKoliznaArea() slúži na nastavenie hodnôt obdĺžnika - X, Y, WIDTH, HEIGHT
     */
    @Override
    public void setKoliznaArea() {
        this.koliznaArea.x = 8;
        this.koliznaArea.y = 20;
        this.koliznaArea.width = 15;
        this.koliznaArea.height = 20;
    }

    /**
     * Metóda getSolidArea() slúži na vrátenie obdĺžnika (Rectangle), pomocou ktorého sa overuje kolízia
     * @return - return kolíznejArea, teda obdĺžnika (Rectangle)
     */
    @Override
    public Rectangle getSolidArea() {
        return this.koliznaArea;
    }
}
