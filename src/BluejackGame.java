import java.util.Scanner;

public class BluejackGame {
    private GameDeck gameDeck;
    private PlayerDeck computerDeck;
    private PlayerDeck userDeck;
    private int computerScore;
    private int userScore;

    public BluejackGame() {
        gameDeck = new GameDeck();
        computerDeck = new PlayerDeck(drawInitialCards());
        userDeck = new PlayerDeck(drawInitialCards());
        computerScore = 0;
        userScore = 0;
    }

    private Card[] drawInitialCards() {
        Card[] initialCards = new Card[10];
        for (int i = 0; i < 5; i++) {
            initialCards[i * 2] = gameDeck.drawCard();
            initialCards[i * 2 + 1] = gameDeck.drawCard();
        }
        return initialCards;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayGameStatus();

            // User's turn
            userTurn(scanner);

            // Computer's turn
            computerTurn();

            // Check game end conditions
            if (isGameOver()) {
                displayGameResult();
                break;
            }
        }

    }

    private void userTurn(Scanner scanner) {
        System.out.println("\nUser's Turn:");

        // Draw a card from the game deck
        Card userDrawnCard = gameDeck.drawCard();
        System.out.println("User draws a card: " + userDrawnCard);

        // Display user's hand
        System.out.println("User's Hand: " + userDeck);

        // Ask user to stand or play a card
        System.out.print("Do you want to stand (s) or play a card (p)? ");
        char choice = scanner.next().charAt(0);

        if (choice == 'p') {
            playUserCard(scanner);
        } else {
            // User stands, add drawn card to the total score
            userScore += userDrawnCard.getValue();
        }

    }

    private void playUserCard(Scanner scanner) {
        // Get user's hand
        Card[] userHand = userDeck.getHand();
        System.out.println("Choose a card to play:");

        // Display user's hand
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + userHand[i]);
        }

        System.out.print("Enter the card number: ");
        int cardNumber = scanner.nextInt();

        if (cardNumber >= 1 && cardNumber <= 4) {
            // Play the selected card
            userDeck.playCard(cardNumber - 1);
            System.out.println("User plays: " + userHand[cardNumber - 1]);
        } else {
            System.out.println("Enter a Valid Number");
        }
    }

    private void computerTurn() {
        System.out.println("\nComputer's Turn:");

        // Draw a card from the game deck
        Card computerDrawnCard = gameDeck.drawCard();
        System.out.println("Computer draws a card: " + computerDrawnCard);

        // Add the drawn card to the computer's hand
        computerDeck.addCard(computerDrawnCard);
        computerScore += computerDrawnCard.getValue();

    }

    private boolean isGameOver() {
        return (userScore >= 20 || computerScore >= 20);
    }

    private void displayGameStatus() {
        System.out.println("\nCurrent Status:");
        System.out.println("User's Score: " + userScore);
        System.out.println("Computer's Score: " + computerScore);
    }

    private void displayGameResult() {
        System.out.println("\nGame Over!");
        System.out.println("User's Score: " + userScore);
        System.out.println("Computer's Score: " + computerScore);

        if (userScore > 20 && computerScore > 20) {
            System.out.println("It's a tie!");
        } else if (userScore > 20) {
            System.out.println("Computer wins!");
        } else if (computerScore > 20) {
            System.out.println("User wins!");
        } else {
            if (userScore > computerScore) {
                System.out.println("User wins!");
            } else if (userScore < computerScore) {
                System.out.println("Computer wins!");
            } else {
                System.out.println("It's a tie!");
            }
        }
    }
    public static void main(String[] args) {
        BluejackGame bluejackGame = new BluejackGame();
        bluejackGame.startGame();
    }
}
