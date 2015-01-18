package ss.sql.token;

import java.util.Map;
import java.util.TreeMap;

public class Identifier extends Token {
    private static Map<String, Token> map = new TreeMap<String, Token>();

    private Identifier(String value) {
        super(value, TokenType.ID);
    }

    public static Token valueOf(String value) {
        String id = value.toUpperCase();
        Token token = map.get(id);
        if (token == null) {
            token = new Identifier(id);
            map.put(id, token);
        }
        return token;
    }
}