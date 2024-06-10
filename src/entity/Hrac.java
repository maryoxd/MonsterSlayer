package entity;
import bary.HealthBar;
import bary.SkoreBar;
import bary.TimerBar;
import itemy.Inventar;
import itemy.brnenia.Brnenie;
import itemy.mece.Mec;
import main.GamePanel;
import main.KeyHandler;
import manazeri.ManazerKolizie;
import manazeri.ManazerSvetov;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Trieda Hráč je potomkom triedy Entity, predstavuje samotného hráča ktorého ovláda človek klávesami
 */
public class Hrac extends Entity {
    private final KeyHandler keyHandler;
    private final ManazerSvetov manazerSvetov;
    private int pocitac = 0;
    private int cisloObrazku = 1;
    private int cisloSveta;
    private double skore;
    private double povodneHp;
    private final Inventar inventar;
    private final ManazerKolizie manazerKolizie;
    private final Rectangle koliznaArea;
    private final Rectangle defaultKoliznaArea;
    private final HealthBar healthBar;
    private final SkoreBar skoreBar;
    private final TimerBar timerBar;
    private Mec mec = null;
    private Brnenie brnenie = null;
    private boolean utocenie;
    private boolean ohnuVzdorny;
    private boolean maVsetkyKluce;
    private int pocetZabitychNepriatelov;

    /**
     * Konštruktor triedy Hráč
     * V samotnom konštruktéri sa nastavujú základné hodnoty pre hráča -> HP, MAXHP, RÝCHLOSŤ, DMG
     * @param keyHandler  - Parameter KeyHandler, keďže hráča chceme ovládať pomocou ActionListenerov, je to potrebný parameter
     * @param gamePanel - Parameter GamePanell, posúvame ďalej predkovi
     * @param manazerSvetov - Parameter ManazerSvetov, posúvame ďalej predkovi
     * @param manazerKolizie - Parameter ManazerKolizie, posúvame ďalej predkovi
     * @param inventar - Parameter Inventár, keďže hráč má svoj vlastný inventár na uskladnenie itemov
     */
    public Hrac(KeyHandler keyHandler, GamePanel gamePanel, ManazerSvetov manazerSvetov, ManazerKolizie manazerKolizie, Inventar inventar) {
        super(TypEntity.HRAC, gamePanel);
        this.keyHandler = keyHandler;
        this.manazerSvetov = manazerSvetov;
        this.inventar = inventar;
        this.manazerKolizie = manazerKolizie;
        this.koliznaArea = new Rectangle();
        this.defaultKoliznaArea = new Rectangle();
        this.healthBar = this.dajHealthBar();
        this.skoreBar = new SkoreBar();
        this.timerBar = new TimerBar();
        this.utocenie = false;
        this.maVsetkyKluce = false;
        this.setKoliznaArea();
        this.nastavCisloSveta();
        this.nastavZaklad();
        this.setHp(100);
        this.setDamage(0);
        this.setMaxHp(100);
        this.setSpeed(4);
        this.setOhnuvzdorny(false);
    }

    /**
     * Metóda nastavZaklad() slúži na nastavenie pozícií hráča v závislosti od toho, v akom Svete sa hráč nachádza
     * Hráčovi sa taktiež aktualizujú HP
     */
    @Override
    public void nastavZaklad() {
        this.povodneHp = this.getMaxHp();
        this.nastavCisloSveta();
        int stredX;
        int stredY;
        if (this.cisloSveta == 1) {
            stredX = (this.getMaxWorldCol() / 2) * this.getVelkostPolicka() - 23;
            stredY = (this.getMaxWorldRow() / 2) * this.getVelkostPolicka() - 25;
        } else {
            stredX = this.getVelkostPolicka() * 49;
            stredY = this.getVelkostPolicka() * 49;
        }
        this.setWorldY(stredY);
        this.setWorldX(stredX);
        this.setScreenX((this.getSirkaObrazovky() / 2) - (this.getVelkostPolicka() / 2));
        this.setScreenY((this.getVyskaObrazovky() / 2) - (this.getVelkostPolicka() / 2));
    }

    /**
     * Metóda getSolidArea() overriduje pôvodnú metódu z predka, slúži na vrátenie obdĺžnika (Rectangle) pomocou ktorého zisťujeme kolíziu
     * @return - Samotnou návratovou hodnotou je obdĺžnik (Rectangle)
     */
    @Override
    public Rectangle getSolidArea() {
        return this.koliznaArea;
    }

    /**
     * Metóda setKoliznaArea() overriduje pôvodnú metódu z predka, slúži na nastavenie hodnôt obdĺžnika (Rectangle)
     */

    @Override
    public void setKoliznaArea() {
        this.koliznaArea.x = 8;
        this.koliznaArea.y = 25;
        this.koliznaArea.width = 20;
        this.koliznaArea.height = 20;
        this.defaultKoliznaArea.x = 8;
        this.defaultKoliznaArea.y = 25;
        this.defaultKoliznaArea.width = 20;
        this.defaultKoliznaArea.height = 20;
    }

    /**
     * Metóda getDefaultSolidAreaHrac() slúži na vrátenie defaultných hodnôt druhého obdĺžnika (Rectangle)
     * @return - Samotnou návratovou hodnotou je druhý obdĺžnik (Rectangle)
     */

    public Rectangle getDefaultSolidAreaHrac() {
        return this.defaultKoliznaArea;
    }

    /**
     * Metóda update() overriduje pôvodnú metódu z predka, slúži na kontrolovanie toho či hráč nemá všetky kľúče (výhra hry)
     * Okrem toho nastavuje hráčovi zbraň, kontroluje kolíziu a ak hráč stlačí tlačídko, postavu táto metóda posunie
     */

    @Override
    public void update() {
        if (this.inventar.skontrolujKluce()) {
            this.maVsetkyKluce = true;
        }
        this.utocenie = this.keyHandler.getUtocenie();
        String nazovBrnenia = "";
        String nazovZbrane = "";
        if (this.brnenie != null) {
            nazovBrnenia = this.brnenie.getNazov();
        }
        if (this.mec != null) {
            nazovZbrane = this.mec.getNazov();
        }
        if (this.inventar.getHracovaZbran() != null && !this.inventar.getHracovaZbran().getNazov().equals(nazovZbrane)) {
            this.pridajMec(this.inventar.getHracovaZbran());
            this.setDamage(this.mec.getPoskodenie());
        }
        if (this.inventar.getHracoveBrnenie() != null && !this.inventar.getHracoveBrnenie().getNazov().equals(nazovBrnenia)) {
            Brnenie predosleBrnenie = this.brnenie;
            this.pridajBrnenie(this.inventar.getHracoveBrnenie());
            double bonusoveHPBrnenia = this.brnenie != null ? this.brnenie.getBonusoveHP() : 0;
            if (predosleBrnenie != null) {
                double bonusoveHPStarehoBrnenia = predosleBrnenie.getBonusoveHP();
                this.setHp(this.getHp() - bonusoveHPStarehoBrnenia + bonusoveHPBrnenia);
                if (this.povodneHp == 100) {
                    this.povodneHp = this.getHp() - bonusoveHPBrnenia;
                }
            } else {
                this.setHp(this.getHp() + bonusoveHPBrnenia);
                this.povodneHp = this.getHp() - bonusoveHPBrnenia;
            }
            this.setMaxHp(this.getHp());
        }
        if (this.keyHandler.isUpPressed() || this.keyHandler.isDownPressed() || this.keyHandler.isRightPressed() || this.keyHandler.isLeftPressed()) {
            if (this.keyHandler.isUpPressed()) {
                this.setSmer("hore");
            }
            if (this.keyHandler.isDownPressed()) {
                this.setSmer("dole");
            }
            if (this.keyHandler.isLeftPressed()) {
                this.setSmer("vlavo");
            }
            if (this.keyHandler.isRightPressed()) {
                this.setSmer("vpravo");
            }
            this.setKolizia(false);
            this.manazerKolizie.skontrolujPolicko(this);
            this.manazerKolizie.checkObject(this, this.inventar);
            this.manazerKolizie.checkDvere(this, this.inventar);

            if (!this.getKolizia()) {
                switch (this.getSmer()) {
                    case "hore" -> this.setWorldY(this.getWorldY() - this.getSpeed());
                    case "dole" -> this.setWorldY(this.getWorldY() + this.getSpeed());
                    case "vlavo" -> this.setWorldX(this.getWorldX() - this.getSpeed());
                    case "vpravo" -> this.setWorldX(this.getWorldX() + this.getSpeed());
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
     * Metóda draw slúži na samotné vykreslovanie objektu Hráča
     * Táto metóda slúži taktiež na kreslenie hráčovho meča
     * @param g2 - Parameter knižnice Graphics2D, keďze chceme Hráča vykresliť
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (this.getSmer()) {
            case "hore" -> {
                if (this.cisloObrazku == 1) {
                    image = this.getObrazok("hore", 1);
                }
                if (this.cisloObrazku == 2) {
                    image = this.getObrazok("hore", 2);
                }
            }
            case "dole" -> {
                if (this.cisloObrazku == 1) {
                    image = this.getObrazok("dole", 1);
                }
                if (this.cisloObrazku == 2) {
                    image = this.getObrazok("dole", 2);
                }
            }
            case "vlavo" -> {
                if (this.cisloObrazku == 1) {
                    image = this.getObrazok("vlavo", 1);
                }
                if (this.cisloObrazku == 2) {
                    image = this.getObrazok("vlavo", 2);
                }
            }
            case "vpravo" -> {
                if (this.cisloObrazku == 1) {
                    image = this.getObrazok("vpravo", 1);
                }
                if (this.cisloObrazku == 2) {
                    image = this.getObrazok("vpravo", 2);
                }
            }
        }
        g2.drawImage(image, this.getScreenX(), this.getScreenY(), this.getVelkostPolicka(), this.getVelkostPolicka(), null);
        this.healthBar.draw(g2, this.getScreenX() / 2, this.getScreenY() / 2, this.getHp(), true, this.getMaxHp());
        this.skoreBar.draw(g2, this.getScreenX() / 2, this.getScreenY() / 2, this.skore);
        this.timerBar.draw(g2, this.getScreenX() / 2, this.getScreenY() / 2);
        int vykreslovanieX = 0;
        int vykreslovanieY = 0;
        if (this.mec != null) {
            switch (this.getSmer()) {
                case "vpravo" -> {
                    vykreslovanieX = this.getScreenX() + 30;
                    vykreslovanieY = this.getScreenY() + 10;
                }
                case "vlavo" -> {
                    vykreslovanieX = this.getScreenX() - 30;
                    vykreslovanieY = this.getScreenY() + 10;
                }
                case "hore" -> {
                    vykreslovanieX = this.getScreenX();
                    vykreslovanieY = this.getScreenY() - 15;
                }
                case "dole" -> {
                    vykreslovanieX = this.getScreenX();
                    vykreslovanieY = this.getScreenY() + 35;
                }
            }
            this.mec.drawHracovMec(g2, vykreslovanieX, vykreslovanieY, this.getVelkostPolicka(), this);
        }
    }

    /**
     * Metóda kontrolujZivoty() slúži na vracanie boolean hodnoty v závislosti od toho, či je hráč nažive (má viac ako 0HP)
     * @return - Samotnou návratovou hodnotou je boolean hodnota
     */
    public boolean kontrolujZivoty() {
        return this.getHp() > 0;
    }

    /**
     * Metóda pridajSkore() slúži na pridanie skóre hráčovi, metóda sa používa len ak hráč zabije inštanciu triedy Nepriatel
     * @param skore - Parameter skore slúži na pridanie nového skóre
     */
    public void pridajSkore(double skore) {
        this.skore += skore;
    }

    /**
     * Metóda getSkore() slúži na vrátenie hodnoty skore, teda vrátenie počtu skóre ktorým hráč disponunje
     * @return - Samotnou návratovou hodnotou je počet skóre hráča
     */
    public double getSkore() {
        return this.skore;
    }

    /**
     * Metóda pridajPocetZabitych() slúži na pripočítanie k počtu zabitých nepriateľov
     */
    public void pridajPocetZabitych() {
        this.pocetZabitychNepriatelov++;
    }

    /**
     * Metóda getPocetZabitychNepriatelov() slúži na vrátenie hodnoty toho, koľko nepriateľov hráč už zabil
     * @return - Samotnou návratovou hodnotou je počet zabitých nepriateľov, teda pocetZabitychNepriatelov
     */
    public int getPocetZabitychNepriatelov() {
        return this.pocetZabitychNepriatelov;
    }

    /**
     * Metóda pridajMec() slúži na priradenie meču k hráčovi
     * @param mec - Parameter mec slúži ako nový meč, ktorý bude hráčovi pridelený
     */
    public void pridajMec(Mec mec) {
        this.mec = mec;
    }

    /**
     * Metóda pridajBrnenie() slúži na priradenie brnenia k hráčovi
     * @param brnenie - Parameter brnenie slúži ako nové brnenie, ktoré bude hráčovi pridelené
     */
    public void pridajBrnenie(Brnenie brnenie) {
        this.brnenie = brnenie;
    }

    /**
     * Metóda getUtocenie() slúži na vrátenie hodnoty toho, či hráč útočí
     * @return - Samotná návratová hodnota je boolean hodnota toho či hráč útočí, teda utocenie
     */
    public boolean getUtocenie() {
        return this.utocenie;
    }

    /**
     * Metóda getOhnuvzdorny() overriduje metódu z predka a vracia false, keďže hráč nie je ohňuvzdorný
     * @return - Samotnou návratovou hodnotou je boolean hodnota toho, či je hráč ohňuvzdorný
     */
    @Override
    public boolean getOhnuvzdorny() {
        return this.ohnuVzdorny;
    }

    /**
     * Metóda setOhnuvzdorny() slúži na nastavenie novej hodnoty toho, či je hráč ohňuvzdorný
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej hodnoty ohnuVzdorny
     */
    @Override
    public void setOhnuvzdorny(boolean novaHodnota) {
        this.ohnuVzdorny = novaHodnota;
    }

    /**
     * Metóda maVsetkyKluce() slúži na kontrolu toho, či hráč disponuje všetkými kľúčami potrebnými ku tomu, aby vyhral hru.
     * @return - Samotnou návratovou hodnotou je boolean hodnota toho, či má hráč všetky kľúče
     */
    public boolean maVsetkyKluce() {
        return !this.maVsetkyKluce;
    }

    /**
     * Metóda nastavCisloSveta() slúži na nastavenie nového čísla Sveta, ktoré získame od Manažéra Svetov
     */
    public void nastavCisloSveta() {
        this.cisloSveta = this.manazerSvetov.getCisloSveta();
    }
}