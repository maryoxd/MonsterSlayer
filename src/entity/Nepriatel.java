package entity;
import bary.HealthBar;
import main.GamePanel;
import manazeri.ManazerKolizie;
import manazeri.ManazerSvetov;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Trieda Nepriatel predstavuje potomka triedy Entity, avšak zároveň je aj predok tried CervenyDrak, Dino, Ghost, Skeleton, Zombie
 */
public class Nepriatel extends Entity {
    private final int cisloSveta;
    private final ManazerSvetov manazerSvetov;
    private final ManazerKolizie manazerKolizie;
    private int pocitac;
    private int cisloObrazku;
    private double skoreZabitia;
    private boolean ohnuVzdorny;
    private final HealthBar healthBar;
    private int pocetKrokov;
    private boolean aktivny;

    /**
     * Konštruktor triedy Nepriatel
     * @param typEntity - Parameter TypEntity predstavuje ENUM Nepriatela, ktorý musí byť pridelený -> posúvame ďalej predkovi Entity
     * @param gamePanel - Parameter GamePanel -> posúvame ďalej predkovi
     * @param manazerSvetov - Parameter ManazerSvetov -> posúvame ďalej predkovi
     * @param manazerKolizie - Parameter ManazerKolizie -> posúvame ďalej predkovi
     * @param cisloSveta - Parameter CisloSveta -> posúvame ďalej predkovi
     * @param worldX - Parameter worldX slúži na priame nastavenie hodnoty X inštancii potomka tejto triedy
     * @param worldY - Parameter worldY slúži na priame nastavenie hodnoty Y inštancii potomka tejto triedy
     */
    public Nepriatel(TypEntity typEntity, GamePanel gamePanel, ManazerSvetov manazerSvetov, ManazerKolizie manazerKolizie, int cisloSveta, int worldX, int worldY) {
        super(typEntity, gamePanel);
        this.cisloSveta = cisloSveta;
        this.manazerSvetov = manazerSvetov;
        this.manazerKolizie = manazerKolizie;
        this.setWorldX(worldX * this.getVelkostPolicka());
        this.setWorldY(worldY * this.getVelkostPolicka());
        this.ohnuVzdorny = false;
        this.pocitac = 0;
        this.cisloObrazku = 1;
        this.pocetKrokov = 0;
        this.healthBar = this.dajHealthBar();
        this.aktivny = true;
        this.nastavZaklad();
    }

    /**
     * Metóda update() overriduje pôvodnú metódu z predka, slúži na updatovanie inštancií tejto triedy
     * Okrem inštancií tried Ghost a CervenyDrak sa ostatné inštancie updatujú totožne
     */
    @Override
    public void update() {
        if (this instanceof Zombie || this instanceof Ghost || this instanceof Skeleton || this instanceof Dino) {
            if (this.manazerSvetov.getCisloSveta() == this.cisloSveta) {
                this.setKolizia(false);
                String aktSmer = this.getSmer();
                boolean aktKolizia = this.getKolizia();
                Random rand = new Random();
                int nahodnySmer = rand.nextInt(100) + 1;
                if (aktKolizia) {
                    switch (nahodnySmer % 4) {
                        case 0 -> this.setSmer("hore");
                        case 1 -> this.setSmer("dole");
                        case 2 -> this.setSmer("vlavo");
                        case 3 -> this.setSmer("vpravo");
                    }
                } else {
                    switch (aktSmer) {
                        case "hore" -> {
                            if (nahodnySmer <= 90) {
                                this.setSmer("hore");
                            } else if (nahodnySmer <= 95) {
                                this.setSmer("vlavo");
                            } else {
                                this.setSmer("vpravo");
                            }
                        }
                        case "dole" -> {
                            if (nahodnySmer <= 90) {
                                this.setSmer("dole");
                            } else if (nahodnySmer <= 95) {
                                this.setSmer("vlavo");
                            } else {
                                this.setSmer("vpravo");
                            }
                        }
                        case "vlavo" -> {
                            if (nahodnySmer <= 90) {
                                this.setSmer("vlavo");
                            } else if (nahodnySmer <= 95) {
                                this.setSmer("hore");
                            } else {
                                this.setSmer("dole");
                            }
                        }
                        case "vpravo" -> {
                            if (nahodnySmer <= 90) {
                                this.setSmer("vpravo");
                            } else if (nahodnySmer <= 95) {
                                this.setSmer("hore");
                            } else {
                                this.setSmer("dole");
                            }
                        }
                    }
                }
                if (!(this instanceof Ghost)) {
                    this.manazerKolizie.skontrolujPolicko(this);
                    if (!this.getKolizia()) {
                        switch (this.getSmer()) {
                            case "hore" -> this.setWorldY(this.getWorldY() - this.getSpeed());
                            case "dole" -> this.setWorldY(this.getWorldY() + this.getSpeed());
                            case "vlavo" -> this.setWorldX(this.getWorldX() - this.getSpeed());
                            case "vpravo" -> this.setWorldX(this.getWorldX() + this.getSpeed());
                        }
                    }
                } else {
                    switch (this.getSmer()) {
                        case "hore" -> this.setWorldY(this.getWorldY() - this.getSpeed());
                        case "dole" -> this.setWorldY(this.getWorldY() + this.getSpeed());
                        case "vlavo" -> this.setWorldX(this.getWorldX() - this.getSpeed());
                        case "vpravo" -> this.setWorldX(this.getWorldX() + this.getSpeed());
                    }
                }
            }
        } else if (this instanceof CervenyDrak) {
            this.setKolizia(false);
            if (this.cisloSveta == this.manazerSvetov.getCisloSveta()) {
                this.manazerKolizie.skontrolujPolicko(this);
                if (this.pocetKrokov < 80 && !this.getKolizia()) {
                    this.setSmer("vpravo");
                } else if (this.pocetKrokov >= 80 && this.pocetKrokov < 160) {
                    this.setSmer("vlavo");
                }

                if (!this.getKolizia()) {
                    switch (this.getSmer()) {
                        case "vpravo" -> this.setWorldX(this.getWorldX() + this.getSpeed());
                        case "vlavo" -> this.setWorldX(this.getWorldX() - this.getSpeed());
                    }
                    this.pocetKrokov++;
                    if (this.pocetKrokov >= 160) {
                        this.pocetKrokov = 0;
                    }
                }
            }
        }
        this.pocitac++;
        if (this.pocitac > 12) {
            if (this.cisloObrazku == 1) {
                this.cisloObrazku = 2;
            } else if (this.cisloObrazku == 2) {
                this.cisloObrazku = 1;
            }
            this.pocitac = 0;
        }
    }

    /**
     * Metóda nastavZaklad() overriduje pôvodnú metódu z predka, slúži na nastavenie základných hodnôť inštancií tejto triedy, v závislosti od toho o akú inštanciu sa jednná
     */
    @Override
    public void nastavZaklad() {
        if (this instanceof Zombie) {
            this.setSpeed(1);
            this.setHp(100);
            this.setMaxHp(100);
            this.setDamage(7.5);
            this.skoreZabitia = 5.5;
            this.setOhnuvzdorny(false);
        }
        if (this instanceof Ghost) {
            this.setSpeed(1);
            this.setHp(150);
            this.setMaxHp(150);
            this.setDamage(10);
            this.skoreZabitia = 10;
            this.setOhnuvzdorny(true);
        }
        if (this instanceof Skeleton) {
            this.setSpeed(1);
            this.setHp(80);
            this.setMaxHp(80);
            this.setDamage(3);
            this.skoreZabitia = 3;
            this.setOhnuvzdorny(false);
        }
        if (this instanceof Dino) {
            this.setSpeed(2);
            this.setHp(200);
            this.setMaxHp(200);
            this.setDamage(4);
            this.skoreZabitia = 15;
            this.setOhnuvzdorny(true);
        }
        if (this instanceof CervenyDrak) {
            this.setSpeed(1);
            this.setHp(175);
            this.setMaxHp(175);
            this.setDamage(8);
            this.skoreZabitia = 18;
            this.setOhnuvzdorny(true);
        }
    }

    /**
     * Metóda getSolidArea() overriduje pôvodnú metódu z predka, slúži na ďalšie overridovanie potomkami
     * @return - Samotnou návratovou hodnotou je null
     */
    @Override
    public Rectangle getSolidArea() {
        return null;
    }

    /**
     * Metóda setKoliznaArea() overriduje pôvodnú metódu z predka, slúži na ďalšie overridovanie potomkami
     */
    @Override
    public void setKoliznaArea() {
    }

    /**
     * Metóda draw slúži na samotné vykreslovanie inštancií tejto triedy
     * @param g2 - Parameter knižnice Graphics2D, keďže chceme tieto objekty vykreslovať
     * @param hrac - Parameter Hráča, keďže chceme objekty vykreslovať len ak je hráč pri nich
     */
    public void draw(Graphics2D g2, Hrac hrac) {
        int velkostPolicka = this.getVelkostPolicka();
        BufferedImage image = null;
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
            switch (this.getSmer()) {
                case "hore" -> {
                    if (this.getCisloObrazku() == 1) {
                        image = this.getObrazok("hore", 1);
                    }
                    if (this.getCisloObrazku() == 2) {
                        image = this.getObrazok("hore", 2);
                    }
                }
                case "dole" -> {
                    if (this.getCisloObrazku() == 1) {
                        image = this.getObrazok("dole", 1);
                    }
                    if (this.getCisloObrazku()  == 2) {
                        image = this.getObrazok("dole", 2);
                    }
                }
                case "vlavo" -> {
                    if (this.getCisloObrazku()  == 1) {
                        image = this.getObrazok("vlavo", 1);
                    }
                    if (this.getCisloObrazku()  == 2) {
                        image = this.getObrazok("vlavo", 2);
                    }
                }
                case "vpravo" -> {
                    if (this.getCisloObrazku()  == 1) {
                        image = this.getObrazok("vpravo", 1);
                    }
                    if (this.getCisloObrazku()  == 2) {
                        image = this.getObrazok("vpravo", 2);
                    }
                }
            }
            g2.drawImage(image, screenX, screenY, this.getVelkostPolicka(), this.getVelkostPolicka(), null);
            this.healthBar.draw(g2, screenX, screenY, this.getHp(), false, this.getMaxHp());
        }
    }

    /**
     * Metóda getCisloObrazku() slúži na vrátenie int hodnoty obrázku, ktorý potomok potrebuje
     * @return - Samotnou návratovou hodnotou je číslo obrázku, teda cisloObrazku
     */
    public int getCisloObrazku() {
        return this.cisloObrazku;
    }

    /**
     * Metóda getCisloSveta() slúži na vrátenie hodnoty čísla Sveta, ktoré je pridelené konštruktorom inštancie potomka
     * @return - Samotnou návratovou hodonotou je číslo Sveta inštancie, teda cisloSveta
     */

    public int getCisloSveta() {
        return this.cisloSveta;
    }

    /**
     * Metóda getSkoreZabitia() slúži na vrátenie hodnoty skóre, ktoré si hráč pripíše ak zabije inštanciu potomka tejto triedy
     * @return - Samotná návratová hodnota je skóre zabitia, teda skoreZabitia
     */
    public double getSkoreZabitia() {
        return this.skoreZabitia;
    }

    /**
     * Metóda getOhnuVzdorny() slúži na vrátenie hodnoty toho, či je inštancia ohňuvzdorná
     * @return - Samotná návratová hodnota je boolean hodnota toho, či je inštancia ohňuvzdorná, teda ohnuVzdorny
     */
    @Override
    public boolean getOhnuvzdorny() {
        return !this.ohnuVzdorny;
    }

    /**
     * Metóda setOhnuVzdorny() slúži na nastavenie novej hodnoty toho, či je inštancia ohňuvzdorná
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej hodnoty pre ohňuvzdornosť, teda ohnuVzdorny
     */
    @Override
    public void setOhnuvzdorny(boolean novaHodnota) {
        this.ohnuVzdorny = novaHodnota;
    }

    /**
     * Metóda jeAktivny() slúži na vrátenie boolean hodnoty toho, či je inštancia aktívna (či sa má vykreslovať a nie je už mŕtva)
     * @return - Samotnou návratovou hodnotou je boolean hodnota toho, či je inštancia aktívna, teda aktivny
     */
    public boolean jeAktivny() {
        return this.aktivny;
    }

    /**
     * Metóda setAktivny() slúži na nastavenie hodnoty toho, či je inštancia aktívna
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej hodnoty toho, či je inštancia aktívna, teda aktívny
     */
    public void setAktivny(boolean novaHodnota) {
        this.aktivny = novaHodnota;
    }
}
