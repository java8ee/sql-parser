package ss.sql.token;

public class Comment extends Token {
    public Comment(String value) {
        super(value, TokenType.COMMENT);
    }
}