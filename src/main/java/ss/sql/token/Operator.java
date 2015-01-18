package ss.sql.token;

import java.util.*;

/* + - * / */
public class Operator extends Token {
    private static final Set<String> VALUES = new TreeSet<>();
    private static Map<String, Token> map = new TreeMap<>();
    static {
        Collections.addAll(VALUES, ".", "+", "-", "*", "/", "=", "!=", "<", ">", "<=", ">=");
        for (String value : VALUES) {
            map.put(value, new Operator(value));
        }
    }

    private Operator(String value) {
        super(value, TokenType.OPERATOR);
    }

    public static boolean is(char symbol) {
        String value = String.valueOf(symbol);
        return VALUES.contains(value);
    }

    public static boolean is(char curr, char next) {
        String value = String.valueOf(curr) + next;
        return VALUES.contains(value);
    }

    public static Token valueOf(char symbol) {
        String value = String.valueOf(symbol);
        Token token = map.get(value);
        return token == null ? Token.NONE : token;
    }

    public static Token valueOf(char curr, char next) {
        String value = String.valueOf(curr) + next;
        Token token = map.get(value);
        return token == null ? Token.NONE : token;
    }
}