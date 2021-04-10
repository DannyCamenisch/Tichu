package ch.dcamenisch.tichu.Trick;

public enum CardSetType {
    SINGLE_CARD(0),
    PAIR(0),
    MULTIPLE_PAIR(0),
    TRIPLE(0),
    FULL_HOUSE(0),
    STRAIGHT(0),
    FOUR_CARDS(1),
    STRAIGHT_FLUSH(2);

    private int power;

    public int getPower() { return power; }

    private CardSetType(int power) {
        this.power = power;
    }
}
