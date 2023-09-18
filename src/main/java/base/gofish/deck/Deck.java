package base.gofish.deck;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Deck implements Serializable {
    private final List<Card> deck = new ArrayList<>();
    private final Map<String, Card> cardMap = new HashMap<>();

    @Serial
    private static final long serialVersionUID = 4L;


    public Deck(){
        int count;
        for (Suit suit: Suit.values()) {
            count = 2;
            for (Name name: Name.values()) {
                deck.add(new Card(suit.name() + "_" + name.name(), suit, name, count++));
            }
        }

        for (Card card : deck) {
            cardMap.put(card.getFullName(), card);
        }
    }

    public void restockDeck(){
        int count;
        for (Suit suit: Suit.values()) {
            count = 2;
            for (Name name: Name.values()) {
                deck.add(new Card(suit.name() + "_" + name.name(), suit, name, count++));
            }
        }
    }

    public Card draw(){
        Card lastCard = null;
        if (!deck.isEmpty()) {
            lastCard = deck.remove(deck.size() - 1);
        }
        return lastCard;
    }

    public void remove(Card card){
        deck.remove(card);
    }

    public void shuffle(Random random){
        Collections.shuffle(deck, random);
    }

    public int size(){
        return deck.size();
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }

    public Map<String, Card> getCardMap() {
        return this.cardMap;
    }
}
