package main;
import javax.swing.JFrame;

/**
 * Samotná trieda Okno predstavuje JFrame do ktorého je umožnené pridať GamePanel, čím sa začne samotná hra
 */
public class Okno {
    private final JFrame okno;

    /**
     * Konštruktor trieda Okno, vytvára nový JFrame
     */
    public Okno() {
        this.okno = new JFrame();
    }

    /**
     * Metóda pridajOkno() slúži na pridanie GamePanelu ktorý je JPanel do okna tejto triedy (JFrame), a následním nastavením okna
     * Nastavujeme defaulntú operáciu pri zatvorení, zakazujeme prenastavovanie veľkosti, či nastavujeme titulok
     * Následne samotné okno zviditeľníme a zapneme hlavnú metódu v tride GamePanel
     * @param gamePanel - Parameter GamePanel potrebujeme na pridanie GamePanelu ktorý je JPanel do nášho JFramu
     */
    public void pridajOkno(GamePanel gamePanel) {
        this.okno.add(gamePanel);
        this.okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.okno.setResizable(false);
        this.okno.setTitle("MONSTER SLAYER");
        this.okno.pack();
        this.okno.setLocationRelativeTo(null);
        this.okno.setVisible(true);
        gamePanel.startGameThread();
    }
}
