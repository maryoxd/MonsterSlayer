package itemy;
import itemy.brnenia.Brnenie;
import itemy.mece.Mec;
import main.GamePanel;
import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Trieda Inventar slúži ako inventár hráča, v ktorom sa uskladňujú itemy ktoré hráč pozbieral
 */
public class Inventar {
    private final Item[] inventar;
    private final Item[] slotZbrane;
    private final Item[] slotArmoru;
    private int riadok = 0;
    private int stlpec = 0;
    private final String[] nazvyVyhernychKlucov;
    private final int velkostPolicka;
    private int polickoInventaraX = 452;
    private int polickoInvetaraY = 164;
    private final GamePanel gamePanel;

    /**
     * Konštruktor triedy Inventar
     * V konštruktori inicializujeme pole výherných kľúčov a polia samotného inventára
     * @param gamePanel - Parameter GamePanelu, keďže chceme použiť veľkosť políčka z triedy GamePanel
     */
    public Inventar(GamePanel gamePanel) {
        this.inventar = new Item[9];
        this.slotZbrane = new Item[1];
        this.slotArmoru = new Item[1];
        this.nazvyVyhernychKlucov = new String[]{"Kluc1", "Kluc2", "Kluc3", "Kluc4", "Kluc5", "Kluc6"};
        this.velkostPolicka = gamePanel.getVelkostPolicka();
        this.gamePanel = gamePanel;
    }

    /**
     * Metóda pridajVec() slúži na pridanie itemu na miesto pola, ktoré je prázdne
     * @param item - Parameter item slúži ako item, ktorý chceme do inventára pridávať
     */
    public void pridajVec(Item item) {
        if (item instanceof Mec && this.slotZbrane[0] == null) {
            this.slotZbrane[0] = item;
        } else if (item instanceof Brnenie && this.slotArmoru[0] == null) {
            this.slotArmoru[0] = item;
        } else {
            for (int i = 0; i < this.inventar.length; i++) {
                if (this.inventar[i] == null) {
                    this.inventar[i] = item;
                    return;
                } else if (this.inventar[i].getNazov().equals(item.getNazov())) {
                    return;
                }
            }
        }
    }

    /**
     * Metóda skontrolujKluce() slúži na skontrolovanie skutočnosti, či už hráč má v inventári všetký kľúče, s ktorými vyhrýva hru
     * @return - Samotnou návratovou hodnotou je boolean hodnota toho, či má hrááč všetky kľúče potrebné k výhre
     */
    public boolean skontrolujKluce() {
        for (String kluc : this.nazvyVyhernychKlucov) {
            boolean najdeny = false;
            for (Item item : this.inventar) {
                if (item != null && item.getNazov().equals(kluc)) {
                    najdeny = true;
                    break;
                }
            }
            if (!najdeny) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metóda draw() slúži na vykreslovanie samotného inventára, itemov v inventári a hráčových slotov na zbraň a brnenie
     * @param g2 - Parameter knižnice Graphics2D, keďže chceme objekt vykresliť
     * @param keyHandler - Parameter keyHandler, keďže kreslíme aj štvorec miesta v inventári, pokiaľ s ním hráč hýbe
     */
    public void draw(Graphics2D g2, KeyHandler keyHandler) {
        if (keyHandler.isIpressed() && (this.inventar[0] != null || this.slotArmoru[0] != null || this.slotZbrane[0] != null)) {
            int frameX = this.velkostPolicka * 9;
            int frameY = this.velkostPolicka * 3;
            int frameWidth = this.velkostPolicka * 6;
            int frameHeight = this.velkostPolicka * 5;
            BufferedImage image;
            try {
                image = ImageIO.read(new FileInputStream("res/player/dole1.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int polickoVybaveniaX = this.velkostPolicka * 2;
            int polickoVybaveniaY = this.velkostPolicka * 5;
            Color cierna = new Color(0, 0, 0, 210);
            Color biela = new Color(255, 255, 255);
            g2.setColor(cierna);
            g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);
            g2.drawImage(image, polickoVybaveniaX - 50, polickoVybaveniaY - 50, this.velkostPolicka * 4, this.velkostPolicka * 4, null);
            g2.setStroke(new BasicStroke(5));
            g2.fillRoundRect(polickoVybaveniaX + 20, polickoVybaveniaY + 50, this.velkostPolicka, this.velkostPolicka, 33, 33);
            g2.fillRoundRect(polickoVybaveniaX + 100, polickoVybaveniaY + 50, this.velkostPolicka, this.velkostPolicka, 33, 33);
            g2.setColor(biela);
            g2.drawRoundRect(polickoVybaveniaX + 20, polickoVybaveniaY + 50, this.velkostPolicka, this.velkostPolicka, 32, 23);
            g2.drawRoundRect(polickoVybaveniaX + 100, polickoVybaveniaY + 50, this.velkostPolicka, this.velkostPolicka, 32, 23);
            g2.drawRoundRect(frameX + 5, frameY + 5, frameWidth - 10, frameHeight - 10, 25, 25);
            if (keyHandler.isSipkaDole() && this.stlpec != 3) {
                this.stlpec++;
            }
            if (keyHandler.isSipkaVpravo() && this.riadok != 4) {
                this.riadok++;
            }
            if (keyHandler.isSipkaHore() && this.stlpec != 0) {
                this.stlpec--;
            }
            if (keyHandler.isSipkaVlavo() && this.riadok != 0) {
                this.riadok--;
            }
            int slotXstart = frameX + 20;
            int slotYstart = frameY + 20;
            int slotX = slotXstart;
            int slotY = slotYstart;
            for (int i = 0; i < this.inventar.length; i++) {
                if (this.inventar[i] != null) {
                    BufferedImage obrazok = this.inventar[i].getImage();
                    g2.drawImage(obrazok, slotX, slotY, this.velkostPolicka, this.velkostPolicka, null);
                    slotX += this.velkostPolicka;
                    if (i == 4 || i == 9) {
                        slotX = slotXstart;
                        slotY += this.velkostPolicka;
                    }
                }
            }
            int cursorX = slotXstart + (this.velkostPolicka * this.riadok);
            int cursorY = slotYstart + (this.velkostPolicka * this.stlpec);
            g2.setColor(biela);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, this.velkostPolicka, this.velkostPolicka, 10, 10);
            this.polickoInventaraX = cursorX;
            this.polickoInvetaraY = cursorY;
            if (this.slotZbrane[0] != null) {
                BufferedImage obrazok = this.slotZbrane[0].getImage();
                g2.drawImage(obrazok, polickoVybaveniaX + 100, polickoVybaveniaY + 50, this.velkostPolicka, this.velkostPolicka, null);
            }
            if (this.slotArmoru[0] != null) {
                BufferedImage obrazok = this.slotArmoru[0].getImage();
                g2.drawImage(obrazok, polickoVybaveniaX + 20, polickoVybaveniaY + 50, this.velkostPolicka, this.velkostPolicka, null);
            }
        }
    }

    /**
     * Metóda update() slúži na zavolanie metódy na vymenenie prvku v poli inventára, pokiaľ hráč stlačí enter
     * @param keyHandler - Parameter keyHandler slúži na zistenie skutočnosti, či hráč stlačil klávesu enter
     */
    public void update(KeyHandler keyHandler) {
        if (keyHandler.isEnterPressed()) {
            this.vymenPrvok();
        }
    }

    /**
     * Metóda vymenPrvok slúži na vymenenie prvku v inventári
     */
    public void vymenPrvok() {
        int prvok = 0;
        if (this.polickoInvetaraY == 164) {
            prvok = switch (this.polickoInventaraX) {
                case 452 -> 0;
                case 500 -> 1;
                case 548 -> 2;
                case 596 -> 3;
                case 644 -> 4;
                default -> throw new IllegalStateException();
            };
        }
        if (this.polickoInvetaraY == 212) {
            prvok = switch (this.polickoInventaraX) {
                case 452 -> 5;
                case 500 -> 6;
                case 548 -> 7;
                case 596 -> 8;
                case 644 -> 9;
                default -> throw new IllegalStateException();
            };
        }
        if (this.polickoInvetaraY == 260) {
            prvok = switch (this.polickoInventaraX) {
                case 452 -> 10;
                case 500 -> 11;
                case 548 -> 12;
                case 596 -> 13;
                case 644 -> 14;
                default -> throw new IllegalStateException();
            };
        }
        if (this.polickoInvetaraY == 308) {
            prvok = switch (this.polickoInventaraX) {
                case 452 -> 15;
                case 500 -> 16;
                case 548 -> 17;
                case 596 -> 18;
                case 644 -> 19;
                default -> throw new IllegalStateException();
            };
        }
        if (this.inventar[prvok] instanceof Mec) {
            Mec mojMec = (Mec)this.slotZbrane[0];
            Mec inventarovyMec;
            inventarovyMec = (Mec)this.inventar[prvok];
            this.inventar[prvok] = mojMec;
            this.slotZbrane[0] = inventarovyMec;
            this.gamePanel.updatePanel();
        } else if (this.inventar[prvok] instanceof Brnenie) {
            Brnenie mojeBrnenie = (Brnenie)this.slotArmoru[0];
            Brnenie inventaroveBrnenie;
            inventaroveBrnenie = (Brnenie)this.inventar[prvok];
            this.inventar[prvok] = mojeBrnenie;
            this.slotArmoru[0] = inventaroveBrnenie;
            this.gamePanel.updatePanel();
        }
    }

    /**
     * Metóda getHracovaZbran() slúži na poskytnutie zbrane hráčovi, ktorá sa nachádza v slote zbrane v inventári
     * @return - Samotnou návratovou hodnotou je meč hráča
     */
    public Mec getHracovaZbran() {
        Mec navratovy = null;
        if (this.slotZbrane[0] != null) {
            navratovy = (Mec)this.slotZbrane[0];
        }
        return navratovy;
    }

    /**
     * Metóda getHracoveBrnenie() slúži na poskytnutie brnenia hráčovi, ktoré sa nachádza v slote brnenia v inventári
     * @return - Samotnou návratovou hodnotou je brnenie hráča
     */
    public Brnenie getHracoveBrnenie() {
        Brnenie navratove = null;
        if (this.slotArmoru[0] != null) {
            navratove = (Brnenie)this.slotArmoru[0];
        }
        return navratove;
    }

    /**
     * Metóda najdiKluc() slúži na nájdenie kľúču v inventári, podľa požadovaného parametra
     * @param nazovKlucu - Parameter nazovKlucu slúži na hľadanie itemu v poli na základe tohto parametra
     * @return - Samotnou návratovou hodnotou je String hodnota nájdeného itemu
     */
    public String najdiKluc(String nazovKlucu) {
        for (Item item : this.inventar) {
            if (item != null && item.getNazov().equals(nazovKlucu)) {
                return item.getNazov();
            }
        }
        return null;
    }
}








