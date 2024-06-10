package bary;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;

/**
 * Trieda SkoreBar slúži na vytvorenie obdĺžníku ktorý vypisuje hráčove skóre
 */
public class SkoreBar {
    public SkoreBar() {
    }

    /**
     * Metóda draw slúži na samotné vykreslenie obdĺžníka so skóre
     * @param g2 - Parameter knižnice Graphics2D, keďže ho chceme vykreslovať
     * @param screenX - Parameter X, podľa neho sa vypočíta finálna hodnota X na kreslenie
     * @param screenY - Parameter Y, podľa neho sa vypočíta finálna hodnota Y na kreslenie
     * @param skore - Parameter skóre, slúži ako hodnota skóre ktorá sa bude vykreslovať
     */
    public void draw(Graphics2D g2, int screenX, int screenY, double skore) {
        int barWidth = 100;
        int barHeight = 20;
        int hpPadding = 2;
        int hpBarY = screenY - barHeight - (hpPadding * 30);
        int hpBarX = screenX - (barWidth / 2);
        g2.setColor(Color.BLACK);
        g2.fillRect(hpBarX, hpBarY, barWidth, barHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth("SKÓRE: " + skore);
        int textHeight = fm.getHeight();
        int textX = hpBarX + barWidth / 2 - textWidth / 2;
        int textY = hpBarY + barHeight / 2 + textHeight / 2 - 1;
        g2.drawString("SKÓRE: " + skore, textX, textY);
    }
}

