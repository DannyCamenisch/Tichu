package ch.dcamenisch.tichu.Card;

public enum CardSetType {
    SINGLE_CARD(0),
    PAIR(0),
    MULTIPLE_PAIR(0),
    TRIPPLE(0),
    FULL_HOUSE(0),
    STRAIGHT(0),
    FOUR_CARDS(1),
    STRAIGHT_FLUSH(2);

    public final int power;

    private CardSetType(int power) {
        this.power = power;
    }
}
