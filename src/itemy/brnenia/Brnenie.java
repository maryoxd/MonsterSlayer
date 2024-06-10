package itemy.brnenia;

import itemy.Item;

/**
 * Trieda Brnenie je potomkom triedy Item a implementuje Interface Vybavenie
 * Predstavuje samotné brnenie ako objekt, ktorý hráč môže používať a pridáva mu bonusové HP
 */
public class Brnenie extends Item implements Vybavenie {
    private final double bonusoveHp;

    /**
     * Konštruktor triedy Brnenie
     * @param nazov - Parameter nazov predstavuje samotný názov brnenia
     * @param cisloSveta - Parameter CisloSveta predstavuje číslo sveta, v ktorom sa brnenie objavuje
     * @param worldX - Parameter worldX predstavuje samotnú X hodnotu brnenia na mape
     * @param worldY - Parameter worldY predstavuje samotnú Y hodnotu brnenia na mape
     * @param bonusoveHp - Parameter bonusoveHp predstavuje bonusové HP ktoré brnenie pridáva hráčovi
     */
    public Brnenie(String nazov, int cisloSveta, int worldX, int worldY, double bonusoveHp) {
        super(nazov, cisloSveta, worldX, worldY);
        this.bonusoveHp = bonusoveHp;
    }

    /**
     * Metóda getBonusoveHP() predstavuje overridnutu metódu z Interfacu, ktorej úlohou je vrátiť hodnotu týchto bonusových HP
     * @return - Samotnou návratovou hodnotou je počet bonusových HP, teda bonusoveHp
     */
    @Override
    public double getBonusoveHP() {
        return this.bonusoveHp;
    }

}
