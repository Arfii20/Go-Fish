package base.gofish.deck;

/**
 * The {@code Suit} enum represents the four standard suits in a deck of playing cards: Spades, Clubs, Diamonds, and Hearts.
 * These values are used to specify the suit of a playing card.
 * Each enum constant corresponds to one of the four suits.
 * <p>
 * Example usage:
 * <pre>
 * Suit mySuit = Suit.HEARTS;
 * if (mySuit == Suit.CLUBS) {
 *     // Handle clubs suit.
 * }
 * </pre>
 *
 * @author Arefin Ahammed
 * @version 1.0
 */
public enum Suit {
    /**
     * Spades suit.
     */
    SPADES,

    /**
     * Clubs suit.
     */
    CLUBS,

    /**
     * Diamonds suit.
     */
    DIAMONDS,

    /**
     * Hearts suit.
     */
    HEARTS
}
