package entity;

/**
 * Enum TypEntity slúži na určenie typu entity, pričom obsahuje String cestu k obrázku
 */
public enum TypEntity {
    ZOMBIE("zombie/"),
    SLIME("slime/"),
    SKELETON("skeleton/"),
    GHOST("ghost/"),
    DINO("dino/"),
    CERVENYDRAK("cervenydrak/"),
    MEGADRAK("megadrak/"),
    HRAC("player/");
    private final String obrazok;

    /**
     * Konštruktor Enumu TypEntity
     * @param obrazok - Parameter String predstavuje cestu k obrázku
     */
    TypEntity(String obrazok) {
        this.obrazok = obrazok;
    }

    /**
     * Metóda getObrazok slúži na vrátenie cesty k obrázku v závislosti od typu Enumu
     * @return - Samotnou návratovou hodnotou je String cesta k obrázku, teda obrazok
     */
    public String getObrazok() {
        return this.obrazok;
    }
}
