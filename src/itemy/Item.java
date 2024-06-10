package itemy;
import entity.Hrac;
import itemy.brnenia.Brnenie;
import itemy.kluce.Kluc;
import itemy.mece.Mec;
import main.GamePanel;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Trieda Item predstavuje samotný vytvorený item s vlastnou kolíznou areou (Rectangle), obrázkom a X, Y pozíciami
 */
public class Item {
    private BufferedImage image;
    private final String nazov;
    private boolean aktivny;
    private int worldX;
    private int worldY;
    private final Rectangle solidArea;
    private final int cisloSveta;

    /**
     * Konštruktor triedy Item
     * @param nazov - Parameter nazov predstavuje názov itemu, ktorý dostane od potomka a na základe toho hľadá obrázok itemu
     * @param cisloSveta - Parameter CisloSveta je číslo sveta v ktorom sa potomok tejto triedy bude nachádzať
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X itemu na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnoty Y itemu na mape
     */

    public Item(String nazov, int cisloSveta, int worldX, int worldY) {
        this.nazov = nazov;
        this.cisloSveta = cisloSveta;
        this.aktivny = false;
        this.worldX = worldX;
        this.worldY = worldY;
        this.nacitajObrazky();
        this.solidArea = new Rectangle(0, 0, 0, 0);
    }

    /**
     * Metóda nacitajObrazjy() slúži na načítanie obrázku hocijakého predka vďaka názvu, ktorý má predok
     */
    public void nacitajObrazky() {
        try {
            this.image = ImageIO.read(new FileInputStream("res/Itemy/" + this.nazov + ".png"));
        } catch (IOException ignored) {
        }
    }

    /**
     * Metóda setAktivny() slúži na zmenenie boolean hodnoty toho, či je item aktívny, touto metódou vieme pracovať len s tými itemami, s ktorými chceme a sú aktívne
     * @param novaHodnota - Parameter nová hodnota slúži na nastavenie novej boolean hodnoty Aktivny
     */
    public void setAktivny(boolean novaHodnota) {
        this.aktivny = novaHodnota;
    }

    /**
     * Metóda setArea() polymorfne nastavuje kolíznu areu danému Itemu, podľa toho aká inštancia predka to je
     */
    public void setArea() {
        if (this instanceof Kluc) {
            this.solidArea.x = 0;
            this.solidArea.y = 0;
            this.solidArea.height = 30;
            this.solidArea.width = 30;
        }
        if (this instanceof Mec) {
            this.solidArea.x = 10;
            this.solidArea.y = 10;
            this.solidArea.height = 40;
            this.solidArea.width = 100;
        }
        if (this instanceof Brnenie) {
            this.solidArea.x = 0;
            this.solidArea.y = 0;
            this.solidArea.height = 30;
            this.solidArea.width = 30;
        }
        if (this instanceof Dvere) {
            this.solidArea.x = 20;
            this.solidArea.y = 0;
            this.solidArea.height = 50;
            this.solidArea.width = 40;
        }
    }

    /**
     * Metóda draw() slúži na vykreslovanie itemov
     * @param g2 - Parameter knižnice Graphics2D, keďže chceme itemy (objekty) vykreslovať
     * @param gamePanel - Parameter GamePanel slúži na zistenie velkosti políčka, ktorej sa má obrázok prispôsobiť
     * @param hrac - Parameter Hráča, keďže chceme itemy (objekty) kresliť len ak ich hráč vidí
     */
    public void draw(Graphics2D g2, GamePanel gamePanel, Hrac hrac) {
        int velkostPolicka = gamePanel.getVelkostPolicka();
        this.nacitajObrazky();
        int worldXhrac = hrac.getWorldX();
        int screenXhrac = hrac.getScreenX();
        int worldYhrac = hrac.getWorldY();
        int screenYhrac = hrac.getScreenY();
        int screenX = this.worldX - worldXhrac + screenXhrac;
        int screenY = this.worldY - worldYhrac + screenYhrac;
        if (this.aktivny && this.worldX + velkostPolicka > worldXhrac - screenXhrac &&
                this.worldX - velkostPolicka < worldXhrac + screenXhrac &&
                this.worldY + velkostPolicka > worldYhrac - screenYhrac &&
                this.worldY - velkostPolicka < worldYhrac + screenYhrac) {
            g2.drawImage(this.image, screenX, screenY, velkostPolicka, velkostPolicka, null);
        }
    }

    /**
     * Metóda getImage() slúži na vrátenie obrázka daného Itemu
     * @return - Samotnou návratovou hodnotou je obrázok Itemu, teda image
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Metóda getNazov() slúži na vrátenie názvu daného Itemu
     * @return - Samotnou návratovou hodnotou je vrátenie názvu Itemu, teda nazov
     */
    public String getNazov() {
        return this.nazov;
    }

    /**
     * Metóda getWorldX() slúži na vrátenie hodnoty X Itemu na mape
     * @return - Samotnou návratovou hodnotou je hodnota X, teda worldX
     */
    public int getWorldX() {
        return this.worldX;
    }

    /**
     * Metóda getWorldY() slúži na vrátenie hodnoty Y Itemu na mape
     * @return - Samotnou návratovou hodnotou je hodnota Y, teda worldY
     */
    public int getWorldY() {
        return this.worldY;
    }

    /**
     * Metóda setWorldX() slúži na nastavenie novej hodnoty X Itemu na mape
     * @param worldX - Parameter worldX slúži na nastavenie novej hodnoty
     */
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    /**
     * Metóda setWorldY() slúži na nastavenie novej hodnoty Y Itemu na mape
     * @param worldY - Parameter worldY slúži na nastavenie novej hodnoty
     */
    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    /**
     * Metóda getJeAktivny() slúži na zistenie a vrátenie hodnoty toho, či je Item momentálne aktívny
     * @return - Samotnou návratovou hodnotou je boolean hodnota toho, či je Item aktívny
     */
    public boolean getJeAktivny() {
        return this.aktivny;
    }

    /**
     * Metóda getCisloSveta() slúži na vrátenie hodnoty čísla Sveta, v ktorom item figuruje
     * @return - Samotnou návratovou hodnotou je hodnota čísla Sveta Itemu, teda cisloSveta
     */
    public int getCisloSveta() {
        return this.cisloSveta;
    }

    /**
     * Meoda getRectangle() slúži na vrátenie hodnoty obdĺžnika (Rectangle), ktorý slúži na zistenie kolízie
     * @return - Samotnou návratovou hodnotou je obdĺžnik (Rectangle)
     */
    public Rectangle getRectangle() {
        return this.solidArea;
    }
}
