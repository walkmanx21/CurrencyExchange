package util;

public final class CheckDecimalSeparator {
    private CheckDecimalSeparator() {
    }

    public static String correction (String number) {
        if (number.contains(",")) {
            number = number.replace(',', '.');
        }
        if (number.contains("%2C")) {
            number = number.replace("%2C", ".");
        }
        return number;
    }
}
