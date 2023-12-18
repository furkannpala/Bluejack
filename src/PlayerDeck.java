import java.util.Random;
public class PlayerDeck {
    private Card[] cards;
    private int size;
    private int totalScore;

    public PlayerDeck(Card[] initialCards) {
        this.cards = new Card[10];
        this.size = 0;
        this.totalScore = 0;

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
        Card[] hand = this.cards;
        if(size > 0) {
            Random r = new Random();
            for (int i = 0; i < 4; i++) {
                int randomIndex = r.nextInt(size);
                Card temp = hand[randomIndex];
                hand[randomIndex] = hand[i];
                hand[i] = temp;
            }
        }
        return hand;
    }

    public Card[] getCards() {
        return cards;
    }

    public void playCard(int index) {
        if (index >= 0 && index < size) {
            totalScore += cards[index].getValue();

            removeCard(index);
        }

    }


    public int getTotalScore() {
        return totalScore;
    }


    private void removeCard(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size - 1; i++) {
                cards[i] = cards[i + 1];
            }
            cards[size-1] = null;
            size--;
        }
    }


    public String toString() {
        String deckString = "";
        for (int i = 0; i < size; i++) {
            deckString += cards[i];
            if (i < size - 1) {
                deckString += ", ";
            }
        }
        return deckString;
    }
}