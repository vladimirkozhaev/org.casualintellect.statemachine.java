package org.casualintellect.statemachine.script.split;

import java.util.List;

import org.casualintellect.statemachine.script.TokenParser;

public class OneCharToken implements ISplitProcessor {

	@Override
	public SymbolType processState(List<String> tokens, final StringBuffer token,
			String charact) {
		tokens.add(token.toString());
		
		token.replace(0, token.length(), "");
		
		
		SymbolType charState = TokenParser.getCharState(charact);

		if (charState!=SymbolType.SPLIT_SYMBOL){
			token.append(charact);
		}
		return charState;
	}

}
