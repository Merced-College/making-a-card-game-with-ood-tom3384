public class Card {
    // declare private variables 
    private int value;
    private String suit; 
    private String rank; 

    // Default contructor with three arguments 
    public Card(int value, String suit, String rank) {
        this.value = value;
        this.suit = suit; 
        this.rank = rank; 
    }
    
    // return value
    public int getValue() {
        return value;
    }

    // return suit
    public String getSuit() {
        return suit;
    }

    // returns rank
    public String getRank() {
        return rank; 
    }

    // return string when class is printed
    public String toString() {
        return suit + " of " + rank;
    }
}
