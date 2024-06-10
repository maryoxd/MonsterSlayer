package itemy;
import main.GamePanel;
import java.util.Arrays;

/**
 * Trieda ManazerItemov slúži na manažovanie Itemov v závislosti od Sveta a vytváranie Itemov do pola
 */
public class ManazerItemov {
    private final Item[] itemy;
    private final int velkostPolicka;

    /**
     * Konštruktor triedy ManazerItemov
     * @param gamePanel - Parameter GamePanel slúži na zistenie veľkosti políčka, ktorej sa má obrázok prispôsobiť
     */
    public ManazerItemov(GamePanel gamePanel) {
        this.itemy = new Item[30];
        this.velkostPolicka = gamePanel.getVelkostPolicka();

    }

    /**
     * Metóda pridajItemDoHry() slúži na pridanie novej inštancie Itemu do pola Itemov
     * @param item - Parameter Item slúži na pridanie nového Itemu do hry, teda do pola ktoré manažuje táto trieda
     */
    public void pridajItemDoHry(Item item) {
        for (int i = 0; i < this.itemy.length; i++) {
            if (this.itemy[i] == null) {
                this.itemy[i] = item;
                break;
            }
        }
    }

    /**
     * Metóda nastavObjekt() slúži na nastavenie objektu pomoocu iterácie cez pole Itemov, pokiaľ má Item požadované čísloSveta, Item sa nastavý na aktívny a aktualizuje sa mu poloha
     * @param cisloSveta - Parameter CisloSveta sa používa na určenie, v ktorom svete sa má daný objekt nastaviť
     */
    public void nastavObjekt(int cisloSveta) {
        for (Item item : this.itemy) {
            if (item != null && cisloSveta == item.getCisloSveta()) {
                item.setAktivny(true);
                int x = item.getWorldX();
                int y = item.getWorldY();
                item.setWorldX(x * this.velkostPolicka);
                item.setWorldY(y * this.velkostPolicka);
            }
            if (item != null && cisloSveta != item.getCisloSveta()) {
                item.setAktivny(false);
            }
        }
    }

    /**
     * Metóda getItemy() slúži na vrátenie kópie pola Itemov
     * @return - Samotnou návratovou hodnotou je kópia pola z tejto triedy
     */
    public Item[] getItemy() {
        return Arrays.copyOf(this.itemy, this.itemy.length);
    }
}
