package org.casualintellect.statemachine.script;

import static org.casualintellect.statemachine.script.split.SymbolType.ONE_CHAR_TOKEN;
import static org.casualintellect.statemachine.script.split.SymbolType.SPLIT_SYMBOL;
import static org.casualintellect.statemachine.script.split.SymbolType.TOKEN;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.casualintellect.statemachine.script.split.ISplitProcessor;
import org.casualintellect.statemachine.script.split.OneCharToken;
import org.casualintellect.statemachine.script.split.SpaceProcessor;
import org.casualintellect.statemachine.script.split.SymbolType;
import org.casualintellect.statemachine.script.split.TokenProcessor;

/**
 * 
 * @author Voffka
 *
 */
public final class TokenParser {

	private static final Set<String> ONE_CHAR_TOKEN_SYMBOLS = new HashSet<String>(
			Arrays.asList("(", ")", ";", "!"));
	private static final Set<String> SPLIT_SYMBOLS = new HashSet<String>(
			Arrays.asList(" ", "\\n", "\\r", "\\t"));

	private static final Map<SymbolType, ISplitProcessor> SPLIT_PROCESSORS = new HashMap<SymbolType, ISplitProcessor>();

	private TokenParser() {
		SPLIT_PROCESSORS.put(ONE_CHAR_TOKEN, new OneCharToken());
		SPLIT_PROCESSORS.put(SPLIT_SYMBOL, new SpaceProcessor());
		SPLIT_PROCESSORS.put(TOKEN, new TokenProcessor());
	}

	private static final TokenParser _instance = new TokenParser();

	public static final TokenParser getInstance() {
		return _instance;
	}

	/**
	 * Method split text of the program on lexemes
	 * 
	 * @param text
	 * @return list with lexemes
	 */
	public List<String> parseTokens(String text) {

		String[] split = text.split("");
		List<String> symbols = new LinkedList<String>(Arrays.asList(split));

		List<String> tokens = new LinkedList<String>();

		if (text.length() == 0) {
			return tokens;
		}

		if (text.length() == 1 && getCharState(text) != SPLIT_SYMBOL) {
			return tokens;
		}
		StringBuffer token = new StringBuffer();
		String firstChar = symbols.get(0);
		SymbolType prevType = getCharState(firstChar);

		if (prevType != SPLIT_SYMBOL) {
			token.append(firstChar);
		}
		ISplitProcessor iSplitProcessor = null;
		for (int i = 1; i < symbols.size(); i++) {
			iSplitProcessor = SPLIT_PROCESSORS.get(prevType);
			prevType = iSplitProcessor.processState(tokens, token,
					symbols.get(i));
		}

		iSplitProcessor = SPLIT_PROCESSORS.get(prevType);
		iSplitProcessor.processState(tokens, token, " ");

		return tokens;

	}

	protected List<Lexeme> toBackPolishNotation(List<Lexeme> lexemes)
			throws LexemeParserException {
		List<Lexeme> backPolishNotation = new LinkedList<Lexeme>();
		Stack<Lexeme> operations = new Stack<Lexeme>();
		for (final Lexeme lexeme : lexemes) {
			switch (lexeme.getLexemeType()) {
			case ATOM_VALUE:
				break;
			case METHOD:
				backPolishNotation.add(lexeme);
				break;
			case OPERATOR:

				while (operations.size() > 0
						&& operations.firstElement().getPriority() > lexeme
								.getPriority()) {
					backPolishNotation.add(operations.pop());

				}
				operations.push(lexeme);

				break;
			case LEFT_BRACKET:
				operations.add(lexeme);
				break;
			case RIGHT_BRACKET:

				while (operations.size() > 0
						&& operations.lastElement().getLexemeType() != LexemeType.LEFT_BRACKET) {
					backPolishNotation.add(operations.pop());
				}

				if (operations.size() == 0
						|| operations.lastElement().getLexemeType() != LexemeType.LEFT_BRACKET) {
					throw new LexemeParserException(")",
							"There aren't left bracket");
				}

				operations.pop();

				break;

			}
		}
		if (operations.size() > 0) {
			backPolishNotation.add(operations.pop());
		}
		Collections.reverse(backPolishNotation);
		return backPolishNotation;
	}

	public List<Lexeme> parseStrToBackPolish(String program)
			throws LexemeParserException {
		return toBackPolishNotation(parseLexemes(program));
	}

	public List<Lexeme> parseLexemes(String program)
			throws LexemeParserException {
		List<Lexeme> lexemes = new LinkedList<Lexeme>();
		List<String> parseTokens = parseTokens(program);
		for (String token : parseTokens) {
			LexemeType[] values = LexemeType.values();
			Lexeme lexeme = null;
			for (LexemeType lexemeType : values) {
				if (lexemeType.match(token)) {
					lexeme = new Lexeme(lexemeType, token);
					break;
				}
			}
			if (lexeme == null) {
				throw new LexemeParserException(token);
			}

			lexemes.add(lexeme);
		}
		return lexemes;
	}

	public static LexemeType getLexemeType(String token) {
		LexemeType[] values = LexemeType.values();
		for (LexemeType lexemeType : values) {
			if (lexemeType.match(token)) {
				return lexemeType;
			}
		}

		return null;
	}

	public static SymbolType getCharState(final String charact) {
		if (SPLIT_SYMBOLS.contains(charact))
			return SymbolType.SPLIT_SYMBOL;
		if (ONE_CHAR_TOKEN_SYMBOLS.contains(charact))
			return SymbolType.ONE_CHAR_TOKEN;
		return SymbolType.TOKEN;
	}

	/**
	 * Method check program on non
	 * 
	 * @param text
	 * @return
	 */
	protected static int checkTestOnNotTerminal(final String text) {
		String[] split = text.split("");
		Pattern p = Pattern.compile("^[a-zA-Z0-9_\t\n\r()!]$");
		for (int i = 0; i < split.length; i++) {
			final String symbol = split[i];
			Matcher m = p.matcher(symbol);
			if (!m.matches()) {
				return i;
			}
		}
		return -1;
	}
}
