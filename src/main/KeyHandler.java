package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Trieda KeyHandler je trieda ktorej úlohou je starať sa o všetky akcie spravené používateľom, teda pokiaľ použije nejaké tlačídlo
 */
public class KeyHandler implements KeyListener {
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean ipressed;
    private boolean jeZmacknutaRaz;
    private boolean sipkaVpravo;
    private boolean sipkaVlavo;
    private boolean sipkaHore;
    private boolean sipkaDole;
    private boolean enterPressed;
    private boolean spacePressed;
    private boolean utocenie;

    /**
     * Konštruktor triedy KeyHandler
     */
    public KeyHandler() {
        this.jeZmacknutaRaz = false;
    }

    /**
     * Metóda overriduje pôvodnú metódu, avšak v programe nepoužívame písanie (keyTyped), preto metóda nemá žiadne použitie avšak musí overridovať pôvodnú
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metóda keyPressed slúži na kontrolovanie, či uživateľ stlačil tlačidlo
     * Rôzne tlačidlá môžu mať iné úlohy:
     * Tlačidlo ESC slúži na vypnutie programu (System.exit(0))
     * Tlačidlá W,A,S,D slúžia na pohyb hráča
     * Tlačidlo I slúži na otvorenie inventára
     * Tlačidlá šípiek slúžia na pohyb v inventári
     * Tlačidlo SPACE slúži na útok hráča
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int klavesa = e.getKeyCode();
        if (klavesa == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (klavesa == KeyEvent.VK_W) {
            this.upPressed = true;
        }
        if (klavesa == KeyEvent.VK_A) {
            this.leftPressed = true;
        }
        if (klavesa == KeyEvent.VK_D) {
            this.rightPressed = true;
        }
        if (klavesa == KeyEvent.VK_S) {
            this.downPressed = true;
        }
        if (klavesa == KeyEvent.VK_I) {
            if (!this.jeZmacknutaRaz) {
                this.jeZmacknutaRaz = true;
                this.ipressed = true;
            } else {
                this.jeZmacknutaRaz = false;
                this.ipressed = false;
            }
        }
        if (klavesa == KeyEvent.VK_RIGHT) {
            this.sipkaVpravo = true;

        }
        if (klavesa == KeyEvent.VK_LEFT) {
            this.sipkaVlavo = true;
        }
        if (klavesa == KeyEvent.VK_UP) {
            this.sipkaHore = true;
        }
        if (klavesa == KeyEvent.VK_DOWN) {
            this.sipkaDole = true;
        }
        if (klavesa == KeyEvent.VK_ENTER) {
            this.enterPressed = true;
        }
        if (klavesa == KeyEvent.VK_SPACE) {
            this.spacePressed = true;
            this.utocenie = true;
        }
        this.zrusSipky();
        this.zrusUtocenie();
    }

    /**
     * Metóda zrusSipky() slúži na automatické nastavenie použitia šípiek na false po určitom čase, ktorý je nastavený v časovači
     * Týmto spôsobom sme vedeli spraviť pomalšie hýbanie v rámci inventára, keďže sa šípky vždy zrušia
     */
    public void zrusSipky() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                KeyHandler.this.sipkaHore = false;
                KeyHandler.this.sipkaDole = false;
                KeyHandler.this.sipkaVpravo = false;
                KeyHandler.this.sipkaVlavo = false;
                KeyHandler.this.enterPressed = false;
                KeyHandler.this.spacePressed = false;
                timer.cancel();
            }
        }, 20);
    }

    /**
     * Metóda zrusUtocenie() slúži na vytvorenie ďalšieho Timera, ktorý po nastavenom čase nastaví útočenie na false, čím vieme spraviť dlhšiu animáciu útočenia
     */
    public void zrusUtocenie() {
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            /**
             * Metóda overriduje pôvodnú metódu run(), v ktorej rušíme premennú útočenie v rámci triedy
             */
            @Override
            public void run() {
                KeyHandler.this.utocenie = false;
                timer2.cancel();
            }
        }, 400);
    }

    /**
     * Metóda keyReleased() slúži na zrušenie stlačenia tlačidiel po tom, ako ich užívateľ už reálne nedrží
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int klavesa = e.getKeyCode();

        if (klavesa == KeyEvent.VK_W) {
            this.upPressed = false;
        }
        if (klavesa == KeyEvent.VK_A) {
            this.leftPressed = false;
        }
        if (klavesa == KeyEvent.VK_D) {
            this.rightPressed = false;
        }
        if (klavesa == KeyEvent.VK_S) {
            this.downPressed = false;
        }
    }

    /**
     * Metóda isUpPressed() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isUpPressed() {
        return this.upPressed;
    }

    /**
     * Metóda isDownPressed() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isDownPressed() {
        return this.downPressed;
    }

    /**
     * Metóda isLeftPressed() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isLeftPressed() {
        return this.leftPressed;
    }

    /**
     * Metóda isRightPressed() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isRightPressed() {
        return this.rightPressed;
    }

    /**
     * Metóda isIPressed() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isIpressed() {
        return this.ipressed;
    }

    /**
     * Metóda isSipkaVpravo() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isSipkaVpravo() {
        return this.sipkaVpravo;
    }

    /**
     * Metóda isSipkaVlavo() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isSipkaVlavo() {
        return this.sipkaVlavo;
    }

    /**
     * Metóda isSipkaHore() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isSipkaHore() {
        return this.sipkaHore;
    }

    /**
     * Metóda isSipkaDole() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isSipkaDole() {
        return this.sipkaDole;
    }

    /**
     * Metóda isEnterPressed() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isEnterPressed() {
        return this.enterPressed;
    }

    /**
     * Metóda isSpacePressed() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean isSpacePressed() {
        return this.spacePressed;
    }

    /**
     * Metóda getUtocenie() slúži na vrátenie hodnoty či používateľ drží dané tlačídlo
     * @return - Samotnou návratovou hodnotou je boolean hodnota držania daného tlačidla
     */
    public boolean getUtocenie() {
        return this.utocenie;
    }
}
