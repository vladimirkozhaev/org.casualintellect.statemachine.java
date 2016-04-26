package org.casualintellect.statemachine.script.split;

import java.util.List;

import org.casualintellect.statemachine.script.TokenParser;

public class SpaceProcessor implements ISplitProcessor {

	@Override
	public SymbolType processState(List<String> tokens, final StringBuffer token, String charact) {
		SymbolType charState = TokenParser.getCharState(charact);
		switch (charState) {
		case ONE_CHAR_TOKEN:
		case TOKEN:
			token.append(charact);
			break;
		default:
			break;
		}
		
		return charState;

	}

}
