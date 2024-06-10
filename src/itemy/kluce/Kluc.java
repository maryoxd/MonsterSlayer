package itemy.kluce;
import itemy.Item;

/**
 * Trieda Kluc je potomkom triedy Item
 * Predstavuje samotný kľúč s ktorým hráč môže intereagovať a úlohou hráča je zbierať tieto kľúče, aby vyhral hru
 */
public class Kluc extends Item {

    /**
     * Konštruktor triedy Kluc
     * @param nazov - Parameter nazov predstavuje samotný názov kľúču, pomocou ktorého sa získa obrázok a kreslí sa v triede predka
     * @param cisloSveta - Parameter CisloSveta predstavuje číslo sveta kľúča, v ktorom sa bude objavovať
     * @param worldX - Parameter worldX predstavuje samotnú hodnotu X kľúča na mape
     * @param worldY - Parameter worldY predstavuje samotnú hodnottu Y kľúča na mape
     */
    public Kluc(String nazov, int cisloSveta, int worldX, int worldY)  {
        super(nazov, cisloSveta, worldX, worldY);
    }
}
