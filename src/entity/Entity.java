package entity;
import bary.HealthBar;
import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Trieda Entity predstavuje abstraktnú triedu ktorá taktiež implementuje Interface Ohnuvzdorny
 * Táto trieda je predkom všetkým inštanciám triedy Nepriatel, a hráčovi
 * Pri riešení vykreslovania len toho, pri čom je hráč (počítanie ScreenX, ScreenY) som si pomáhal videoTutoriálom! (<a href="https://www.youtube.com/watch?v=Ny_YHoTYcxo&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=5&ab_channel=RyiSnow">...</a>)
 */
public abstract class Entity implements Ohnuvzdorny {
    private int worldX;
    private int worldY;
    private int screenY;
    private int screenX;
    private int speed;
    private double hp;
    private double maxHp;
    private double damage;
    private boolean kolizia = false;
    private String smer;
    private BufferedImage dole1;
    private BufferedImage dole2;
    private BufferedImage hore1;
    private BufferedImage hore2;
    private BufferedImage vpravo1;
    private BufferedImage vpravo2;
    private BufferedImage vlavo1;
    private BufferedImage vlavo2;
    private final int velkostPolicka;
    private final int sirkaObrazovky;
    private final int vyskaObrazovky;
    private final String obrazokEntity;
    private final int maxWorldRow;
    private final int maxWorldCol;
    private long lastDamageTime;

    /**
     * Konštruktor triedy Entita predstavuje TypEntity (ENUM), GamePanel z ktorého pomocou getterov nastavujeme premenné s ktorými pracujeme
     * @param typEntity - Parameter TypEntity predstavuje ENUM Entity, ktorý musí byť pridelený, používa sa najmä na získavanie obrázkov
     * @param gamePanel - Parameter GamePanel slúži na získavanie rôznych hodnôt z triedy GamePanel
     */
    public Entity(TypEntity typEntity, GamePanel gamePanel)  {
        this.velkostPolicka = gamePanel.getVelkostPolicka();
        this.sirkaObrazovky = gamePanel.getSirkaObrazovky();
        this.vyskaObrazovky = gamePanel.getVyskaObrazovky();
        this.maxWorldCol = gamePanel.getMaxWorldCol();
        this.maxWorldRow = gamePanel.getMaxWorldRow();
        this.lastDamageTime = System.currentTimeMillis();
        this.obrazokEntity = typEntity.getObrazok();
        this.smer = "vpravo";
        this.nastavObrazky();
    }

    // ABSTRAKTNÉ METÓDY

    /**
     * Abstraktná metóda update() slúži na samotné abstraktné updatovanie inštancií - v rámci potomkov triedy Nepriatel najmä o pohyb, v prípade hráča sa updatuje viacero vecí
     */
    public abstract void update();

    /**
     * Abstraktná metóda nastavZaklad() slúži na abstraktné počiatočné nastavenie rôznych premenných potomkov - HP, MaxHP, rýchlosť (Speed), sila útoku (Damage)..
     */
    public abstract void nastavZaklad();

    /**
     * Abstraktná etóda getSolidArea() slúži na abstraktné vracanie obdĺžníka (Rectangel)
     * @return - returnom je samotný obdĺžnik
     */
    public abstract Rectangle getSolidArea();

    /**
     * Abstraktná metóda setKoliznaArea() slúži na nastavenie hodnôť obdĺžnika (Rectangel)
     */
    public abstract void setKoliznaArea();
    // ABSTRAKTNÉ METÓDY

    /**
     * Metóda nastavObrazky() slúži na nastavenie obrázku, obrázok sa vždy nastaví podľa TypuEntity predka, preto ho môže používať hocijaká inštancia predka
     * V prípade inštancie CervenyDrak sa načítávajú len obrázky vpravo1, vpravo2, vlavo1, vlavo2, keďže sa môže hýbať len týmito smermi a iné obrázky neexistujú
     */
    public void nastavObrazky() {
        try {
            if (!(this instanceof CervenyDrak)) {
                this.dole1 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "dole1.png"));
                this.dole2 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "dole2.png"));
                this.hore1 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "hore1.png"));
                this.hore2 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "hore2.png"));
            }
            this.vpravo1 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vpravo1.png"));
            this.vpravo2 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vpravo2.png"));
            this.vlavo1 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vlavo1.png"));
            this.vlavo2 = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vlavo2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metóda getObrazok() slúži na samotné vrátenie požadovaného obrázku v závislosti od Smeru v ktorom sa potomok nachádza, a ktorý obrázok chce (číslo1, číslo2
     * @param smer - Parameter smer slúži na zistenie smeru inštancie, podľa ktorej sa potom vracia obrázok v danom smere
     * @param cislo - Parameter cislo slúži na zistenie toho, ktorý obrázok si inštancia žiada (každý smer má 2 obrázky)
     * @return - Samotnou návratovou hodnotou je obrázok danej Entity
     */
    public BufferedImage getObrazok(String smer, int cislo) {
        BufferedImage obr = null;
        switch (smer) {
            case "dole" -> {
                if (cislo == 1) {
                    obr = this.dole1;
                } else if (cislo == 2) {
                    obr = this.dole2;
                }
            }
            case "hore" -> {
                if (cislo == 1) {
                    obr = this.hore1;
                } else if (cislo == 2) {
                    obr = this.hore2;
                }
            }
            case "vpravo" -> {
                if (cislo == 1) {
                    obr = this.vpravo1;
                } else if (cislo == 2) {
                    obr = this.vpravo2;
                }
            }
            case "vlavo" -> {
                if (cislo == 1) {
                    obr = this.vlavo1;
                } else if (cislo == 2) {
                    obr = this.vlavo2;
                }
            }
            default -> {
                return this.dole1;
            }
        }
        return obr;
    }

    /**
     * Metóda setSmer() slúži na nastavovanie Smeru potomkom
     * @param smer - Parameter predstavuje nový smer, ktorý bude inštancii nastavený
     */
    public void setSmer(String smer) {
        this.smer = smer;
    }

    /**
     * Metóda getSmer() slúži na vrátenie smeru inštancie
     * @return - Samotnou návratovou hodnotou je smer v ktorom sa momentálne inštancia nachádza
     */
    public String getSmer() {
        return this.smer;
    }

    /**
     * Metóda setSpeed() slúži na nastavenie rýchlosti potomkom
     * @param speed - Parameter predstavuje novú hodnotu rýchlosti, ktorá bude inštancii nastavená
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Metóda getSpeed() slúži na vrátenie rýclosti inštancie
     * @return - Samotnou návratovou hodnotou je rýchlosť, ktorú momentálne inštancia má
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Metóda setWorldX() slúži na nastavenie novej hodnoty X pre danú inštanciu
     * @param noveX - Parameter predstavuje novú hodnotu X, na ktorej sa potomok bude nachádzať
     */

    public void setWorldX(int noveX) {
        this.worldX = noveX;
    }

    /**
     * Metóda setWorldY() slúži na nastavenie novej hodnoty Y pre danú inštanciu
     * @param noveY - Parameter predstavuje novú hodnotu Y, na ktorej sa potomok bude nachádzať
     */
    public void setWorldY(int noveY) {
        this.worldY = noveY;
    }

    /**
     * Metóda getWorldY() slúži na vrátenie hodnoty Y na ktorej sa potomok nachádza
     * @return - Samotnou návratovou hodnotou je hodnota Y potomka
     */
    public int getWorldY() {
        return this.worldY;
    }

    /**
     * Metóda getWorldX() slúži na vrátenie hodnoty X na ktorej sa potomok nachádza
     * @return - Samotnou návratovou hodnotou je hodnota X potomka
     */
    public int getWorldX() {
        return this.worldX;
    }

    /**
     * Metóda setScreenY() slúži na nastavenie novej hodnoty Y obrazovky
     * @param noveY - Parameter predstavuje novú hodnotu ktorá bude pridelená screenY
     */
    public void setScreenY(int noveY) {
        this.screenY = noveY;
    }

    /**
     * Metóda setScreenX() slúži na nastavenie novej hodnoty X obrázku
     * @param noveX - Parameter predstavuje novú hodnotu ktorá bude pridelená screenX
     */
    public void setScreenX(int noveX) {
        this.screenX = noveX;
    }

    /**
     * Metóda getScreenY() slúži na vrátenie hodnoty ScreenY
     * @return - Samotnou návratovou hodnotou je hodnota screenY
     */
    public int getScreenY() {
        return this.screenY;
    }

    /**
     * Metóda getScreenX() slúži na vrátenie hodnoty ScreenX
     * @return - Samotnou návratovou hodnotou je hodnota screenX
     */
    public int getScreenX() {
        return this.screenX;
    }

    /**
     * Metóda getVelkostPolicka() slúži na vrátenie hodnoty velkostPolicka, ktorá je v konštruktori inicializovaná z GamePanelu
     * @return - Samotnou návratovou hodnotou je hodnota velkostPolicka
     */
    public int getVelkostPolicka() {
        return this.velkostPolicka;
    }

    /**
     * Metóda getSirkaObrazovky() slúži na vrátenie hodnoty sirkaObrazovky, ktorá je v konštruktori inicializovaná z GamePanelu
     * @return - Samotnou návratovou hodnotou je hodnota sirkaObrazovky
     */
    public int getSirkaObrazovky() {
        return this.sirkaObrazovky;
    }

    /**
     * Metóda getVyskaObrazovky() slúži na vrátenie hodnoty vyskaObrazovky, ktorá je v konštruktori inicializovaná z GamePanelu
     * @return - Samotnou návratovou hodnotou je hodnota vyskaObrazovky
     */
    public int getVyskaObrazovky() {
        return this.vyskaObrazovky;
    }

    /**
     * Metóda getMaxWorldRow() slúži na vrátenie hodnoty maxWorldRow, ktorá predstavuje maximálnu šírku mapy
     * Podľa tejto premennej si môže hráč nastavovať jeho pozíciu na stred
     * @return - Samotnou návratovou hodnotou je hodnota maxWorldRow
     */
    public int getMaxWorldRow() {
        return this.maxWorldRow;
    }

    /**
     * Metóda getMaxWorldCol() slúži na vrátenie hodnoty maxWorldCol, ktorá predstavuje maximálnu výšku mapy
     * Podľa tejto premennej si môže hráč nastavovať jeho pozíciu na stred
     * @return - Samotnou návratovou hodnotou je hodnota maxWorldCol
     */
    public int getMaxWorldCol() {
        return this.maxWorldCol;
    }

    /**
     * Metóda getHp() slúži na vrátenie hodnoty HP, ktoré môže mať každá inštancia potomka inú
     * @return - Samotnou návratovou hodnotou je hodnota HP
     */
    public double getHp() {
        return this.hp;
    }

    /**
     * Metóda setHp() slúži na nastavenie novej hodnoty HP potomkovi
     * @param hp - Parameter hp slúži na nastavenie novej hodnoty
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Metóda uberHp() slúži na odčítanie hodnoty od aktuálnych HP inštancie
     * @param hp - Parameter hp slúži na počet ktorý sa má od aktuálnych HP odčítať
     */
    public void uberHp(double hp) {
        this.hp -= hp;
    }

    /**
     * Metóda getMaxHp() slúži na vrátenie hodnoty MaxHP, ktorú môže mať každá inštancia potomka inú
     * @return - Samotnou návratovou hodnotou je hodnota MaxHP
     */
    public double getMaxHp() {
        return this.maxHp;
    }

    /**
     * Metóda setMaxHp() slúži na nastavenie novej hodnoty maximálnych HP inštancie potomka
     * @param maxHp - Parameter maxHp slúži na nastavenie novej hodnoty
     */
    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * Metóda getDamage() slúži na vrátenie hodnoty poškodenia (Damage) od inštancie potomka
     * @return - Samotnou návratovou hodnotou je hodnota poškodenia (Damage)
     */
    public double getDamage() {
        return this.damage;
    }

    /**
     * Metóda setDamage() slúži na nastavenie novej hodnoty poškodenia inštancie potomka
     * @param damage - Parameter damage slúži na nastavenie novej hodnoty
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Metóda getLastDamageTime() slúži na vrátenie hodnoty, kedy bol potomok naposledy poškodený
     * @return - Samotnou návratovou hodnotou je čas, lastDamageTime
     */
    public long getLastDamageTime() {
        return this.lastDamageTime;
    }

    /**
     * Metóda setLastDamageTime() slúži na nastavenie novej hodnoty času, kedy bol potomok naposledy poškodený
     * @param lastDamageTime - Parameter lastDamageTime slúži na nastavenie novej hodnoty
     */
    public void setLastDamageTime(long lastDamageTime) {
        this.lastDamageTime = lastDamageTime;
    }

    /**
     * Metóda getObrazokEntity() slúži na vrátenie string cesty k obrázku, ktorú používa inštancia Ghost
     * @return - Samotnou návratovou hodnotou je String
     */
    public String getObrazokEntity() {
        return this.obrazokEntity;
    }

    /**
     * Metóda dajHealthBar() slúži na vytvorenie nového HealthBaru pre hocijakú inštanciu potomka
     * @return - Samotnou návratovou hodnotou je nový HealthBar
     */
    public HealthBar dajHealthBar() {
        return new HealthBar();
    }

    /**
     * Metóda setKolizia() slúži na nastavenie kolízie danej inštancie potomka
     * @param kolizia - Parameter kolizia slúži na nastavenie novej boolean hodnoty
     */
    public void setKolizia(boolean kolizia) {
        this.kolizia = kolizia;
    }

    /**
     * Metóda getKolizia() slúži na vrátenie boolean hodnoty toho, či je inštancia potomka v kolízii
     * @return - Samotnou návratovou hodnotou je boolean hodnota kolízie
     */
    public boolean getKolizia() {
        return this.kolizia;
    }
}
