package main;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Trieda UvodnaPlocha slúži na vytvorenie a vykreslenie úvodnej plochy, s použitím tlačidiel (buttonov).
 * Trieda používa najmä knižnicu JFrame na vytvorenie okna, s použítím ActionListeneru ktoré slúži na fungovanie tlačidiel (buttonov).
 */
public class UvodnaPlocha  implements ActionListener {
    private final JFrame frame;
    private JButton button1;
    private JButton button2;

    /**
     * Trieda UvodnaPlocha vytvára pomocou importovaných knižníc JFrame, JLabel a JButton okno úvodnej plochy.
     * Úvodná plocha pozostáva zo 2 buttonov, (button ŠTART, button KONIEC)
     * Plocha taktiež obsahuje obrázok, ktorý slúži ako pozadie
     */
    public UvodnaPlocha() {
        this.frame = new JFrame();
    }

    /**
     * Metóda vytvorPlochu() slúži na vytvorenie 2 buttonov a ich másledné nastavenie na požadované X, Y súradnice, farbu či font
     * Metóda taktiež vytvára nový JLabel pozadia, kde je nastavený obrázok
     */
    public void vytvorPlochu() {
        this.button1 = new JButton("START");
        this.button1.setBounds(250, 400, 300, 100);
        this.button1.addActionListener(this);
        this.button1.setFocusable(false);
        this.button1.setFont(new Font("Boomer Tantrum", Font.BOLD, 35));
        this.button1.setBackground(Color.GREEN);
        this.button1.setBorder(BorderFactory.createEtchedBorder());
        this.button2 = new JButton("KONIEC");
        this.button2.setBounds(250, 550, 300, 100);
        this.button2.addActionListener(this);
        this.button2.setFocusable(false);
        this.button2.setFont(new Font("Boomer Tantrum", Font.BOLD, 35));
        this.button2.setBackground(Color.RED);
        this.button2.setForeground(Color.WHITE);
        this.button2.setBorder(BorderFactory.createEtchedBorder());
        this.frame.setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("res/plocha/plocha.png"));
        background.setLayout(new FlowLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(824, 802);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.add(this.button1);
        this.frame.add(this.button2);
        this.frame.add(background);
        this.frame.setTitle("Uvodná obrazovka | Monster Slayer");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation((int)(screenSize.getWidth() / 2 - this.frame.getSize().getWidth() / 2), (int)(screenSize.getHeight() / 2 - this.frame.getSize().getHeight() / 2));
    }

    /**
     * Metóda actionPerformed(ActionEvent e) slúži na zistenie, či používateľ stlačil daný button, a ak áno, vykoná sa daný kód.
     * Pri stlačení buttonu1 sa vytvorí nové okno do ktorého sa pridá GamePanel
     * Pri stlačení buttonu2 sa vypne program cez System.exit(0)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button1) {
            Okno okno = new Okno();
            okno.pridajOkno(new GamePanel());
            this.frame.dispose();
        }
        if (e.getSource() == this.button2) {
            System.exit(0);

        }
    }
}


