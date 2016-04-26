package org.casualintelllect.statemachine.calc;

import java.util.List;

import org.casualintellect.statemachine.script.Lexeme;
import org.casualintellect.statemachine.script.LexemeParserException;
import org.casualintellect.statemachine.script.TokenParser;
import org.junit.Test;

public class CalcPolishNotationTest {
	@Test
	public void testPolishNotationCalc() throws LexemeParserException{
		List<Lexeme> parseStrToBackPolish = TokenParser.getInstance().parseStrToBackPolish("!(a && !b)");
		
	}
}
