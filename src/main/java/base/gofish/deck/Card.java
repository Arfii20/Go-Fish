package base.gofish.deck;

import java.io.Serial;
import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
    private final Suit suit;
    private final Name name;
    private final int value;
    @Serial
    private static final long serialVersionUID = 0;

    public Card (Suit suit, Name name, int value) {
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

    @Override
    public int compareTo(Card cardIn) {
        return Integer.compare(this.value, cardIn.value);
    }
}
