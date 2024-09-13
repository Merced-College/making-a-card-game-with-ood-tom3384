// Group Members: Jeremiah Tenn,
// Tom Nguyen
// 9-13-2024
// OOD Group Project
// added betting system win/lose money, cannot play if has no money
import java.util.Random;
import java.util.Scanner;


public class BlackJack {

  // Creates a deck of 52 cards 
  private static Card[] cards = new Card[52];

  private static int currentCardIndex = 0;
  private static int suitIndex = 0;

  // added starting money 
  private static int money = 50;
  private static int bet = 0;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean turn = true;
    String playerDecision = "" ;
    //added a for loop so the game can be played multiple times
    //for (int i = 0; i < turn; i++) {
    while(turn) {
      initializeDeck();
      shuffleDeck();
      int playerTotal = 0;
      int dealerTotal = 0;
      playerTotal = dealInitialPlayerCards(scanner);
    

      //fix dealInitialDealerCards
      dealerTotal = dealInitialDealerCards();      
      
      //fix playerTurn

      playerTotal = playerTurn(scanner, playerTotal);
      if (playerTotal > 21) {
        System.out.println("You busted! Dealer wins.");
        // System.out.println("You busted! Dealer wins.");
        return;
      }

      //fix dealerTurn
      dealerTotal = dealerTurn(dealerTotal);
      
      // this function now also adds or subtracts the betted amount to the players money
      determineWinner(playerTotal, dealerTotal);
      
      //if player has no money, cannot play more games
      if (money <= 0) {
        System.out.println("You ran out of money :( ");
        turn = false;
        continue;
      }
  
      //asks player if they want to play again
      System.out.println("Would you like to play another hand? Enter 'yes' or 'no'.");
      // Prints out the amount of money remaining. 
      System.out.println("You have $" + money + " remaining.");
     
      playerDecision = scanner.next().toLowerCase();
      
      // Makes sure the player uses valid input
      while(!(playerDecision.equals("no") || (playerDecision.equals("yes")))){
        System.out.println("Invalid action. Please type 'hit' or 'stand'.");
        playerDecision = scanner.next().toLowerCase();
        
        System.out.println();
      }
      
      // if no then stop game and output thanks message
      if (playerDecision.equals("no"))
          turn = false;
    }
    System.out.println("Thanks for playing!");
    // System.out.println("You have $" + money + " remaining.");
  }

  // algorithm to create deck
  private static void initializeDeck() {
    //for (int i = 0; i < DECK.length; i++) {
    String[] SUITS = { "Hearts", "Diamonds", "Clubs","Spades" };
    String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9","10", "Jack", "Queen", "King","Ace" };
    int suitsIndex = 0, rankIndex = 0;
    for (int i = 0; i < cards.length; i++) {
      //DECK[i] = i;
      //public Card(int value, String suit, String rank) {
      int val = 10;
      if(rankIndex < 9)
        val = Integer.parseInt(RANKS[rankIndex]);
      
      // card has three arguements / variables: val, suit, and rank
      cards[i] = new Card( val, SUITS[suitIndex], RANKS[rankIndex]);
      suitIndex++;
      if (suitIndex == 4) {
        suitIndex = 0;
        rankIndex++;
      }
    }
  }
  // algorithm to shuffle deck
  private static void shuffleDeck() {
    Random random = new Random();
    for (int i = 0; i < cards.length; i++) {
      int index = random.nextInt(cards.length);
      Card temp = cards[i];
      cards[i] = cards[index];
      cards[index] = temp;
    }
  }
  // algorithm to deal initial player cards
  // added Scanner scanner so that it can accept player input for how much money to bet 
  private static int dealInitialPlayerCards(Scanner scanner) {
    /*int card1 = dealCard();
    int card2 = dealCard();*/
    Card card1 = dealCard();
    Card card2 = dealCard();
    
    //System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[card1 / 13] + " and " + RANKS[card2] + " of " + SUITS[card2 / 13]);
    System.out.println("Your cards: " + card1.getRank() + " of " + card1.getSuit() + " and " + card2.getRank() + " of " + card2.getSuit());



    // tell the user how much money they have for betting. Bet is global variable
    // /System.out.println("The user starts with $" + money + " to bet with.");
    System.out.println("The user starts with $" + money + " to bet with.");
    //Scans the user input of how much money they want to bet. nextInt so player can input whole number dollar amount. 
    System.out.println("Amount to bet? Please enter a whole number.");
    bet = scanner.nextInt();
              
    //return cardValue(card1) + cardValue(card2);
    return card1.getValue() + card2.getValue();
  }
  // alogrithm to deal initial dealer cards
  private static int dealInitialDealerCards() {
    Card card1 = dealCard();
    
    // Prints out what the dealer's card is. 
    System.out.println("Dealer's card: " + card1);
    return card1.getValue();
  }
  private static int playerTurn(Scanner scanner, int playerTotal) {
    while (true) {


      // Asks the player if they want to hit (draw another card) or stand (stop drawing cards).
      System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
      String action = scanner.next().toLowerCase();
      if (action.equals("hit")) {
        Card newCard = dealCard();
        playerTotal += newCard.getValue();
        // Changed so it doesn't print Card@38ofb434, and so it's more readable by humans. 
        System.out.println("You drew a " + newCard);

        if (playerTotal > 21) {
          //added
          //resets playerTotal so the game can be played multiple times
          System.out.println("You busted!" );
          playerTotal = 0;
          // break; 
          return playerTotal;
          
        }
        // Breaks the loop if the user types 'stand' so that the user stops drawing cards. 
      } else if (action.equals("stand")) {
        break;
      } 
      else {
       // Asks the user to specifically type 'hit' or 'stand', otherwise the program won't proceed. 
        System.out.println("Invalid action. Please type 'hit' or 'stand'.");
        
      }
    }
    return playerTotal;
  }
  // algorithm for dealer's turn
  private static int dealerTurn(int dealerTotal) {
    // Dealer will draw until their total is over 17. Their newly drawn card will be added to their dealerTotal.     
    while (dealerTotal < 17) {
      Card newCard = dealCard();
      dealerTotal += newCard.getValue();
     }
    // Prints out new/current dealerTotal.     
    System.out.println("Dealer's total is " + dealerTotal);
    return dealerTotal;
  }
  // algorithm to determine the winner
  private static void determineWinner(int playerTotal, int dealerTotal) {

    // If player busts, then the playerTotal was set to 0 on line 164, and the player automatically loses and the dealer wins. 
    // "If the player goes bust, they have already lost their wager, even if the dealer goes bust as well." 
    if (playerTotal == 0) {
      System.out.println("Dealer wins!");
      money -= bet;
      playerTotal = 0;
    // If the dealer's total is over 21, or the player's total is more than the dealer's, then it prints out that the player wins. 
    } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
      System.out.println("You win!");;
      // Adds the bet amount to the player's current money total, because the player won. 
      money += bet;
    // If the dealer's total is equal to the player's total, then it's a draw. 
    } else if (dealerTotal == playerTotal) {      
      System.out.println("It's a tie!");
    } else {
      // Otherwise, if none of the above are true (if it's not a draw, or if the player's total is less than the dealer's), then the dealer wins (and it sets the playerTotal equal to 0)
      System.out.println("Dealer wins!");
      // Subtracts the bet amount from the user's current money after the player loses.
      money -= bet;
      playerTotal = 0;
    }
  }
  // algroithm to deal a card
  //private static int dealCard() {
  private static Card dealCard() {
    //return DECK[currentCardIndex++] % 13;
    return cards[currentCardIndex++];
  }
  // algorithm to determine card value
  private static int cardValue(int card) {
    return card < 9 ? card + 2 : 10;
  }
}
