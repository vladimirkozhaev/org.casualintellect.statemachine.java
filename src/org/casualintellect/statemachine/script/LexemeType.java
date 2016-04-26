package org.casualintellect.statemachine.script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LexemeType {
	METHOD("([a-z]|_)(_|[a-z0-9])*"), OPERATOR("(&&|\\|\\||!)"), LEFT_BRACKET(
			"^\\($"), RIGHT_BRACKET("^\\)$"), ATOM_VALUE("^(true|false)$");

	LexemeType(String regexStr) {

		pattern = Pattern.compile(regexStr);

	}

	public boolean match(String matchedString) {
		Matcher m = pattern.matcher(matchedString);
		return m.matches();
	}

	private Pattern pattern;

}
