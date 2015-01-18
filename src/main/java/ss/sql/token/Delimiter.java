package ss.sql.token;

import java.util.*;

public class Delimiter extends Token {
    private static Set<String> values = new TreeSet<>();
    private static Map<String, Token> map = new TreeMap<>();

    static {
        Collections.addAll(values, "(", ")", ",");
        for (String value : values) {
            map.put(value, new Delimiter(value));
        }
    }

    private Delimiter(String value) {
        super(value, TokenType.DELIMITER);
    }

    public static boolean is(char value) {
        return values.contains(String.valueOf(value));
    }

    public static Token valueOf(char value) {
        Token token = map.get(String.valueOf(value));
        return token == null ? Token.NONE : token;
    }
}