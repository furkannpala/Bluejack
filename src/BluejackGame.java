import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BluejackGame {
    private GameDeck gameDeck;
    private PlayerDeck computerDeck;
    private PlayerDeck userDeck;
    private int computerScore;
    private int userScore;
    private int totalUserScore;
    private int totalComputerScore;

    private int Max_History_Size = 10;
    private String[] gameHistory;
    private int[] historyUserScores;
    private int[] historyComputerScores;
    private int historyIndex;
    private int gameCounter;
    private static String Game_History_File_Path = "game_history.txt";


    public BluejackGame() {
        gameDeck = new GameDeck();
        computerDeck = new PlayerDeck(drawInitialCards());
        userDeck = new PlayerDeck(drawInitialCards());
        computerScore = 0;
        userScore = 0;
        gameHistory = new String[Max_History_Size];
        historyUserScores = new int[Max_History_Size];
        historyComputerScores = new int[Max_History_Size];
        historyIndex = 0;
        gameCounter = 1;

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


            // Check game end conditions
            if (isGameOver()) {
                displayGameResult();
                break;
            }
        }

    }

    public boolean blueCards(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            if (!"blue".equals(cards[i].getColor())) {
                return false;
            }
        }
        return true;
    }

    private void userTurn(Scanner scanner) {
        System.out.println("\nUser's Turn:");
        Card userDrawnCard = gameDeck.drawCard();

        System.out.print("Do you want to end turn (e),stand (s) or play a card (p)? ");
        char choice = scanner.next().charAt(0);

        if (choice == 'p') {
            playUserCard(scanner);
            computerTurn();

        } else if (choice == 'e') {
            computerTurn();
        } else if (choice == 's') {
            // User stands, add drawn card to the total score
            userScore += userDrawnCard.getValue();
            System.out.println("User draws a card: " + userDrawnCard);
            System.out.println("User's Hand: " + userDeck);
            computerTurn();
        }



        if (userDeck.getTotalScore() == 20 && blueCards(userDeck.getCards())) {
            userScore = 20;
            System.out.println("User's total score is 20 with only blue cards!");
            return;
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


        while (true) {
            System.out.print("Enter the card number: ");
            int cardNumber = scanner.nextInt();

            if (cardNumber >= 1 && cardNumber <= 4) {
                Card selectedCard = userHand[cardNumber - 1];

                userDeck.playCard(cardNumber - 1);
                System.out.println("User plays: " + selectedCard);
                userScore += selectedCard.getValue();
                System.out.println("Remaining cards in your hand: ");
                for (int i = 0; i < 3; i++) {
                    System.out.println((i + 1) + "." + userHand[i + 1]);
                }
                break;

            } else {
                System.out.println("Enter a Valid Number");

            }
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
        if (computerDeck.getTotalScore() == 20 && blueCards(computerDeck.getCards())) {
            computerScore = 20;
            System.out.println("Computer's total score is 20 with only blu cards!");
        }

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
        Scanner sc = new Scanner(System.in);

            int totalUserScore = 0;
            int totalComputerScore = 0;

            try {
                BluejackGame.readGameHistoryFromFile();
            } catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
            }


            while (true) {
                if (totalUserScore == 3 || totalComputerScore == 3) {
                    break;
                }
                BluejackGame bluejackGame = new BluejackGame();
                bluejackGame.startGame();

                if (bluejackGame.userScore > 20 || bluejackGame.computerScore == 20) {
                    totalComputerScore++;
                } else if (bluejackGame.computerScore > 20 || bluejackGame.userScore == 20) {
                    totalUserScore++;
                }
                bluejackGame.addGameRecord("User", totalUserScore, totalComputerScore);


                System.out.println("---Total Score---");
                System.out.println("User: " + totalUserScore);
                System.out.println("Computer: " + totalComputerScore);
            }
    }
    private void addGameRecord(String playerName, int totalUserScore, int totalComputerScore) {
        if (totalUserScore == 3 || totalComputerScore == 3) {

            String record = String.format("Player Score: %d - Computer Score: %d, %s", totalUserScore, totalComputerScore, getCurrentDate());

            if (historyIndex == Max_History_Size) {
                gameHistory[historyIndex % Max_History_Size] = null;
            }

            // Yeni oyun sonucunu ekleyin
            gameHistory[historyIndex % Max_History_Size] = record;
            historyIndex++;

            writeGameRecordToFile(record);
        }
    }
    private void writeGameRecordToFile(String record) {
        try (FileWriter fileWriter = new FileWriter(Game_History_File_Path, true)) {
                fileWriter.write(record);
                fileWriter.write(System.lineSeparator());
                fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }

    }

    private String getCurrentDate() {
        long currentTime = System.currentTimeMillis();
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(currentTime));

    }
    public void printGameRecords(int n) {

        int startIndex = historyIndex - n;
        if(startIndex < 0) {
            startIndex = 0;
        }
        System.out.println("Game Records: ");
        for(int i = startIndex; i < historyIndex; i++) {
            int adjustedIndex = i % Max_History_Size;
            if(gameHistory[adjustedIndex] != null) {
                System.out.println(gameHistory[adjustedIndex]);
            }
        }
    }

    private static void readGameHistoryFromFile() throws IOException {
        try (Scanner scanner = new Scanner(Game_History_File_Path)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }

        }
    }

}