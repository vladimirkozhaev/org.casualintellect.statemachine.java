package org.casualintellect.statemachine.script;

/**
 * 
 * @author Voffka
 *
 */
public class Lexeme {
	public Lexeme(LexemeType lexemeType, String lexemeString) {
		super();
		this.lexemeType = lexemeType;
		this.lexemeString = lexemeString;
		this.priority = calculatePriority(lexemeType, lexemeString);
	}
	
	public Lexeme (Lexeme lexeme){
		this.lexemeType=lexeme.getLexemeType();
		this.lexemeType=lexeme.getLexemeType();
		this.priority=lexeme.getPriority();
	}

	private int calculatePriority(LexemeType lexemeType, String lexemeString) {
		switch (lexemeType) {
		case OPERATOR:
			if (lexemeType.equals("!")) {
				return 5;
			}
			if (lexemeString.equals("||")) {
				return 4;
			}
			if (lexemeString.equals("&&")) {
				return 3;
			}
		case LEFT_BRACKET:
			return 2;
		case RIGHT_BRACKET:
			return 1;
		default:
			return 0;

		}
	}

	private LexemeType lexemeType;
	private String lexemeString;
	private int priority;

	public LexemeType getLexemeType() {
		return lexemeType;
	}

	public void setLexemeType(LexemeType lexemeType) {
		this.lexemeType = lexemeType;
	}

	public String getLexemeString() {
		return lexemeString;
	}

	public void setLexemeString(String lexemeString) {
		this.lexemeString = lexemeString;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		return lexemeString;
	}
	
	
}
