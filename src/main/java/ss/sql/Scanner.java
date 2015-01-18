package ss.sql;

import ss.sql.token.*;

public class Scanner {
    private final String sql;
    private int pos;

    public Scanner(String sql) {
        this.sql = sql;
    }

    public Token getToken() {
        StringBuilder buf = new StringBuilder();
        TokenType state = TokenType.NONE;
        char curr;
        char next = pos >= sql.length() ? 0 : sql.charAt(pos);
        for (; pos < sql.length(); pos++) {
            curr = next;
            next = pos == sql.length() - 1 ? 0 : sql.charAt(pos + 1);

            switch (state) {
                case NONE:
                    if (Character.isLetter(curr) || curr == '_') {
                        state = TokenType.ID;
                        buf.append(curr);
                    } else if (Character.isDigit(curr)) {
                        state = TokenType.NUMBER;
                        buf.append(curr);
                    } else if (curr == '"') {
                        state = TokenType.ALIAS;
                    } else if (curr == '\'') {
                        state = TokenType.STRING;
                    } else if (curr == '/' && next == '*') {
                        state = TokenType.COMMENT;
                        pos++;
                        next = pos == sql.length() - 1 ? 0 : sql.charAt(pos + 1);
                    } else if (Operator.is(curr, next)) {
                        pos+=2;
                        return Operator.valueOf(curr, next);
                    } else if (Operator.is(curr)) {
                        pos++;
                        return Operator.valueOf(curr);
                    } else if (Delimiter.is(curr)) {
                        pos++;
                        return Delimiter.valueOf(curr);
                    }
                    break;
                case ID:
                    if (Character.isLetterOrDigit(curr) || curr == '_') {
                        buf.append(curr);
                    } else {
                        Token token = Keyword.valueOf(buf.toString());
                        return token == null ? Identifier.valueOf(buf.toString()) : token;
                    }
                    break;
                case NUMBER:
                    if (Character.isDigit(curr)) {
                        buf.append(curr);
                    } else if (Blank.is(curr)) {
                        return new Token(buf.toString(), TokenType.NUMBER);
                    } else {
                        throw new RuntimeException("Unknown char '" + curr + "' after number " + buf);
                    }
                    break;
                case ALIAS:
                    if (curr == '"') {
                        pos++;
                        if (next == '"') {
                            next = pos >= sql.length() ? 0 : sql.charAt(pos + 1);
                            buf.append(curr);
                        } else {
                            return new Token(buf.toString(), TokenType.ALIAS);
                        }
                    } else {
                        buf.append(curr);
                    }
                    break;
                case STRING:
                    if (curr == '\'') {
                        pos++;
                        if (next == '\'') {
                            next = pos >= sql.length() ? 0 : sql.charAt(pos + 1);
                            buf.append(curr);
                        } else {
                            return new Token(buf.toString(), TokenType.STRING);
                        }
                    } else {
                        buf.append(curr);
                    }
                    break;
                case COMMENT:
                    if (curr == '*' && next == '/') {
                        pos+=2;
                        return new Comment(buf.toString());
                    } else {
                        buf.append(curr);
                    }
                    break;
                default:
                    throw new RuntimeException("Incorrect state " + state);
            }
        }

        Token token;
        switch (state) {
            case ID:
                token = Keyword.valueOf(buf.toString());
                if (token == null) {
                    token = Identifier.valueOf(buf.toString());
                }
                break;
            default:
                token = new Token(buf.toString(), state);
        }
        return token;
    }
}