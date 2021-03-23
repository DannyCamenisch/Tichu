package ch.dcamenisch.tichu;

public class Card implements Comparable{
    public final Color color;
    public final Value value;

    public Card (Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public int cardValue() {
        switch (this.value) {
            case DRAGON: return 25;
            case PHOENIX: return -25;
            case KING: return 10;
            case TEN: return 10;
            case FIVE: return 5;
            default: return 0;
        }
    }

    public void printCard() {
        System.out.println(color + "_" + value);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Card) {
            return value.priority - ((Card) o).value.priority;
        } else {
            return 0;
        }
    }

    public enum Color {
        BLUE,
        RED,
        GREEN,
        BLACK,
        SPECIAL
    }

    public enum Value {
        MAHJONG(0),
        DOG(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14),
        PHOENIX(15),
        DRAGON(16);

        public final int priority;
        private Value(int priority) {
            this.priority = priority;
        }
    }

}