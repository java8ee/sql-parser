package ss.sql.token;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Blank extends Token {
    private static Set<String> values = new TreeSet<>();

    static {
        Collections.addAll(values, " ", "\t", "\n", "\r");
    }

    private Blank() {
        super("", TokenType.NONE);
    }

    public static boolean is(char value) {
        return values.contains(String.valueOf(value));
    }
}