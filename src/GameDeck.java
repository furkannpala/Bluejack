import java.util.Random;

public class GameDeck {
    private final Card[] cards;
    private int size;

    public GameDeck() {
        this.cards = new Card[60]; // 4 colors 10 values(4*10/Game Decks) + 2*10(Player Decks)
        this.size = 0;
        initializeDeck();
    }

    private void initializeDeck() {
        String[] colors = {"blue", "yellow", "red", "green"};
        Random r = new Random();
        Random innerrandom = new Random();

        for (int i = 0; i < colors.length; i++) {
            for (int j = 1; j <= 10; j++) {
                addCard(new Card(colors[i], j, "+"));
            }
        }

        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            String color = colors[new Random().nextInt(colors.length)];
            int value = new Random().nextInt(6) + 1;
            boolean isPositive = random.nextBoolean();
            String sign;
            if (isPositive) {
                sign = "+";
            } else {
                sign = "-";
            }
            addCard(new Card(color, value, sign));
        }

        for (int i = 0; i < 2; i++) {
            if (size > 0) {
                if (r.nextDouble() > 0.2) {
                    String color = colors[new Random().nextInt(colors.length)];
                    int value = new Random().nextInt(6) + 1;
                    String sign = (new Random().nextBoolean()) ? "+" : "-";
                    addCard(new Card(color, value, sign));
                } else if (r.nextDouble() <= 0.2) {
                    if (innerrandom.nextDouble() <= 0.5) {  // %50 flip %50 double
                        String specialType = "flip";
                        addCard(new Card("", 0, specialType));
                    } else if (innerrandom.nextDouble() > 0.5) {
                        String specialType = "double";
                        addCard(new Card("", 0, specialType));
                    }
                }
            }
        }
        shuffle();
    }
    private void shuffle() {
        Random random = new Random();
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    private void addCard(Card card) {
        if (size < cards.length) {
            cards[size++] = card;
        }
    }

    public Card drawCard() {
        if (size > 0) {
            return cards[--size];
        } else {
            return null;
        }
    }
}


