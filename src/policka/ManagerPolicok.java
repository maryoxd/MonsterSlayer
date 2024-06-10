package policka;
import entity.Hrac;
import main.GamePanel;
import manazeri.ManazerSvetov;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Trieda ManagerPolicok slúži na samotné manažovanie políčok, keďže vytvára viacero polí z Políčok a stará sa o samotné vytvorenie hracej mapy
 * Pri tvorbe triedy som si pomáhal video tutoriálom! (<a href="https://www.youtube.com/watch?v=ugzxCcpoSdE&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=4&ab_channel=RyiSnow">...</a>)
 */
public class ManagerPolicok {
    private final GamePanel gamePanel;
    private final Policko[] policko;
    private final int[][] cisloPolicka;
    private final int[][] cisloPolicka2;
    private int maxWorldStlpec;
    private int maxWorldRiadok;
    private final int velkostPolicka;
    private final ManazerSvetov manazerSvetov;

    /**
     * Konštruktor triedy ManagerPolicok
     * @param gamePanel - Parameter GamePanel slúži na zistenie veľkosti políčka, veľkosti mapy s ktorou trieda neskôr pracuje
     * @param manazerSvetov - Parameter ManazerSvetov, keďže chceme políčka nastavovať v závislosti od momentálneho hracieho Sveta
     */
    public ManagerPolicok(GamePanel gamePanel, ManazerSvetov manazerSvetov) {
        this.gamePanel = gamePanel;
        this.manazerSvetov = manazerSvetov;
        this.velkostPolicka = this.gamePanel.getVelkostPolicka();
        this.maxWorldStlpec = this.gamePanel.getMaxWorldCol();
        this.maxWorldRiadok = this.gamePanel.getMaxWorldRow();
        this.policko = new Policko[50];
        this.cisloPolicka = new int[25][22];
        this.cisloPolicka2 = new int[99][99];
        this.nastavPolicka();
        this.nastavObrazkyPolickam();
        this.nacitajMapu();

    }

    /**
     * Metóda nastavPolicka() slúži na nastavenie hodnôt šírky a výšky políčok v závislosti od momentálneho hracieho Sveta
     */
    public void nastavPolicka() {
        int cisloSveta = this.manazerSvetov.getCisloSveta();
        if (cisloSveta == 1) {
            this.gamePanel.setMaxWorldCol(25);
            this.gamePanel.setMaxWorldRow(22);
            this.maxWorldStlpec = 25;
            this.maxWorldRiadok = 22;
        } else {
            this.gamePanel.setMaxWorldCol(99);
            this.gamePanel.setMaxWorldRow(99);
            this.maxWorldStlpec = 99;
            this.maxWorldRiadok = 99;
        }
    }

    /**
     * Metóda nastavObrazkyPolickam() slúži na nastavenie obrázkov políčkam v závislosti od momentálneho hracieho Sveta
     * Metóda taktiež vytvára pole v ktorom sa políčka nachádzajú
     */
    public void nastavObrazkyPolickam() {
        int cisloSveta = this.manazerSvetov.getCisloSveta();
        for (int i = 0; i < this.policko.length; i++) {
            this.policko[i] = new Policko();
        }
        try {
            if (cisloSveta == 1) {
                this.policko[0].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\000.png")));
                this.policko[0].setKolizia(true);
                this.policko[1].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\001.png")));
                this.policko[1].setKolizia(true);
                this.policko[2].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\002.png")));
                this.policko[2].setKolizia(true);
                this.policko[3].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\hellportal.png")));
                this.policko[3].setTeleportacia(true);
                this.policko[3].setTeleportacneCislo(4);
                this.policko[4].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\blessportal.png")));
                this.policko[4].setTeleportacia(true);
                this.policko[4].setTeleportacneCislo(3);
                this.policko[5].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\homeportal.png")));
                this.policko[5].setTeleportacia(true);
                this.policko[5].setTeleportacneCislo(2);
                this.policko[6].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\008.png")));
                this.policko[7].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\009.png")));
                this.policko[8].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\004.png")));
                this.policko[9].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\005.png")));
                this.policko[10].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\006.png")));
                this.policko[11].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet1\\007.png")));
            }
            if (cisloSveta == 2) {
                this.policko[1].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\001.png")));
                this.policko[2].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\002.png")));
                this.policko[3].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\003.png")));
                this.policko[4].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\004.png")));
                this.policko[5].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\005.png")));
                this.policko[6].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\006.png")));
                this.policko[7].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\007.png")));
                this.policko[8].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\008.png")));
                this.policko[9].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\009.png")));
                this.policko[10].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\010.png")));
                this.policko[11].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\011.png")));
                this.policko[12].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\012.png")));
                this.policko[13].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\013.png")));
                this.policko[14].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\014.png")));
                this.policko[15].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\015.png")));
                this.policko[16].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\016.png")));
                this.policko[16].setKolizia(true);
                this.policko[17].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\017.png")));
                this.policko[18].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\018.png")));
                this.policko[18].setKolizia(true);
                this.policko[19].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\019.png")));
                this.policko[19].setKolizia(true);
                this.policko[20].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\020.png")));
                this.policko[20].setKolizia(true);
                this.policko[21].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\021.png")));
                this.policko[21].setKolizia(true);
                this.policko[22].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\022.png")));
                this.policko[22].setKolizia(true);
                this.policko[23].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\023.png")));
                this.policko[23].setKolizia(true);
                this.policko[24].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\024.png")));
                this.policko[24].setKolizia(true);
                this.policko[25].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\025.png")));
                this.policko[25].setKolizia(true);
                this.policko[26].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\026.png")));
                this.policko[26].setKolizia(true);
                this.policko[27].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\027.png")));
                this.policko[27].setKolizia(true);
                this.policko[28].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\028.png")));
                this.policko[28].setKolizia(true);
                this.policko[29].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\029.png")));
                this.policko[29].setKolizia(true);
                this.policko[30].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\030.png")));
                this.policko[30].setKolizia(true);
                this.policko[31].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\031.png")));
                this.policko[31].setKolizia(true);
                this.policko[32].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\032.png")));
                this.policko[32].setKolizia(true);
                this.policko[33].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\033.png")));
                this.policko[33].setKolizia(true);
                this.policko[34].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\034.png")));
                this.policko[35].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\035.png")));
                this.policko[35].setKolizia(true);
                this.policko[38].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet2\\portalZakladny.png")));
                this.policko[38].setTeleportacia(true);
                this.policko[38].setTeleportacneCislo(1);
            }
            if (cisloSveta == 3) {
                this.policko[0].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\000.png")));
                this.policko[1].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\001.png")));
                this.policko[1].setKolizia(true);
                this.policko[2].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\002.png")));
                this.policko[2].setKolizia(true);
                this.policko[3].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\003.png")));
                this.policko[4].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\004.png")));
                this.policko[5].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\005.png")));
                this.policko[6].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\006.png")));
                this.policko[7].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\007.png")));
                this.policko[8].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\008.png")));
                this.policko[9].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\009.png")));
                this.policko[10].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\010.png")));
                this.policko[11].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\011.png")));
                this.policko[12].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\012.png")));
                this.policko[13].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\013.png")));
                this.policko[14].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\014.png")));
                this.policko[15].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\015.png")));
                this.policko[16].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\016.png")));
                this.policko[17].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\017.png")));
                this.policko[18].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\018.png")));
                this.policko[19].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\019.png")));
                this.policko[20].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\020.png")));
                this.policko[21].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\021.png")));
                this.policko[21].setKolizia(true);
                this.policko[22].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\022.png")));
                this.policko[23].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\023.png")));
                this.policko[23].setKolizia(true);
                this.policko[24].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\024.png")));
                this.policko[24].setKolizia(true);
                this.policko[25].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\025.png")));
                this.policko[25].setKolizia(true);
                this.policko[26].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\026.png")));
                this.policko[26].setKolizia(true);
                this.policko[27].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\027.png")));
                this.policko[27].setKolizia(true);
                this.policko[28].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\028.png")));
                this.policko[28].setKolizia(true);
                this.policko[29].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\029.png")));
                this.policko[29].setKolizia(true);
                this.policko[30].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\030.png")));
                this.policko[30].setKolizia(true);
                this.policko[31].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\031.png")));
                this.policko[31].setKolizia(true);
                this.policko[32].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\032.png")));
                this.policko[32].setKolizia(true);
                this.policko[33].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\033.png")));
                this.policko[33].setKolizia(true);
                this.policko[34].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\034.png")));
                this.policko[34].setKolizia(true);
                this.policko[35].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\035.png")));
                this.policko[35].setKolizia(true);
                this.policko[36].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\036.png")));
                this.policko[36].setKolizia(true);
                this.policko[37].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\037.png")));
                this.policko[37].setKolizia(true);
                this.policko[38].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\038.png")));
                this.policko[39].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\039.png")));
                this.policko[40].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet3\\portalZakladny.png")));
                this.policko[40].setTeleportacia(true);
                this.policko[40].setTeleportacneCislo(1);
            }
            if (cisloSveta == 4) {
                this.policko[0].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\000.png")));
                this.policko[1].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\001.png")));
                this.policko[1].setKolizia(true);
                this.policko[2].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\002.png")));
                this.policko[2].setKolizia(true);
                this.policko[3].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\003.png")));
                this.policko[4].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\004.png")));
                this.policko[4].setPoskodzuje(true);
                this.policko[4].setPoskodenie(2.0);
                this.policko[5].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\005.png")));
                this.policko[6].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\006.png")));
                this.policko[7].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\007.png")));
                this.policko[8].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\008.png")));
                this.policko[9].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\009.png")));
                this.policko[10].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\010.png")));
                this.policko[11].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\011.png")));
                this.policko[12].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\012.png")));
                this.policko[12].setPoskodzuje(true);
                this.policko[12].setPoskodenie(2.0);
                this.policko[13].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\013.png")));
                this.policko[13].setPoskodzuje(true);
                this.policko[13].setPoskodenie(2.0);
                this.policko[14].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\014.png")));
                this.policko[14].setKolizia(true);
                this.policko[15].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\015.png")));
                this.policko[16].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\016.png")));
                this.policko[16].setKolizia(true);
                this.policko[17].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\017.png")));
                this.policko[17].setPoskodzuje(true);
                this.policko[17].setPoskodenie(2.0);
                this.policko[18].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\018.png")));
                this.policko[19].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\019.png")));
                this.policko[20].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\020.png")));
                this.policko[21].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\021.png")));
                this.policko[22].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\022.png")));
                this.policko[23].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\023.png")));
                this.policko[24].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\024.png")));
                this.policko[25].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\025.png")));
                this.policko[26].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\026.png")));
                this.policko[27].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\027.png")));
                this.policko[28].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\028.png")));
                this.policko[29].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\029.png")));
                this.policko[30].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\030.png")));
                this.policko[31].setObrazok(ImageIO.read(new FileInputStream("res\\tiles\\Svet4\\portalZakladny.png")));
                this.policko[31].setTeleportacia(true);
                this.policko[31].setTeleportacneCislo(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metóda nacitajMapu() slúži na načítanie čísel z mapy podľa momentáleho čísla Sveta, kde potom tieto hodnoty ukladá do pola
     */
    public void nacitajMapu() {
        int cisloSveta = this.manazerSvetov.getCisloSveta();
        try {
            FileInputStream fis = null;
            if (cisloSveta == 1) {
                fis = new FileInputStream("res\\mapy\\svet1Mapa.txt");
            }
            if (cisloSveta == 2) {
                fis = new FileInputStream("res\\mapy\\svet2Mapa.txt");
            }
            if (cisloSveta == 3) {
                fis = new FileInputStream("res\\mapy\\svet3Mapa.txt");
            }
            if (cisloSveta == 4) {
                fis = new FileInputStream("res\\mapy\\svet4Mapa.txt");
            }
            BufferedReader br = null;
            if (fis != null) {
                br = new BufferedReader(new InputStreamReader(fis));
            }
            int stlpec = 0;
            int riadok = 0;
            while (stlpec < this.maxWorldStlpec && riadok < this.maxWorldRiadok) {
                String line = null;
                if (br != null) {
                    line = br.readLine();
                }
                while (stlpec < this.maxWorldStlpec) {
                    String[] cisla = new String[0];
                    if (line != null) {
                        cisla = line.split(" ");
                    }
                    int num = Integer.parseInt(cisla[stlpec]);
                    if (cisloSveta == 1) {
                        this.cisloPolicka[stlpec][riadok] = num;
                    } else {
                        this.cisloPolicka2[stlpec][riadok] = num;
                    }
                    stlpec++;
                }
                if (stlpec == this.maxWorldStlpec) {
                    stlpec = 0;
                    riadok++;
                }
            }
            if (br != null) {
                br.close();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metóda draw slúži na samotné vykreslenie políčok z načítaných polí
     * @param g2 - Parameter knižnice Graphics2D, keďže chceme políčka vykreslovať
     * @param hrac - Parameter Hráča, keďže chceme políčka kresliť len ak ich hráč vidí
     */
    public void draw(Graphics2D g2, Hrac hrac) {
        int hracoveWX = hrac.getWorldX();
        int hracoveWY = hrac.getWorldY();
        int hracoveSX = hrac.getScreenX();
        int hracoveSY = hrac.getScreenY();
        int cisloSveta = this.manazerSvetov.getCisloSveta();
        int worldStlpec = 0;
        int worldRiadok = 0;
        this.nastavPolicka();
        while (worldStlpec < this.maxWorldStlpec && worldRiadok < this.maxWorldRiadok) {
            int finalnePolicko;
            if (cisloSveta == 1) {
                finalnePolicko = this.cisloPolicka[worldStlpec][worldRiadok];
            } else {
                finalnePolicko = this.cisloPolicka2[worldStlpec][worldRiadok];
            }
            int worldX = worldStlpec * this.velkostPolicka;
            int worldY = worldRiadok * this.velkostPolicka;
            int screenX = worldX - hracoveWX + hracoveSX;
            int screenY = worldY - hracoveWY + hracoveSY;
            if (worldX + this.velkostPolicka > hracoveWX - hracoveSX  && worldX - this.velkostPolicka < hracoveWX + hracoveSX && worldY + this.velkostPolicka > hracoveWY - hracoveSY && worldY - this.velkostPolicka < hracoveWY + hracoveSY) {
                g2.drawImage(this.policko[finalnePolicko].getObrazok(), screenX, screenY, this.velkostPolicka, this.velkostPolicka, null);
            }
            worldStlpec++;
            if (worldStlpec == this.maxWorldStlpec) {
                worldStlpec = 0;
                worldRiadok++;
            }
        }
    }

    /**
     * Metóda getCisloPolicka() slúži na vrátenie kópie pola s políčkami, tým pádom do tohto poľa nemôže iná trieda zasahovať
     * @return - Samotnou návratovou hodnotou je pole políčok
     */
    public int[][] getCisloPolicka() {
        int[][] novePole = new int[this.cisloPolicka.length][];
        for (int i = 0; i < this.cisloPolicka.length; i++) {
            novePole[i] = new int[this.cisloPolicka[i].length];
            System.arraycopy(this.cisloPolicka[i], 0, novePole[i], 0, this.cisloPolicka[i].length);
        }
        return novePole;
    }

    /**
     * Metóda getCisloPolicka2() slúži na vrátenie kópie pola s políčkami iného sveta, tým pádom do tohto poľa nemôže iná trieda zasahovať
     * @return - Samotnou návratovou hodnotou je pole políčok
     */
    public int[][] getCisloPolicka2() {
        int[][] novePole = new int[this.cisloPolicka2.length][];
        for (int i = 0; i < this.cisloPolicka2.length; i++) {
            novePole[i] = new int[this.cisloPolicka2[i].length];
            System.arraycopy(this.cisloPolicka2[i], 0, novePole[i], 0, this.cisloPolicka2[i].length);
        }
        return novePole;
    }

    /**
     * Metóda getPolicko() slúži na vrátenie kópie pola s políčkami z prvého sveta, tým pádom do tohto poľa nemôže iná trieda zasahovať
     * @return - Samotnou návratovou hodnotou je pole políčok
     */
    public Policko[] getPolicko() {
        return Arrays.copyOf(this.policko, this.policko.length);
    }
}


