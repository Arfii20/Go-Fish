package base.gofish.deck;

import java.io.Serial;
import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
    private final String fullName;
    private final Suit suit;
    private final Name name;
    private final int value;
    @Serial
    private static final long serialVersionUID = 0;

    public Card (String fullName, Suit suit, Name name, int value) {
        this.fullName = fullName;
        this.suit = suit;
        this.name = name;
        this.value = value;
    }

    public Suit getType() {
        return this.suit;
    }

    public int getValue() {
        return this.value;
    }

    public Name getName() {
        return this.name;
    }

    public String getFullName() {
        return this.fullName;
    }

    @Override
    public int compareTo(Card cardIn) {
        return Integer.compare(this.getValue(), cardIn.getValue());
    }
}
