package itemy;
import entity.Hrac;
import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Trieda Dvere je potomkom triedy Item a predstavuje Item, ktorý sa nedá zobrať, slúži na zablokovanie prechodu
 */
public class Dvere extends Item {
    private boolean zamknute;
    private final String odomykaciKluc;
    private BufferedImage obrazok;

    /**
     * Konštruktor triedy Dvere
     * @param nazov - Parameter nazov predstavuje samotný názov dverí, pomocou ktorého sa získa obrázok a kreslí sa v triede predka
     * @param cisloSveta - Parameter CisloSveta predstavuje číslo sveta dverí, v ktorom sa bude objavovať
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X dverí na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnotu Y dverí na mape
     * @param odomykaciKluc - Parameter odomykaciKluc predstavuje String názov kľúču, pomoocu ktorého sa dvere dajú odomknúť.
     */
    public Dvere(String nazov, int cisloSveta, int worldX, int worldY, String odomykaciKluc) {
        super(nazov, cisloSveta, worldX, worldY);
        this.zamknute = true;
        this.odomykaciKluc = odomykaciKluc;
        this.setArea();
        this.nacitajObrazky();
        this.setWorldX(worldX * 48);
        this.setWorldY(worldY * 48);
    }

    /**
     * Metóda odomkni zistí pomocou metódy z triedy Inventár, či sa majú odomknúť
     * @param inventar - Parameter inventar slúži na zistenie, či hráč má v inventári kľúč ktorý odomyká tieto dvere
     */
    public void odomkni(Inventar inventar) {
        this.zamknute = inventar.najdiKluc(this.odomykaciKluc) == null;
    }

    /**
     * Metóda getNazovOobrazka() slúži na gettovanie cesty k obrázku v závislosti od toho, či sú dvere zamknuté
     * @return - Samotná návratová hodnota je String k ceste obrázku, aj pokiaľ sú dvere zamknuté aj pokiaľ sú otvorené
     */
    public String getNazovObrazka() {
        String navratovy;
        if (this.zamknute) {
            navratovy = "Dvere1Zamknute";
        } else {
            navratovy = "Dvere1Otvorene";
        }
        return navratovy;
    }

    /**
     * Metóda nacitajObrazky() overriduje pôvodnú metódu z predka, slúži na načítavanie iných obrázkov ako v predkovi
     */
    @Override
    public void nacitajObrazky() {
        try {
            this.obrazok = ImageIO.read(new FileInputStream("res/Itemy/" + this.getNazovObrazka()  + ".png"));
        } catch (IOException ignored) {
        }
    }

    /**
     * Metóda draw() overriduje pôvodnú metódu z predka, slúži na vykreslovanie dverí
     * @param g2 - Parameter knižnice Graphics2D, keďže chceme objekt vykreslovať
     * @param gamePanel - Parameter GamePanel slúži na zistenie veľkosti políčka, ktorej sa má obrázok prispôsobiť
     * @param hrac - Parameter Hráča, keďže chceme dvere kresliť len ak ich hráč vidí
     */
    @Override
    public void draw(Graphics2D g2, GamePanel gamePanel, Hrac hrac) {
        int velkostPolicka = gamePanel.getVelkostPolicka();
        this.nacitajObrazky();
        int worldXhrac = hrac.getWorldX();
        int screenXhrac = hrac.getScreenX();
        int worldYhrac = hrac.getWorldY();
        int screenYhrac = hrac.getScreenY();
        int screenX = this.getWorldX() - worldXhrac + screenXhrac;
        int screenY = this.getWorldY() - worldYhrac + screenYhrac;
        if (this.getWorldX() + velkostPolicka > worldXhrac - screenXhrac &&
                this.getWorldX() - velkostPolicka < worldXhrac + screenXhrac &&
                this.getWorldY() + velkostPolicka > worldYhrac - screenYhrac &&
                this.getWorldY() - velkostPolicka < worldYhrac + screenYhrac) {
            g2.drawImage(this.obrazok, screenX, screenY, velkostPolicka, velkostPolicka, null);
        }
    }

    /**
     * Metóda getZamknute() slúži na gettovanie boolean hodnoty toho, či sú dvere zamknuté
     * @return - Samotnou návratovou hodnotou je boolean hodnota zamknutých dverí
     */
    public boolean getZamknute() {
        return this.zamknute;
    }
}



