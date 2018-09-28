package Blackjack;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private List<Card> cards;
    private int total;

    public Person() {
        cards = new ArrayList<Card>();
        total = 0;
    }

    public Card drawCard(Card card) {
        cards.add(card);
        if (card.getValue() == 11 && (getTotal() + 11) > 21) {
            total -= 10;
        }
        total += card.getValue();
        return card;
    }

    public int getTotal() {
        return total;
    }

    public int getCard(int i) {
        return cards.get(i).getValue();
    }

    public int listLength() {
        return cards.size();
    }
}
