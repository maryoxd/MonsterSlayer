package main;
import entity.CervenyDrak;
import entity.Dino;
import entity.Ghost;
import entity.Hrac;
import entity.Nepriatel;
import entity.Skeleton;
import entity.Zombie;
import itemy.Dvere;
import itemy.Inventar;
import itemy.Item;
import itemy.ManazerItemov;
import itemy.brnenia.BozskeBrnenie;
import itemy.brnenia.KovoveBrnenie;
import itemy.brnenia.LadoveBrnenie;
import itemy.brnenia.ObycajneBrnenie;
import itemy.brnenia.PekelneBrnenie;
import itemy.brnenia.TemneBrnenie;
import itemy.kluce.Kluc;
import itemy.mece.BozskyMec;
import itemy.mece.ObycajnyMec;
import itemy.mece.OhnivyMec;
import itemy.mece.StromovyMec;
import itemy.mece.TemnyMec;
import manazeri.ManazerKolizie;
import manazeri.ManazerSvetov;
import manazeri.Svety;
import policka.ManagerPolicok;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trieda GamePanel slúži ako JPanel a hlavná trieda celého programu
 * GamePanel taktiež implementuje Runnable, keďže obsahuje hlavný cyklus programu s GameThreadom
 */

public class GamePanel extends JPanel implements Runnable {
    private final int povodnaVelkostPolicka = 16;
    private final int skala = 3;
    private final int velkostPolicka = this.povodnaVelkostPolicka * this.skala;
    private final int maxScreenStlpec = 16;
    private final int maxScreenRiadok = 12;
    private final int screenSirka = this.velkostPolicka * this.maxScreenStlpec;
    private final int screenVyska = this.velkostPolicka * this.maxScreenRiadok;
    private static final int FPS = 60;
    private final ManagerPolicok managerPolicok;
    private final ManazerKolizie manazerKolizie;
    private final ManazerSvetov manazerSvetov;
    private final KeyHandler keyH = new KeyHandler();
    private final Hrac hrac;
    private Thread gameThread;
    private final Item[] poleItemov;
    private final Inventar inventar;
    private final ArrayList<Nepriatel> zoznamNepriatelov;
    private final ArrayList<Dvere> zoznamDveri;
    private int maxWorldCol = 67;
    private int maxWorldRow = 68;
    private int opakovania;

    /**
     * Konštruktor triedy GamePanel
     * V samotnom konštruktori sa nastavuje JPanel, vytvárajú sa inštancie objektov, prídavajú sa Itemy do hry cez metódy ManazeraItemov
     * Okrem toho sa vytvárajú inštancie triedy Nepriatel a následne sa pridávajú do ArrayListu
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenSirka, this.screenVyska));
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyH);
        this.setFocusable(true);
        this.zoznamDveri = new ArrayList<>();
        ManazerItemov manazerItemov = new ManazerItemov(this);

        // SVET 2
        manazerItemov.pridajItemDoHry(new ObycajneBrnenie(2, 48, 52));
        manazerItemov.pridajItemDoHry(new Kluc("Kluc1", 2, 79, 85));
        manazerItemov.pridajItemDoHry(new Kluc("Kluc2", 2, 15, 13));
        manazerItemov.pridajItemDoHry(new ObycajnyMec(2, 50, 52));
        manazerItemov.pridajItemDoHry(new TemnyMec(2, 16, 51));
        manazerItemov.pridajItemDoHry(new TemneBrnenie(2, 18, 51));
        manazerItemov.pridajItemDoHry(new LadoveBrnenie(13, 87));
        this.zoznamDveri.add(new Dvere("Dvere1Zavrete", 2, 30, 42, "Kluc1"));
        this.zoznamDveri.add(new Dvere("Dvere1Zavrete", 2, 11, 84, "Kluc3"));
        // SVET 2

        // SVET 3
        manazerItemov.pridajItemDoHry(new Kluc("Kluc5", 3, 50, 76));
        manazerItemov.pridajItemDoHry(new Kluc("Kluc6", 3, 80, 73));
        manazerItemov.pridajItemDoHry(new BozskyMec(3, 32, 83));
        manazerItemov.pridajItemDoHry(new BozskeBrnenie(3, 59, 49));
        manazerItemov.pridajItemDoHry(new StromovyMec(49, 51));
        this.zoznamDveri.add(new Dvere("Dvere3Zavrete", 3, 29, 54, "Kluc5"));
        this.zoznamDveri.add(new Dvere("Dvere3Zavrete", 3, 39, 14, "Kluc3"));
        // SVET 3

        // SVET 4
        manazerItemov.pridajItemDoHry(new Kluc("Kluc3", 4, 49, 88));
        manazerItemov.pridajItemDoHry(new Kluc("Kluc4", 4, 19, 41));
        manazerItemov.pridajItemDoHry(new OhnivyMec( 18, 84));
        manazerItemov.pridajItemDoHry(new PekelneBrnenie( 57, 14));
        manazerItemov.pridajItemDoHry(new KovoveBrnenie( 4, 80, 69));
        this.zoznamDveri.add(new Dvere("Dvere2Zavrete", 4, 22, 84, "Kluc2"));
        // SVET 4

        this.poleItemov = manazerItemov.getItemy();
        this.inventar = new Inventar(this);
        this.manazerSvetov = new ManazerSvetov(Svety.LOBBY);
        this.managerPolicok = new ManagerPolicok(this, this.manazerSvetov);
        this.manazerKolizie = new ManazerKolizie(this, this.managerPolicok, manazerItemov, this.manazerSvetov, this.poleItemov, List.copyOf(this.zoznamDveri));
        this.hrac = new Hrac(this.keyH, this, this.manazerSvetov, this.manazerKolizie, this.inventar);



        // NASTAVENIE NEPRIATEĽOV PODĽA SVETA
        this.zoznamNepriatelov = new ArrayList<>();

        // SVET 2
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 14, 21));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 18, 21));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 23, 19));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 22, 23));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 25, 25));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 23, 70));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 19, 72));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 21, 75));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 22, 64));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 13, 76));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 49, 36));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 48, 28));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 2, 73, 84));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 2, 14, 35));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 2, 14, 40));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 2, 20, 43));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 2, 19, 49));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 2, 17, 43));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 2, 15, 52));
        // SVET 2

        // SVET 3
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 3, 45, 75));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 3, 50, 80));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 3, 42, 79));
        this.zoznamNepriatelov.add(new Skeleton(this, this.manazerSvetov, this.manazerKolizie, 3, 54, 75));
        this.zoznamNepriatelov.add(new Ghost(this, this.manazerSvetov, this.manazerKolizie, 3, 41, 79));
        this.zoznamNepriatelov.add(new Ghost(this, this.manazerSvetov, this.manazerKolizie, 3, 21, 42));
        this.zoznamNepriatelov.add(new Ghost(this, this.manazerSvetov, this.manazerKolizie, 3, 80, 69));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 3, 26, 37));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 3, 28, 43));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 3, 31, 42));
        this.zoznamNepriatelov.add(new Zombie(this, this.manazerSvetov, this.manazerKolizie, 3, 29, 52));
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 3, 12, 13));
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 3, 18, 18));
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 3, 24, 12));
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 3, 30, 18));
        // SVET 3

        // SVET 4
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 4, 29, 67));
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 4, 28, 33));
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 4, 84, 58));
        this.zoznamNepriatelov.add(new Dino(this, this.manazerSvetov, this.manazerKolizie, 4, 83, 66));
        this.zoznamNepriatelov.add(new CervenyDrak(this, this.manazerSvetov, this.manazerKolizie, 49, 72));
        this.zoznamNepriatelov.add(new CervenyDrak(this, this.manazerSvetov, this.manazerKolizie, 28, 84));
        this.zoznamNepriatelov.add(new CervenyDrak(this, this.manazerSvetov, this.manazerKolizie, 49, 78));
        this.zoznamNepriatelov.add(new CervenyDrak(this, this.manazerSvetov, this.manazerKolizie, 18, 55));
        this.zoznamNepriatelov.add(new Ghost(this, this.manazerSvetov, this.manazerKolizie, 4, 53, 15));
        this.zoznamNepriatelov.add(new Ghost(this, this.manazerSvetov, this.manazerKolizie, 4, 61, 15));
        this.zoznamNepriatelov.add(new Ghost(this, this.manazerSvetov, this.manazerKolizie, 4, 35, 55));
        // SVET4
    }

    /**
     * Metóda startGameThread() slúži na vytvorenie nového Threadu tohto programu
     */
    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    /**
     * Metóda run overriduje pôvodnú metódu run, stará sa o prekreslovanie všetkých inštancií, volá metódy ManazeraKolizie (s použitím kópií listov)
     * Pokiaľ hráč prehrá / vyhrá hru, List nepriateľov sa vymaže a hra sa skončí (neprebieha už žiadne vykreslovanie)
     * Pri metóde som si z časti pomáhal videoTutoriálom! (<a href="https://www.youtube.com/watch?v=VpH33Uw-_0E&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=2&ab_channel=RyiSnow">...</a>)
     */
    @Override
    public void run() {
        double drawInterval = (double)1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (this.gameThread != null) {
            int cisloSveta = this.manazerSvetov.getCisloSveta();
            if (this.hrac.getHp() > 0) {
                if (this.hrac.maVsetkyKluce()) {
                    this.hrac.update();
                    List<Nepriatel> nepriateliaToUpdate = new ArrayList<>();
                    for (Nepriatel nepriatel : this.zoznamNepriatelov) {
                        if (cisloSveta == nepriatel.getCisloSveta() && nepriatel.getHp() > 0 && nepriatel.jeAktivny()) {
                            nepriateliaToUpdate.add(nepriatel);
                        }
                    }
                    List<Nepriatel> unmodifiableNepriatelia = Collections.unmodifiableList(nepriateliaToUpdate);
                    for (Nepriatel nepriatel : unmodifiableNepriatelia) {
                        if (this.manazerSvetov.getCisloSveta() == nepriatel.getCisloSveta() && nepriatel.getHp() > 0) {
                            if (this.keyH.isSpacePressed()) {
                                this.manazerKolizie.checkZautocenie(List.copyOf(nepriateliaToUpdate), this.hrac, this.inventar);
                            }
                            nepriatel.update();
                            this.manazerKolizie.checkNepriatelKolizia(nepriatel, this.hrac);
                            this.manazerKolizie.checkSelfKolizia(List.copyOf(nepriateliaToUpdate), this.hrac);
                            if (nepriatel instanceof Ghost && this.manazerSvetov.getCisloSveta() == nepriatel.getCisloSveta()) {
                                ((Ghost)nepriatel).hracVBlizkosti(this.hrac);
                            }
                        }
                    }
                    this.repaint();
                    try {
                        double remainingTime = nextDrawTime - System.nanoTime();
                        remainingTime = remainingTime / 1000000;

                        if (remainingTime < 0) {
                            remainingTime = 0;
                        }
                        Thread.sleep((long)remainingTime);
                        nextDrawTime += drawInterval;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    this.zoznamNepriatelov.clear();
                    this.koniecHry(true);
                    this.opakovania = 1;
                }
            } else {
                this.zoznamNepriatelov.clear();
                this.koniecHry(false);
                this.opakovania = 1;
            }
        }
    }

    /**
     * Metóda paintComponent slúži na vykreslovanie všetkých objektov ktoré chceme vykreslovať a majú svoju vlastnú metódu draw() s parametrom Graphics2D
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        if (this.hrac.kontrolujZivoty() && this.hrac.maVsetkyKluce()) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            this.managerPolicok.draw(g2, this.hrac);
            for (Item value : this.poleItemov) {
                if (value != null) {
                    if (value.getCisloSveta() == this.manazerSvetov.getCisloSveta()) {
                        value.draw(g2, this, this.hrac);
                    }
                }
            }
            for (Dvere dverevhre : this.zoznamDveri) {
                if (this.manazerSvetov.getCisloSveta() == dverevhre.getCisloSveta()) {
                    dverevhre.draw(g2, this, this.hrac);
                }
            }
            this.hrac.draw(g2);
            for (Nepriatel nepriatel : this.zoznamNepriatelov) {
                if (nepriatel.getCisloSveta() == this.manazerSvetov.getCisloSveta() && nepriatel.getHp() > 0 && nepriatel.jeAktivny()) {
                    nepriatel.draw(g2, this.hrac);
                }
            }
            this.inventar.draw(g2, this.keyH);
            this.inventar.update(this.keyH);
            g2.dispose();
        }
    }

    /**
     * Metóda updatePanel() slúži na prekreslenie a revalidácie samotného JPanelu, pričom na chvíľú uspíme samotný Thread
     */
    public void updatePanel() {
        this.repaint();
        this.revalidate();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metóda koniecHry() sa zavolá v prípade že hráč vyhrá / prehrá hru, slúži na vykreslenie textu ktorý informuje používateľa o prehre alebo výhre
     * Okrem toho metóda volá metódu zapisDoSuboru(), ktorá do súboru zapíše informácie o hre (Počet zabitých nepriateľov, skóre)
     * @param vyhra - Parameter slúži na zadanie toho, či hráč hru prehral alebo vyhral (false = prehral, true = vyhral)
     */
    public void koniecHry(boolean vyhra) {
        if (this.opakovania != 1) {
            this.setLayout(null);
            this.setBackground(Color.LIGHT_GRAY);
            Color farba;
            String finalnyText;
            if (vyhra) {
                finalnyText = "VYHRAL SI!";
                farba = Color.GREEN;
            } else {
                finalnyText = "PREHRAL SI!";
                farba = Color.RED;
            }
            JLabel textHry = new JLabel(finalnyText);
            textHry.setFont(new Font("Boomer Tantrum", Font.BOLD, 80));
            textHry.setForeground(farba);
            textHry.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel textEsc = new JLabel("Klavesou ESC ukoncis hru");
            textEsc.setFont(new Font("Boomer Tantrum", Font.BOLD, 40));
            textEsc.setForeground(Color.WHITE);
            textEsc.setHorizontalAlignment(SwingConstants.CENTER);
            int width = 550;
            int height = 100;
            textHry.setBounds((this.getWidth() - width) / 2, (this.getHeight() - height) / 2 - 50, width, height);
            textEsc.setBounds((this.getWidth() - width - 100) / 2, (this.getHeight() - height) / 2 + 100, width + 100, height);
            this.add(textHry);
            this.add(textEsc);
            this.revalidate();
            this.repaint();
            this.zapisDoSuboru();
        }
    }

    /**
     * Metóda zapisDoSuboru() slúži na vypísanie do súboru informácie o hre po jej skončení
     * Zapisuje sa skóre hráča a koľko nepriateľov sa mu podarilo počas hrania zabiť + "textové logo" hry
     */
    public void zapisDoSuboru() {
        String cestaKSuboru = "res/vysledok.txt";
        try {
            PrintWriter pw = new PrintWriter(cestaKSuboru);
            pw.println("""
                    $$\\      $$\\  $$$$$$\\  $$\\   $$\\  $$$$$$\\ $$$$$$$$\\ $$$$$$$$\\ $$$$$$$\\         $$$$$$\\  $$\\        $$$$$$\\ $$\\     $$\\ $$$$$$$$\\ $$$$$$$\\ \s
                    $$$\\    $$$ |$$  __$$\\ $$$\\  $$ |$$  __$$\\\\__$$  __|$$  _____|$$  __$$\\       $$  __$$\\ $$ |      $$  __$$\\\\$$\\   $$  |$$  _____|$$  __$$\\\s
                    $$$$\\  $$$$ |$$ /  $$ |$$$$\\ $$ |$$ /  \\__|  $$ |   $$ |      $$ |  $$ |      $$ /  \\__|$$ |      $$ /  $$ |\\$$\\ $$  / $$ |      $$ |  $$ |
                    $$\\$$\\$$ $$ |$$ |  $$ |$$ $$\\$$ |\\$$$$$$\\    $$ |   $$$$$\\    $$$$$$$  |      \\$$$$$$\\  $$ |      $$$$$$$$ | \\$$$$  /  $$$$$\\    $$$$$$$  |
                    $$ \\$$$  $$ |$$ |  $$ |$$ \\$$$$ | \\____$$\\   $$ |   $$  __|   $$  __$$<        \\____$$\\ $$ |      $$  __$$ |  \\$$  /   $$  __|   $$  __$$<\s
                    $$ |\\$  /$$ |$$ |  $$ |$$ |\\$$$ |$$\\   $$ |  $$ |   $$ |      $$ |  $$ |      $$\\   $$ |$$ |      $$ |  $$ |   $$ |    $$ |      $$ |  $$ |
                    $$ | \\_/ $$ | $$$$$$  |$$ | \\$$ |\\$$$$$$  |  $$ |   $$$$$$$$\\ $$ |  $$ |      \\$$$$$$  |$$$$$$$$\\ $$ |  $$ |   $$ |    $$$$$$$$\\ $$ |  $$ |
                    \\__|     \\__| \\______/ \\__|  \\__| \\______/   \\__|   \\________|\\__|  \\__|       \\______/ \\________|\\__|  \\__|   \\__|    \\________|\\__|  \\__|
                                                                                                                                                              \s
                                                                                                                                                              \s
                                                                                                                                                              \s""");
            pw.println("--- HRÁČ ---");
            pw.println("SKÓRE: " + this.hrac.getSkore());
            pw.println("ZABITÝCH NEPRIATEĽOV: " + this.hrac.getPocetZabitychNepriatelov());
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metóda getVelkostPolicka() slúži na vrátenie hodnoty veľkosti políčka
     * @return - Samotnou návratovou hodnotou je veľkosť políčka
     */
    public int getVelkostPolicka() {
        return this.velkostPolicka;
    }

    /**
     * Metóda getSirkaObrazovky() slúži na vrátenie hodnoty šírky obrazovky
     * @return - Samotnou návratovou hodnotou je šírka obrazovky
     */
    public int getSirkaObrazovky() {
        return this.screenSirka;
    }

    /**
     * Metóda getVyskaobrazovky() slúži na vrátenie hodnoty výšky obrazovky
     * @return - Samotnou návratovou hodnotou je výška obrazovky
     */
    public int getVyskaObrazovky() {
        return this.screenVyska;
    }

    /**
     * Metóda getMaxWorldCol() slúži na vrátenie hodnoty maximálnej výšky hry v riadkoch (používame pri tvorení mapy s políčkami)
     * @return - Samotnou návratovou hodnotou je výška hry v riadkoch
     */
    public int getMaxWorldCol() {
        return this.maxWorldCol;
    }

    /**
     * Metóda setMaxWorldCol() slúži na nastavenie novej hodnoty maximálnej výšky hry v riadkoch (používame pri tvorení mapy s políčkami)
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej int hodnoty tejto výšky
     */
    public void setMaxWorldCol(int novaHodnota) {
        this.maxWorldCol = novaHodnota;
    }

    /**
     * Metóda setMaxWorldRow() slúži na nastavenie novej hodnoty maximálnej šírky hry v riadkoch (používame pri tvorení mapy s políčkami)
     * @param novaHodnota - Parameter novaHodnota slúži na nastavenie novej int hodnoty tejto šírky
     */
    public void setMaxWorldRow(int novaHodnota) {
        this.maxWorldRow = novaHodnota;
    }

    /**
     * Metóda getMaxWorldRow() slúži na vrátenie hodnoty maximálnej šírky hry v riadkoch (používame pri tvorení mapy s políčkami)
     * @return - Samotnou návratovou hodnotou je šírka hry v riadkoch
     */
    public int getMaxWorldRow() {
        return this.maxWorldRow;
    }



}
