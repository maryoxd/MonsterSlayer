package entity;

/**
 * Interface Ohnuvzdorny slúži na gettovanie a settovanie toho, či je daná inštancia / objekt ohňuvzdorný alebo nie
 */
public interface Ohnuvzdorny {

    /**
     * Metóda getOhnuvzdorny() slúži na vrátenie boolean hodnoty toho, či je daná inštancia / objekt ohňuvzdorný alebo nie
     * @return - Samotnou návratovou hodnotou je boolean hodnota
     */
    boolean getOhnuvzdorny();

    /**
     * Metóda setOhnuvzdorny() slúži na nastavenie boolean hodnoty true / false
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej boolean hodnoty
     */
    void setOhnuvzdorny(boolean novaHodnota);

}
