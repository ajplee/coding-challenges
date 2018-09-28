package Blackjack;

public class Card {
    private int rank;
    private int suit;
    private int value;

    enum Suits {
        Clubs,
        Diamonds,
        Hearts,
        Spades
    }

    enum Ranks {
        Joker,
        Ace,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King
    }

    Card(int suit, int rank) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        if(rank > 10) {
            value = 10;
        }
        else if(rank == 1) {
            value = 11;
        }
        else {
            value = rank;
        }
        return value;
    }
}


