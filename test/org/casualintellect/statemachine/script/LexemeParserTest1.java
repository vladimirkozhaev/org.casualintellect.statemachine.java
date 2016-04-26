package org.casualintellect.statemachine.script;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.casualintellect.statemachine.script.Lexeme;
import org.casualintellect.statemachine.script.LexemeParserException;
import org.casualintellect.statemachine.script.LexemeType;
import org.casualintellect.statemachine.script.TokenParser;
import org.junit.Test;

public class LexemeParserTest1 {

	@Test
	public void testParseLexemeText() {
		List<String> parseLexemeText = TokenParser.getInstance().parseTokens(
				"a and b or c");
		assertArrayEquals(new int[] { 5 }, new int[] { parseLexemeText.size() });
		assertArrayEquals(new String[] { "a", "and", "b", "or", "c" },
				parseLexemeText.toArray());
		parseLexemeText = TokenParser.getInstance().parseTokens(
				"a and b ( or c 12 3");

		assertArrayEquals(new String[] { "a", "and", "b", "(", "or", "c", "12",
				"3" }, parseLexemeText.toArray());
		parseLexemeText = TokenParser.getInstance().parseTokens(
				"a and b (or c 12 3");
		assertArrayEquals(new String[] { "a", "and", "b", "(", "or", "c", "12",
				"3" }, parseLexemeText.toArray());
		parseLexemeText = TokenParser.getInstance().parseTokens(
				"a and b(or c 12 3");
		assertArrayEquals(new String[] { "a", "and", "b", "(", "or", "c", "12",
				"3" }, parseLexemeText.toArray());
		parseLexemeText = TokenParser.getInstance().parseTokens(
				"a and b( or c 12 3");
		assertArrayEquals(new String[] { "a", "and", "b", "(", "or", "c", "12",
				"3" }, parseLexemeText.toArray());
		parseLexemeText = TokenParser.getInstance().parseTokens(
				"a and b(or c 12 3");
		assertArrayEquals(new String[] { "a", "and", "b", "(", "or", "c", "12",
				"3" }, parseLexemeText.toArray());
	}

	@Test
	public void textCheckLexemeType() {
		assertTrue(LexemeType.METHOD.match("a123"));
		assertFalse(LexemeType.METHOD.match(""));
		assertFalse(LexemeType.METHOD.match("1salo"));
		assertTrue(LexemeType.METHOD.match("_salo"));
		assertTrue(LexemeType.OPERATOR.match("&&"));
		assertFalse(LexemeType.OPERATOR.match("&"));
		assertTrue(LexemeType.OPERATOR.match("||"));
		assertTrue(LexemeType.OPERATOR.match("!"));
	}

	@Test
	public void testParseLexemes() throws LexemeParserException {
		List<Lexeme> parseTokens = TokenParser.getInstance().parseLexemes(
				"aaa && b(|| c a12 b3");
		assertArrayEquals(new int[] { 8 }, new int[] { parseTokens.size() });

	}

	@Test
	public void testParseWithBrackets() {
		List<String> parseLexemeText = TokenParser.getInstance().parseTokens(
				"a and (b or c)");
		assertArrayEquals(new int[] { 7 }, new int[] { parseLexemeText.size() });
	}

	@Test
	public void testCheckIncorrectSymbol() {
		assertArrayEquals(
				new int[] { -1 },
				new int[] { TokenParser.checkTestOnNotTerminal("aZ()\t\n\r!()") });
	}

	@Test
	public void testBackPolishNotation() throws LexemeParserException {
		List<Lexeme> parseLexemes = TokenParser.getInstance()
				.parseStrToBackPolish("a && b");
		assertArrayEquals(new int[] { 3 }, new int[] { parseLexemes.size() });
		assertArrayEquals(new String[] { "&&", "b", "a" }, new String[] {
				parseLexemes.get(0).getLexemeString(),
				parseLexemes.get(1).getLexemeString(),
				parseLexemes.get(2).getLexemeString() });

		parseLexemes = TokenParser.getInstance()
				.parseStrToBackPolish("!(a && b)");
		assertArrayEquals(new int[] { 4 }, new int[] { parseLexemes.size() });
		assertArrayEquals(new String[] { "!","&&", "b", "a" }, new String[] {
				parseLexemes.get(0).getLexemeString(),
				parseLexemes.get(1).getLexemeString(),
				parseLexemes.get(2).getLexemeString(),
				parseLexemes.get(3).getLexemeString()
				});
		
		parseLexemes = TokenParser.getInstance()
				.parseStrToBackPolish("!(a && !b)");
		assertArrayEquals(new int[] { 5 }, new int[] { parseLexemes.size() });
		assertArrayEquals(new String[] { "!","&&", "!","b", "a" }, new String[] {
				parseLexemes.get(0).getLexemeString(),
				parseLexemes.get(1).getLexemeString(),
				parseLexemes.get(2).getLexemeString(),
				parseLexemes.get(3).getLexemeString(),
				parseLexemes.get(4).getLexemeString()
		});
	}

}
