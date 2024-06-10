package manazeri;
import entity.Entity;
import entity.Hrac;
import entity.Nepriatel;
import itemy.Dvere;
import itemy.Inventar;
import itemy.Item;
import itemy.ManazerItemov;
import main.GamePanel;
import policka.ManagerPolicok;
import java.awt.Rectangle;
import java.util.List;

/**
 * Trieda ManazerKolizie je jedna z najväčších tried a slúži na manažovanie všetkých typov kolízie medzi Hráčom a Nepriatelmi, medzi Nepriatelmi, a medzi Hráčovou zbraňou a nepriateľmi
 * Trieda taktiež kontroluje kolíziu medzi políčkami či skutočnosť, či sa neohňuvzdorná Entita dotýka poškodzujúceho políčka
 */
public class ManazerKolizie {
    private final ManagerPolicok managerPolicok;
    private final ManazerItemov manazerItemov;
    private final ManazerSvetov manazerSvetov;
    private final int velkostPolicka;
    private final Item[] itemy;
    private final List<Dvere> dvere;

    /**
     *
     * @param gamePanel - Parameter GamePanel slúži na zistenie veľkosti políčka, čo je zadefinované v triede GamePanel
     * @param managerPolicok - Parameter ManagerPolicok, keďže pracujeme s polami políčok z tejto triedy
     * @param manazerItemov - Parameter ManazerItemov, keďže kontrolujeme aj kolíziu hráča s Itemami na zemi
     * @param manazerSvetov - Parameter ManazerSvetov, keďže každý svet má iné políčka s inými nastaveniami
     * @param itemy - Parameter polí itemov, ako parameter dostávame len kópiu pôvodného pola. Jedná sa o Itemy, ktoré sa objavujú v hre
     * @param dvere - Parameter ArrayListu dverí, ako parameter dostávame len kópiu pôvodného listu dverí, ktoré sa objavujú v hre
     */
    public ManazerKolizie(GamePanel gamePanel, ManagerPolicok managerPolicok, ManazerItemov manazerItemov, ManazerSvetov manazerSvetov, Item[] itemy, List<Dvere> dvere) {
        this.manazerItemov = manazerItemov;
        this.velkostPolicka = gamePanel.getVelkostPolicka();
        this.managerPolicok = managerPolicok;
        this.manazerSvetov = manazerSvetov;
        this.itemy = itemy;
        this.dvere = dvere;
    }

    /**
     * Metóda skontrolujPolicko() slúži na výpočet entity, na ktorom políčku sa nachádza a následne zisťuje, či dané políčko predstavuje kolíziu
     * Pokiaľ políčko predstavuje kolíziu, entite sa zakáže pohyb, a teda daným políčkom neprejde
     * Pri tvorbe metódy som si z väčšej časti pomáhal videotutoriálom! (<a href="https://www.youtube.com/watch?v=oPzPpUcDiYY&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=6&ab_channel=RyiSnow">...</a>)
     * @param entity - Parameter Entity, v prípade Hráča polymorfne kontrolujeme viacero vecí, inak metóda funguje na hocijakú inštanciu triedy Entita
     */
    public void skontrolujPolicko(Entity entity) {
        int entityLWX;
        int entityRWX;
        int entityTWY;
        int entityBTY;
        entityLWX = entity.getWorldX() + (int)entity.getSolidArea().getX();
        entityRWX = entity.getWorldX() + (int)entity.getSolidArea().getX() + (int)entity.getSolidArea().getWidth();
        entityTWY = entity.getWorldY() + (int)entity.getSolidArea().getY();
        entityBTY = entity.getWorldY() + (int)entity.getSolidArea().getY() + (int)entity.getSolidArea().getHeight();
        int entityLavyStlpec = entityLWX / this.velkostPolicka;
        int entityPravyRiadok = entityRWX / this.velkostPolicka;
        int entityHornyRiadok = entityTWY / this.velkostPolicka;
        int entityDolnyRiadok = entityBTY / this.velkostPolicka;
        int polickoN1 = 0;
        int polickoN2 = 0;
        int cisloSveta = this.manazerSvetov.getCisloSveta();
        int[][] policka = this.managerPolicok.getCisloPolicka();
        int[][] policka2 = this.managerPolicok.getCisloPolicka2();
        switch (entity.getSmer()) {
            case "hore" -> {
                entityHornyRiadok = (entityTWY - entity.getSpeed()) / this.velkostPolicka;
                if (cisloSveta == 1) {
                    polickoN1 = policka[entityLavyStlpec][entityHornyRiadok];
                    polickoN2 = policka[entityPravyRiadok][entityHornyRiadok];
                } else {
                    polickoN1 = policka2[entityLavyStlpec][entityHornyRiadok];
                    polickoN2 = policka2[entityPravyRiadok][entityHornyRiadok];
                }
            }
            case "dole" -> {
                entityDolnyRiadok = (entityBTY + entity.getSpeed()) / this.velkostPolicka;
                if (cisloSveta == 1) {
                    polickoN1 = policka[entityLavyStlpec][entityDolnyRiadok];
                    polickoN2 = policka[entityPravyRiadok][entityDolnyRiadok];
                } else {
                    polickoN1 = policka2[entityLavyStlpec][entityDolnyRiadok];
                    polickoN2 = policka2[entityPravyRiadok][entityDolnyRiadok];
                }
            }
            case "vlavo" -> {
                entityLavyStlpec = (entityLWX - entity.getSpeed()) / this.velkostPolicka;
                if (cisloSveta == 1) {
                    polickoN1 = policka[entityLavyStlpec][entityHornyRiadok];
                    polickoN2 = policka[entityLavyStlpec][entityDolnyRiadok];
                } else {
                    polickoN1 = policka2[entityLavyStlpec][entityHornyRiadok];
                    polickoN2 = policka2[entityLavyStlpec][entityDolnyRiadok];
                }
            }
            case "vpravo" -> {
                entityPravyRiadok = (entityRWX + entity.getSpeed()) / this.velkostPolicka;
                if (cisloSveta == 1) {
                    polickoN1 = policka[entityPravyRiadok][entityHornyRiadok];
                    polickoN2 = policka[entityPravyRiadok][entityDolnyRiadok];
                } else {
                    polickoN1 = policka2[entityPravyRiadok][entityHornyRiadok];
                    polickoN2 = policka2[entityPravyRiadok][entityDolnyRiadok];
                }
            }
        }
        if (this.managerPolicok.getPolicko()[polickoN1].getKolizia() || this.managerPolicok.getPolicko()[polickoN2].getKolizia()) {
            entity.setKolizia(true);
        }
        if (this.managerPolicok.getPolicko()[polickoN1].getPoskodzuje()) {
            if (entity.getOhnuvzdorny() && System.currentTimeMillis() - entity.getLastDamageTime() > 500) {
                entity.uberHp(this.managerPolicok.getPolicko()[polickoN1].getPoskodenie());
                entity.setLastDamageTime(System.currentTimeMillis());
            }
        } else if (this.managerPolicok.getPolicko()[polickoN2].getPoskodzuje()) {
            if (entity.getOhnuvzdorny() && System.currentTimeMillis() - entity.getLastDamageTime() > 500) {
                entity.uberHp(this.managerPolicok.getPolicko()[polickoN1].getPoskodenie());
                entity.setLastDamageTime(System.currentTimeMillis());
            }
        }
        if (entity instanceof Hrac) {
            if (this.managerPolicok.getPolicko()[polickoN1].getTeleportacia() || this.managerPolicok.getPolicko()[polickoN2].getTeleportacia()) {
                if (this.managerPolicok.getPolicko()[polickoN1].getTeleportacneCislo() == 1 || this.managerPolicok.getPolicko()[polickoN2].getTeleportacneCislo() == 1) {
                    this.manazerSvetov.setSvet(Svety.LOBBY);
                    this.managerPolicok.nastavPolicka();
                    this.managerPolicok.nacitajMapu();
                    this.managerPolicok.nastavObrazkyPolickam();
                    entity.nastavZaklad();
                    this.manazerItemov.nastavObjekt(1);
                }
                if (this.managerPolicok.getPolicko()[polickoN1].getTeleportacneCislo() == 2 || this.managerPolicok.getPolicko()[polickoN2].getTeleportacneCislo() == 2) {
                    this.manazerSvetov.setSvet(Svety.ZAKLADNY);
                    this.managerPolicok.nastavPolicka();
                    this.managerPolicok.nacitajMapu();
                    this.managerPolicok.nastavObrazkyPolickam();
                    entity.nastavZaklad();
                    this.manazerItemov.nastavObjekt(2);
                }
                if (this.managerPolicok.getPolicko()[polickoN1].getTeleportacneCislo() == 3 || this.managerPolicok.getPolicko()[polickoN2].getTeleportacneCislo() == 3) {
                    this.manazerSvetov.setSvet(Svety.RAJ);
                    this.managerPolicok.nastavPolicka();
                    this.managerPolicok.nacitajMapu();
                    this.managerPolicok.nastavObrazkyPolickam();
                    entity.nastavZaklad();
                    this.manazerItemov.nastavObjekt(3);
                }
                if (this.managerPolicok.getPolicko()[polickoN1].getTeleportacneCislo() == 4 || this.managerPolicok.getPolicko()[polickoN2].getTeleportacneCislo() == 4) {
                    this.manazerSvetov.setSvet(Svety.PEKLO);
                    this.managerPolicok.nastavPolicka();
                    this.managerPolicok.nacitajMapu();
                    this.managerPolicok.nastavObrazkyPolickam();
                    entity.nastavZaklad();
                    this.manazerItemov.nastavObjekt(4);
                }
            }
        }

    }

    /**
     * Metóda  checkObject() slúži na zistenie skutočnosti, či sa hráč dotýka Itemu, pokiaľ áno tak sa mu pomocou metódy z triedy Inventar pridá do inventára
     * @param hrac - Parameter Hráča, keďže potrebujeme Hráčovu kolíznu areu na zistenie kolízie
     * @param inventar - Parameter inventára, keďže chceme do inventára v prípade kolízie tieto Itemy pridávať
     */
    public void checkObject(Hrac hrac, Inventar inventar) {
        Rectangle hracov = new Rectangle(hrac.getWorldX(), hrac.getWorldY(), (int)hrac.getDefaultSolidAreaHrac().getWidth(), (int)hrac.getDefaultSolidAreaHrac().getHeight());
        int cisloSveta = this.manazerSvetov.getCisloSveta();
        for (Item item : this.itemy) {
            if (item != null && cisloSveta != 1 && item.getJeAktivny()) {
                item.setArea();
                Rectangle itemu = new Rectangle(item.getWorldX(), item.getWorldY(), (int)item.getRectangle().getWidth(), (int)item.getRectangle().getHeight());
                if (hracov.intersects(itemu)) {
                    inventar.pridajVec(item);
                    item.setAktivny(false);
                }
            }
        }
    }

    /**
     * Metóda checkNepriatelKolizia() slúži na zistenie kolízie medzi Hráčom a Nepriatelmi, pokiaľ sa tak stane, hráčovi sa uberú životy (HP)
     * Hráčovi a Nepriatelom sa taktiež nastavý lastDamageTime (čas posledného poškodenia), tým pádom sa zakáže rýchle minutie HP pokiaľ sa dotýkajú dlhú dobu
     * @param nepriatel - Parametrom je samotný nepriateľ, s ktorým by hráč mohol mať kolíziu
     * @param hrac - Parametrom je taktiež Hráč, keďže kontrolujeme kolíziu medzi ním a Nepriatelom
     */
    public void checkNepriatelKolizia(Nepriatel nepriatel, Hrac hrac) {
        Rectangle hracov = new Rectangle(hrac.getWorldX(), hrac.getWorldY(), (int)hrac.getDefaultSolidAreaHrac().getWidth(), (int)hrac.getDefaultSolidAreaHrac().getHeight());
        Rectangle nepriatelov = new Rectangle(nepriatel.getWorldX(), nepriatel.getWorldY(), (int)nepriatel.getSolidArea().getWidth(), (int)nepriatel.getSolidArea().getHeight());
        if (hracov.intersects(nepriatelov)) {
            if (System.currentTimeMillis() - nepriatel.getLastDamageTime() > 550) {
                hrac.uberHp(nepriatel.getDamage());
                nepriatel.setLastDamageTime(System.currentTimeMillis());
                hrac.setLastDamageTime(System.currentTimeMillis());
            }
        }
    }

    /**
     * Metóda checkSelfKolizia() slúži na zistenie kolízie medzi Nepriatelmi, pokiaľ sa stane, nepriatelom sa uberú HP
     * Pokiaľ nepriatelove HP spadnú pod 0,5, nepriatel sa nastaví na neaktívneho a už nebude v hre figurovať
     * Hráčovi sa taktiež pridáva skóre pokiaľ nepriatel zomrie, ale nepridá sa mu počet zabitých nepriatelov, keďže sa nepriatelia zabijú samy
     * @param nepriatelia - Parametrom je list nepriatelov, cez ktorý chceme iterovať. Parameter je len kópiou pôvodného listu nepriateľov
     * @param hrac - Parametrom je taktiež hráč, keďže mu chceme pridávať skóre za každého mŕtveho nepriatela
     */
    public void checkSelfKolizia(List<Nepriatel> nepriatelia, Hrac hrac) {
        for (int i = 0; i < nepriatelia.size(); i++) {
            Nepriatel nepriatel1 = nepriatelia.get(i);
            Rectangle rect1 = new Rectangle(nepriatel1.getWorldX(), nepriatel1.getWorldY(), (int)nepriatel1.getSolidArea().getWidth(), (int)nepriatel1.getSolidArea().getHeight());
            for (int j = i + 1; j < nepriatelia.size(); j++) {
                Nepriatel nepriatel2 = nepriatelia.get(j);
                Rectangle rect2 = new Rectangle(nepriatel2.getWorldX(), nepriatel2.getWorldY(), (int)nepriatel2.getSolidArea().getWidth(), (int)nepriatel2.getSolidArea().getHeight());
                if (rect1.intersects(rect2) && nepriatel1.getCisloSveta() == nepriatel2.getCisloSveta() && nepriatel1.jeAktivny() && nepriatel2.jeAktivny()) {
                    nepriatel1.setKolizia(true);
                    nepriatel2.setKolizia(true);
                    if (System.currentTimeMillis() - nepriatel1.getLastDamageTime() > 850 && System.currentTimeMillis() - nepriatel2.getLastDamageTime() > 850) {
                        nepriatel1.uberHp(nepriatel2.getDamage());
                        nepriatel2.uberHp(nepriatel1.getDamage());
                        nepriatel1.setLastDamageTime(System.currentTimeMillis());
                        nepriatel2.setLastDamageTime(System.currentTimeMillis());
                        if (nepriatel1.getHp() <= 0.5 && nepriatel1.jeAktivny()) {
                            hrac.pridajSkore(nepriatel1.getSkoreZabitia());
                            nepriatel1.setAktivny(false);
                        } else if (nepriatel2.getHp() <= 0.5 && nepriatel2.jeAktivny()) {
                            hrac.pridajSkore(nepriatel2.getSkoreZabitia());
                            nepriatel2.setAktivny(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * Metóda checkZautocenie() slúži na zistenie skutočnosti, či hráč svojím útokom zasiahol nepriatela
     * Pokiaľ hráč zasiahne nepriatela a zabije ho útokmi, pripočíta sa mu skóre a taktiež počet zabitých nepriatelov
     * Keď nepriatel zomrie, opäť sa nastaví na neaktívneho a v hre už nefiguruje
     * @param nepriatelia - Parametrom je list nepriatelov cez ktorých chceme iterovať, opäť sa jedná len o kópiu pôvodného listu
     * @param hrac - Parametrom je hráč, keďže chceme hráčovi pridávať skóre a počet zabitých nepriateľov
     * @param inventar - Parametrom je inventár, keďže pri útočení musíme zistiť či má hráč zbraň, poprípade akú zbraň a aké má zbraň poškodenie (damage)
     */
    public void checkZautocenie(List<Nepriatel> nepriatelia, Hrac hrac, Inventar inventar) {
        Rectangle zbran = null;
        if (inventar.getHracovaZbran() != null) {
            zbran = new Rectangle(inventar.getHracovaZbran().getWorldX(), inventar.getHracovaZbran().getWorldY(), (int)inventar.getHracovaZbran().getRectangle().getWidth(), inventar.getHracovaZbran().getRectangle().height);
        }
        for (Nepriatel nepriatel1 : nepriatelia) {
            Rectangle rect1 = new Rectangle(nepriatel1.getWorldX(), nepriatel1.getWorldY(), (int)nepriatel1.getSolidArea().getWidth() + 20, (int)nepriatel1.getSolidArea().getHeight() + 20);
            if (zbran != null) {
                zbran.x = hrac.getWorldX();
                zbran.y = hrac.getWorldY();
            }
            if (zbran != null && zbran.intersects(rect1)) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (System.currentTimeMillis() - nepriatel1.getLastDamageTime() > 350) {
                    nepriatel1.setLastDamageTime(System.currentTimeMillis());
                    nepriatel1.uberHp(inventar.getHracovaZbran().getPoskodenie());
                }
            }
            if (nepriatel1.getHp() <= 0.5 && nepriatel1.jeAktivny()) {
                hrac.pridajSkore(nepriatel1.getSkoreZabitia());
                hrac.pridajPocetZabitych();
                nepriatel1.setAktivny(false);
            }
        }
    }

    /**
     * Metóda checkDvere() slúži na zistenie skutočnosti, či sa hráč dotýka dverí, a pokiaľ má hráč požadovaný odomykací kľúč, dvere sa otvoria
     * @param hrac - Parametrom je Hráč, keďže chceme zistiť kolíziu medzi ním a dverami
     * @param inventar - Parametrom je inventár hráča, keďže chceme zistiť či má hráč v inventári požadovaný kľúč
     */
    public void checkDvere(Hrac hrac, Inventar inventar) {
        int cisloSveta = this.manazerSvetov.getCisloSveta();
        Rectangle hracov = new Rectangle(hrac.getWorldX(), hrac.getWorldY(), (int)hrac.getDefaultSolidAreaHrac().getWidth(), (int)hrac.getDefaultSolidAreaHrac().getHeight());
        for (Dvere dverevhre : this.dvere) {
            if (cisloSveta == dverevhre.getCisloSveta()) {
                Rectangle rectangleDveri = new Rectangle(dverevhre.getWorldX(), dverevhre.getWorldY() - 10, (int)dverevhre.getRectangle().getWidth(), (int)dverevhre.getRectangle().getHeight());
                if (hracov.intersects(rectangleDveri)) {
                    dverevhre.odomkni(inventar);
                    if (dverevhre.getZamknute()) {
                        switch (hrac.getSmer()) {
                            case "vpravo", "vlavo":
                                break;
                            case "hore":
                                hrac.setWorldY(hrac.getWorldY() + 20);
                                break;
                            case "dole":
                                hrac.setWorldY(hrac.getWorldY() - 20);
                                break;
                        }
                    }
                }
            }
        }
    }
}


