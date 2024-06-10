package entity;
import bary.HealthBar;
import main.GamePanel;
import manazeri.ManazerKolizie;
import manazeri.ManazerSvetov;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Trieda Ghost je potomkom triedy Nepriatel, predstavuje nepriateľa, ktorý spôsobuje hráčovi poškodenie a hráč naňho môže zaútočiť
 */
public class Ghost extends Nepriatel {
    private final Rectangle koliznaArea;
    private boolean aktivny;
    private final int polomerAktivity;
    private BufferedImage obrazok;
    private final HealthBar healthBar;
    private final String obrazokEntity;

    /**
     * Konštruktor triedy Ghost
     * @param gamePanel - Parameter GamePanel, posúvame ďalej predkovi
     * @param manazerSvetov - Parameter ManazerSvetov, posúvame ďalej predkovi
     * @param manazerKolizie - Parameter ManazerKolizie, posúvame ďalej predkovi
     * @param cisloSveta - Parameter ktorý určuje, v akom Svete sa má samotná inštancia tejto triedy nachádzať
     * @param worldX - Parameter ktorý určuje samotnú X hodnotu inštancie na mape - posúvame predkovi
     * @param worldY - Parameter ktorý určuje samotnú Y hodnotu inštancie na mape - posúvame predkovi
     */
    public Ghost(GamePanel gamePanel, ManazerSvetov manazerSvetov, ManazerKolizie manazerKolizie, int cisloSveta, int worldX, int worldY) {
        super(TypEntity.GHOST, gamePanel, manazerSvetov, manazerKolizie, cisloSveta, worldX, worldY);
        this.aktivny = true;
        this.polomerAktivity = 150;
        this.koliznaArea = new Rectangle();
        this.healthBar = this.dajHealthBar();
        this.obrazokEntity = this.getObrazokEntity();
        this.setKoliznaArea();
        this.nastavObrazky();
    }

    /**
     * Metóda setKoliznaArea() slúži na nastavenie hodnôt obdĺžnika - X, Y, WIDTH, HEIGHT
     */
    @Override
    public void setKoliznaArea() {
        this.koliznaArea.x = 8;
        this.koliznaArea.y = 30;
        this.koliznaArea.width = 35;
        this.koliznaArea.height = 35;
    }

    /**
     * Metóda getSolidArea() slúži na vrátenie obdĺžnika (Rectangle), pomocou ktorého sa overuje kolízia
     * @return - return kolíznejArea, teda obdĺžnika (Rectangle)
     */
    @Override
    public Rectangle getSolidArea() {
        return this.koliznaArea;
    }

    /**
     * Metóda nastavObrazky() Overriduje pôvodnú metódu, keďže Ghost si zmení obrázok pokiaľ je pri hráčovi (je aktívny)
     * Obrázok sa mení v závislosti od Smeru tejto inštancie
     */
    @Override
    public void nastavObrazky() {
        try {
            if (!this.aktivny) {
                super.nastavObrazky();
                switch (this.getSmer()) {
                    case "hore" -> {
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = this.getObrazok("hore", 1);
                        }
                        if (this.getCisloObrazku() == 2) {
                            this.obrazok = this.getObrazok("hore", 2);
                        }
                    }
                    case "dole" -> {
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = this.getObrazok("dole", 1);
                        }
                        if (this.getCisloObrazku() == 2) {
                            this.obrazok = this.getObrazok("dole", 2);
                        }
                    }
                    case "vlavo" -> {
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = this.getObrazok("vlavo", 1);
                        }
                        if (this.getCisloObrazku() == 2) {
                            this.obrazok = this.getObrazok("vlavo", 2);
                        }
                    }
                    case "vpravo" -> {
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = this.getObrazok("vpravo", 1);
                        }
                        if (this.getCisloObrazku() == 2) {
                            this.obrazok = this.getObrazok("vpravo", 2);
                        }
                    }
                }
            } else {
                switch (this.getSmer()) {
                    case "vpravo" -> {
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vpravo1Aktivovany.png"));
                        } else {
                            this.obrazok = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vpravo2Aktivovany.png"));
                        }
                    }
                    case "vlavo" -> {
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vlavo1Aktivovany.png"));
                        } else {
                            this.obrazok = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "vlavo2Aktivovany.png"));
                        }
                    }
                    case "dole" -> {
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "dole1Aktivovany.png"));
                        } else {
                            this.obrazok = ImageIO.read(new FileInputStream("res/" + this.obrazokEntity + "dole2Aktivovany.png"));
                        }
                    }
                    case "hore" -> {
                        super.nastavObrazky();
                        if (this.getCisloObrazku() == 1) {
                            this.obrazok = this.getObrazok("hore", 1);
                            break;
                        }
                        if (this.getCisloObrazku() == 2) {
                            this.obrazok = this.getObrazok("hore", 2);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metóda update overriduje pôvodnú metódu, používa jej vlastnosti s dodatočnou úpravou, nastavenia obrázkov
     */
    @Override
    public void update() {
        super.update();
        this.nastavObrazky();
    }

    /**
     * Metóda draw() slúži na samotné kreslenie Ghosta
     * Pokiaľ nie je Ghost aktívny (nie je pri ňom hráč dostatočne blízko), volá sa kreslenie z predka pomocou super()
     * @param g2 - Parameter knižnice Graphics2D, keďže objekt chceme kresliť
     * @param hrac - Parameter Hráča, keďze objekt chceme vykreslovať len ak sa pri ňom hráč nachádza
     */
    @Override
    public void draw(Graphics2D g2, Hrac hrac) {
        int velkostPolicka = this.getVelkostPolicka();
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
            if (!this.aktivny) {
                super.draw(g2, hrac);
            } else {
                g2.drawImage(this.obrazok, screenX, screenY, this.getVelkostPolicka(), this.getVelkostPolicka(), null);
                this.healthBar.draw(g2, screenX, screenY, this.getHp(), false, this.getMaxHp());
            }
        }
    }

    /**
     * Metóda hracVBlizkosti() slúži na zistenie toho, či sa hráč nachádza v blízkosti Ghosta, ak áno, nastaví sa na aktívneho a zmení si obrázok
     * Pokiaľ je Ghost aktívny, taktiež sa prbližuje k hráčovi, čím mu spôsobuje poškodenie
     * @param hrac - Parameter Hráča, keďže v metóde chceme zistiť podľa pozícií Hráča, či je blízko
     */
    public void hracVBlizkosti(Hrac hrac) {
        int hracX = hrac.getWorldX();
        int hracY = hrac.getWorldY();
        int mojaX = this.getWorldX();
        int mojaY = this.getWorldY();
        double vzdialenost = Math.sqrt(Math.pow(hracX - mojaX, 2) + Math.pow(hracY - mojaY, 2));
        int tolerancia = 5;
        if (vzdialenost <= this.polomerAktivity + tolerancia) {
            this.aktivny = true;
            double smerX = (hracX - mojaX) / vzdialenost;
            double smerY = (hracY - mojaY) / vzdialenost;
            double priblizovanieX = 2 * smerX;
            double priblizovanieY = 2 * smerY;
            if (mojaX + priblizovanieX > 0 && mojaY + priblizovanieY > 0) {
                this.setWorldX((int)(mojaX + priblizovanieX));
                this.setWorldY((int)(mojaY + priblizovanieY));
                this.setSmer(hrac.getSmer());
            }
        } else {
            this.aktivny = false;
        }
    }
}
