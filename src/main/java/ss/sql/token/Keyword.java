package ss.sql.token;

import java.util.Map;
import java.util.TreeMap;

public class Keyword extends Token {
    private static Map<String, Token> map = new TreeMap<String, Token>();
    private static final String[] VALUES = {"SELECT", "AS", "FROM", "INNER", "OUTER", "LEFT", "RIGHT", "JOIN", "ON",
        "WHERE", "AND", "OR", "BETWEEN", "LIKE", "IN", "ORDER", "BY", "GROUP", "HAVING", "ROWNUM"};

    static {
        for (String value : VALUES) {
            map.put(value, new Keyword(value));
        }
    }

    private Keyword(String value) {
        super(value, TokenType.KEYWORD);
    }

    public static Token valueOf(String value) {
        return map.get(value.toUpperCase());
    }
}