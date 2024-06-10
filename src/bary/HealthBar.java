package bary;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Trieda HealthBar slúži na vytvorenie HealthBaru požadovaným entitám.
 */
public class HealthBar {
    public HealthBar() {
    }

    /**
     * Metóda draw() slúži na vykreslenie samotného HealthBaru podľa zadaných parametrov metódy
     * @param g2 - Parameter knižnice Graphics2D, keďže objekt chceme kresliť
     * @param screenX - Parameter X, podľa neho sa vypočíta finálna hodnota X na kreslenie
     * @param screenY - Parameter Y, podľa neho sa vypočíta finálna hodnota Y na kreslenie
     * @param hp - Parameter HP, keďže samotný HealthBar predstavuje dva obdĺžníky, musíme vedieť koľko HP (šírku) bude mať obdĺžnik
     * @param hracov - Boolean hodnota toho, či HealthBar vykreslujeme pre hráča, keďže sa mu môžu HP meniť inak ako nepriateľom
     * @param maxHp - Parameter MaxHP, aby sme pomocou kreslenia stringu vedeli zaznačiť, koľko HP má entita z maximálnych HP
     */
    public void draw(Graphics2D g2, int screenX, int screenY, double hp, boolean hracov, double maxHp) {
        int barWidth;
        int barHeight;
        int hpPadding = 2;
        int hpBarX;
        int hpBarY;
        double hpPercent = hp / maxHp;
        if (!hracov) {
            barWidth = (int)(maxHp > 100 ? maxHp : 100);
            barHeight = 10;
            hpBarY = screenY - barHeight - hpPadding;
            hpBarX = screenX - (barWidth / 4);
        } else {
            barWidth = 250;
            barHeight = 50;
            hpBarY = screenY - barHeight - (hpPadding * 40);
            hpBarX = screenX - (barWidth / hpPadding) - barHeight;
        }
        if (hp >= 1) {
            g2.setColor(Color.BLACK);
            g2.fillRect(hpBarX, hpBarY, barWidth, barHeight);

            int hpBarFillWidth = (int)(hpPercent * barWidth);
            if (hp > 70) {
                g2.setColor(Color.GREEN);
            } else if (hp >= 40) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.RED);
            }
            g2.fillRect(hpBarX + hpPadding, hpBarY + hpPadding, hpBarFillWidth - hpPadding * 2, barHeight - hpPadding * 2);
            g2.setColor(Color.WHITE);
            if (hracov) {
                g2.setFont(new Font("Arial", Font.BOLD, 25));
            } else {
                g2.setFont(new Font("Arial", Font.BOLD, 10));
            }
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(hp + " / " + maxHp);
            int textHeight = fm.getHeight();
            int textX = hpBarX + barWidth / 2 - textWidth / 2;
            int textY = hpBarY + barHeight / 2 + textHeight / 2 - 1;
            g2.drawString(hp + " / " + maxHp, textX, textY - 10);
        }
    }
}





