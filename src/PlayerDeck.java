import java.util.Random;
public class PlayerDeck {
    private Card[] cards;
    private int size;

    public PlayerDeck(Card[] initialCards) {
        this.cards = new Card[4];
        this.size = 0;

        for (int i = 0; i < 4; i++ ) {
            addCard(initialCards[i]);
        }
    }

    public void addCard(Card card) {
        if (size < cards.length) {
            cards[size++] = card;
        }
    }

    public Card[] getHand() {
        Card[] hand = new Card[4];
        if(size > 0) {
            Random r = new Random();
            for (int i = 0; i < 4; i++) {
                int randomIndex = new Random().nextInt(size);
                 hand[i] = cards[randomIndex];
                removeCard(randomIndex);
            }
        }
        return hand;
    }

    public Card[] getCards() {
        return cards;
    }

    private void removeCard(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size - 1; i++) {
                cards[i] = cards[i + 1];
            }
            cards[size - 1] = null;
            size--;
        }
    }


    public String toString() {
        StringBuilder deckString = new StringBuilder();
        for (int i = 0; i < size; i++) {
            deckString.append(cards[i]).append(", ");
        }
        return deckString.toString();
    }
}

