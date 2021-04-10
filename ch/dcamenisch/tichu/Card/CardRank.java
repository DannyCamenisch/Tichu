package ch.dcamenisch.tichu.Card;

public enum CardRank {
    DOG(0, 0),
    MAHJONG(1, 0),
    TWO(2, 0),
    THREE(3, 0),
    FOUR(4, 0),
    FIVE(5, 5),
    SIX(6, 0),
    SEVEN(7, 0),
    EIGHT(8, 0),
    NINE(9, 0),
    TEN(10, 10),
    JACK(11, 0),
    QUEEN(12, 0),
    KING(13, 10),
    ACE(14, 0),
    PHOENIX(15, -25),
    DRAGON(16, 25);

    private int power;
    private int points;

    public int getPower() { return power; }
    public int getPoints() { return points; }

    public void setPower(int power) { this.power = power; }

    private CardRank(int power, int points) {
        this.power = power;
        this.points = points;
    }
}
