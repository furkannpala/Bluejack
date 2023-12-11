public class Card {
    private String color;
    private int value;
    private String sign;

    public Card(String color, int value, String sign) {
        this.color = color;
        this.value = value;
        this.sign = sign;
    }

    public int getValue() {
        if (sign.equals("+")) {
            return value;
        } else if (sign.equals("-")) {
            return -value;
        } else {
            return value;
        }
    }

    public String getColor() {
        return color;
    }


    public String toString() {
        return color + " " + sign + value ;
    }
}
