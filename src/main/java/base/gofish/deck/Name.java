package base.gofish.deck;

/**
 * The {@code Name} enum represents the names of playing cards in a standard deck,
 * including numbered cards (Two to Ten), face cards (Jack, Queen, King), and the Ace.
 * These values are used to specify the name or rank of a playing card.
 * <p>
 * Each enum constant corresponds to one of the possible card names.
 * </p>
 * Example usage:
 * <pre>
 * Name myCardName = Name.JACK;
 * if (myCardName == Name.ACE) {
 *     // Handle Ace card.
 * }
 * </pre>
 *
 * @author Arefin Ahammed
 * @version 1.0
 */
public enum Name {
    /**
     * The Two card.
     */
    TWO,

    /**
     * The Three card.
     */
    THREE,

    /**
     * The Four card.
     */
    FOUR,

    /**
     * The Five card.
     */
    FIVE,

    /**
     * The Six card.
     */
    SIX,

    /**
     * The Seven card.
     */
    SEVEN,

    /**
     * The Eight card.
     */
    EIGHT,

    /**
     * The Nine card.
     */
    NINE,

    /**
     * The Ten card.
     */
    TEN,

    /**
     * The Jack card.
     */
    JACK,

    /**
     * The Queen card.
     */
    QUEEN,

    /**
     * The King card.
     */
    KING,

    /**
     * The Ace card.
     */
    ACE
}
