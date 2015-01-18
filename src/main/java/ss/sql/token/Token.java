package ss.sql.token;

public class Token {
    public static final Token NONE = new Token("", TokenType.NONE);

    private final TokenType type;
    private final String value;

    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }
    public TokenType getType() {
        return type;
    }
}