package bary;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.FontMetrics;

/**
 * Trieda TimerBar slúži na vytvorenie TimerBaru pre hráča, ktorý ráta koľko sekúnd už hráč hrá
 */
public class TimerBar {
    private long startTime;
    public TimerBar() {
        this.startTime = 0;
    }

    /**
     * Metóda draw() slúži na vykreslenie samotného TimerBaru podľa zadaných parametrov metódy
     * @param g2 - Parameter knižnice Graphics2D, keďže objekt chceme kresliť
     * @param screenX - Parameter X, podľa neho sa vypočíta finálna hodnota X na kreslenie
     * @param screenY - Parameter Y, podľa neho sa vypočíta finálna hodnota Y na kreslenie
     */
    public void draw(Graphics2D g2, int screenX, int screenY) {
        if (this.startTime == 0) {
            this.startTime = System.currentTimeMillis();
        }
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - this.startTime;
        int sekundy = (int)(elapsedTime / 1000);
        int minuty = sekundy / 60;
        sekundy %= 60;
        int barWidth = 100;
        int barHeight = 20;
        int hpPadding = 2;
        int hpBarY = screenY - barHeight - (hpPadding * 30);
        int hpBarX = screenX - (barWidth + (barWidth / 2));
        g2.setColor(Color.BLACK);
        g2.fillRect(hpBarX, hpBarY, barWidth, barHeight);
        String cas = String.format("%02d:%02d", minuty, sekundy);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth("ČAS: " + cas);
        int textHeight = fm.getHeight();
        int textX = hpBarX + barWidth / 2 - textWidth / 2;
        int textY = hpBarY + barHeight / 2 + textHeight / 2 - 1;
        g2.drawString("ČAS: " + cas, textX, textY);
    }
}
