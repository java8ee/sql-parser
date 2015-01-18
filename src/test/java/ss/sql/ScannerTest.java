package ss.sql;

import org.junit.Assert;
import org.junit.Test;

import ss.sql.token.Token;
import ss.sql.token.TokenType;

public class ScannerTest {
    @Test
    public void testSimpleSelect() {
        Scanner scanner = new Scanner("SELECT * FROM DUAL");
        assertToken(scanner, "SELECT", TokenType.KEYWORD);
        assertToken(scanner, "*", TokenType.OPERATOR);
        assertToken(scanner, "FROM", TokenType.KEYWORD);
        assertToken(scanner, "DUAL", TokenType.ID);
        assertToken(scanner, "", TokenType.NONE);
        assertToken(scanner, "", TokenType.NONE);
        assertToken(scanner, "", TokenType.NONE);
    }

    @Test
    public void testComplexSelect() {
        Scanner scanner = new Scanner("select/*Comment*/FROM Table");
        assertToken(scanner, "SELECT", TokenType.KEYWORD);
        assertToken(scanner, "Comment", TokenType.COMMENT);
        assertToken(scanner, "FROM", TokenType.KEYWORD);
        assertToken(scanner, "TABLE", TokenType.ID);
        assertToken(scanner, "", TokenType.NONE);
        assertToken(scanner, "", TokenType.NONE);
    }

    @Test
    public void testExpression() {
        Scanner scanner = new Scanner("  \t \r \n   SELECT /* FIRST_ROWS(50) */ id as \"My \"\"ID\"\"\" , name FROM schema.Person" +
                " WHERE current_salary <= 1000 AND (age >= 18 or name like '%th%'\r)  \t\n");
        assertExpression(scanner);
    }

    @Test
    public void testSolidExpression() {
        Scanner scanner = new Scanner("SELECT/* FIRST_ROWS(50) */id as\"My \"\"ID\"\"\",name FROM schema.Person" +
                " WHERE current_salary<=1000 AND(age>=18 or name like'%th%')");
        assertExpression(scanner);
    }

    private void assertExpression(Scanner scanner) {
        assertToken(scanner, "SELECT", TokenType.KEYWORD);
        assertToken(scanner, " FIRST_ROWS(50) ", TokenType.COMMENT);
        assertToken(scanner, "ID", TokenType.ID);
        assertToken(scanner, "AS", TokenType.KEYWORD);
        assertToken(scanner, "My \"ID\"", TokenType.ALIAS);
        assertToken(scanner, ",", TokenType.DELIMITER);
        assertToken(scanner, "NAME", TokenType.ID);
        assertToken(scanner, "FROM", TokenType.KEYWORD);
        assertToken(scanner, "SCHEMA", TokenType.ID);
        assertToken(scanner, ".", TokenType.OPERATOR);
        assertToken(scanner, "PERSON", TokenType.ID);
        assertToken(scanner, "WHERE", TokenType.KEYWORD);
        assertToken(scanner, "CURRENT_SALARY", TokenType.ID);
        assertToken(scanner, "<=", TokenType.OPERATOR);
        assertToken(scanner, "1000", TokenType.NUMBER);
        assertToken(scanner, "AND", TokenType.KEYWORD);
        assertToken(scanner, "(", TokenType.DELIMITER);
        assertToken(scanner, "AGE", TokenType.ID);
        assertToken(scanner, ">=", TokenType.OPERATOR);
        assertToken(scanner, "18", TokenType.NUMBER);
        assertToken(scanner, "OR", TokenType.KEYWORD);
        assertToken(scanner, "NAME", TokenType.ID);
        assertToken(scanner, "LIKE", TokenType.KEYWORD);
        assertToken(scanner, "%th%", TokenType.STRING);
        assertToken(scanner, ")", TokenType.DELIMITER);
        assertToken(scanner, "", TokenType.NONE);
        assertToken(scanner, "", TokenType.NONE);
    }

    private void assertToken(Scanner scanner, String value, TokenType type) {
        Token token = scanner.getToken();
        Assert.assertEquals(value, token.getValue());
        Assert.assertEquals(type, token.getType());
    }
}