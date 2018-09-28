package Blackjack;

import java.util.Scanner;

public class Blackjack {
    Person player = new Person();
    Person dealer = new Person();
    private Deck deck;

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.start();
    }

    public void start() {
        deck = new Deck();
        System.out.println("start");

        deck.shuffle();
        initialDeal(player);
        System.out.println("your cards are: " + player.getCard(0) + " and " + player.getCard(1));
        showDealersFirst();
        boolean end = false;
        while(!end) {
            if(player.getTotal() == 21) {
                end = true;
                System.out.println("you won! Blackjack!");
            } else if (dealer.getTotal() == 21) {
                end = true;
                System.out.println("you lost Dealer had blackjack");
            }
            if (!end && hit()) {
                draw(player);
                System.out.println("you drew: " + player.getCard(player.listLength() - 1));
                System.out.println("your total is: " + player.getTotal());
                if(player.getTotal() == 21) {
                    end = true;
                    System.out.println("you won! Dealers total was: " + dealer.getTotal());
                }
                if (player.getTotal() > 21) {
                    end = true;
                    System.out.println("you lost. Your total was: " + player.getTotal());
                }
            }
            else if (!end) {
                boolean shouldDealerHit = dealerHit();
                if(!shouldDealerHit) {
                    System.out.println("dealer stands");
                    if (player.getTotal() > dealer.getTotal()) {
                        end = true;
                        System.out.println("you won! Dealers total was: " + dealer.getTotal());
                    } else {
                        end = true;
                        System.out.println("you lost Dealers total was: " + dealer.getTotal());
                    }
                }
                while(shouldDealerHit) {
                    draw(dealer);
                    System.out.println("Dealer drew: " + player.getCard(player.listLength() - 1));
                    shouldDealerHit = dealerHit();
                    if(dealer.getTotal() == 21) {
                        shouldDealerHit = false;
                        System.out.println("you lost Dealers total was: " + dealer.getTotal());
                    }
                    if(dealer.getTotal() > 21) {
                        shouldDealerHit = false;
                        System.out.println("you won! Dealers total was: " + dealer.getTotal());
                    }
                }
                end = true;
            }
        }
    }

    public void initialDeal(Person person) {
        for(int i = 0; i < 2; i++) {
            draw(person);
        }
    }

    public void showDealersFirst() {
        initialDeal(dealer);
        System.out.println("dealer's first card is: " + dealer.getCard(0));
    }

    public void draw(Person person) {
        person.drawCard(deck.draw());
    }

    public boolean hit() {
        System.out.println("hit? y/n");
        Scanner in = new Scanner(System.in);
        while(true) {
            String input = in.next();
            if(input.equals("y")) {
                return true;
            }
            else if(input.equals("n")) {
                return false;
            }
            else
                System.out.println("please type 'y' or 'n'");
        }
    }

    public boolean dealerHit() {
        double rand = Math.random() * dealer.getTotal();

        return rand < 14;
    }

}
