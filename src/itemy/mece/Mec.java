package itemy.mece;
import entity.Hrac;
import itemy.Item;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Trieda Meč je potomok triedy Item a implementuje Interface Zbrane
 * Predstavuje samotný meč ako objekt, ktorý hráč môže používať a dáva hráčovi možnosť útočiť s poškodením v závislosti od meča
 */
public class Mec extends Item implements Zbrane {
    private final double poskodenie;
    private BufferedImage obrazokUtocenia;

    /**
     * Konštruktor triedy Meč
     * @param nazov - Parameter nazov predstavuje samotný názov meču
     * @param cisloSveta - Parameter CisloSveta predstavuje číslo sveta, v ktorom sa meč objavuje
     * @param worldX - Parameter worldX predstavuje samotnú X hodnotu meča na mape
     * @param worldY - Parameter worldY predstavuje samotnú Y hodnotu meča na mape
     * @param poskodenie - Parameter poskodenie predstavuje poškodenie daného meču
     */
    public Mec(String nazov, int cisloSveta, int worldX, int worldY, double poskodenie) {
        super(nazov, cisloSveta, worldX, worldY);
        this.poskodenie = poskodenie;
    }

    /**
     * Metóda nacitajObrazky() slúži na načítanie obrázkov meču, ktorý už hráč používa a útočí s ním
     * @param hrac - Parameter hráča je použitý na zisťovanie Smeru hráča, podľa ktorého sa vyberá obrázok meču
     */
    public void nacitajObrazky(Hrac hrac) {
        try {
            switch (hrac.getSmer()) {
                case "vpravo" -> {
                    if (hrac.getUtocenie()) {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_VpravoUtok.png"));
                    } else {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_Vpravo.png"));
                    }
                }
                case "vlavo" -> {
                    if (hrac.getUtocenie()) {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_VlavoUtok.png"));
                    } else {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_Vlavo.png"));
                    }
                }
                case "hore" -> {
                    if (hrac.getUtocenie()) {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_HoreUtok.png"));
                    } else {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_Hore.png"));
                    }
                }
                case "dole" -> {
                    if (hrac.getUtocenie()) {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_DoleUtok.png"));
                    } else {
                        this.obrazokUtocenia = ImageIO.read(new FileInputStream("res/Itemy/MeceUtok/" + this.getNazov() + "_Dole.png"));
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Metóda drawHracovMec() slúži na kreslenie meča ktorý hráč používa
     * @param g2 - Parameter knižnice Graphics2D, keďže chceme meč vykresliť
     * @param x - Parameter X, parameter používame na kreslenie na tejto X súradnici
     * @param y - Parameter Y, parameter používame na kreslenie na tejto Y súradnici
     * @param skala - Parameter skala slúži na zväčšenie obrázku v závislosti od parametra škála
     * @param hrac - Parameter Hráča potrebujeme aby sme mohli načítať obrázok, ktorý sa bude vykreslovať v závislosti od hráčovho Smeru
     */
    public void drawHracovMec(Graphics2D g2, int x, int y, int skala, Hrac hrac) {
        this.nacitajObrazky(hrac);
        g2.drawImage(this.obrazokUtocenia, x, y, skala, skala - 15, null);
    }

    /**
     * Metóda getDmg() overriduje metódu z Interfacu, slúži na vrátenie hodnoty poškodenia meču
     * @return - Samotnou návratovou hodnotou je hodnota poškodenia, teda poskodenie
     */
    @Override
    public double getPoskodenie() {
        return this.poskodenie;
    }

}
