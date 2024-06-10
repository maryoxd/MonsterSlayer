package main;

/**
 * Trieda Main slúži ako hlavný spúšťač programu, vytvoára novú plochu (JFrame) a volá metódu tejto plochy, aby vytvorila úvodnu plochu
 */
public class Main {
    public static void main(String[] args) {
        UvodnaPlocha plocha = new UvodnaPlocha();
        plocha.vytvorPlochu();
    }
}
