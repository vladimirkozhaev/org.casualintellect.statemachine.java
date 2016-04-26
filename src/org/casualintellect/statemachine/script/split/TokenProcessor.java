package org.casualintellect.statemachine.script.split;

import java.util.List;

import org.casualintellect.statemachine.script.TokenParser;

public class TokenProcessor implements ISplitProcessor {

	@Override
	public SymbolType processState(List<String> tokens, final StringBuffer token, String charact) {
		SymbolType charState = TokenParser.getCharState(charact);
		
		if (charState==SymbolType.ONE_CHAR_TOKEN){
			tokens.add(token.toString());
			token.delete(0, token.length());
			token.append(charact);
			return SymbolType.ONE_CHAR_TOKEN;
		}else if (charState==SymbolType.SPLIT_SYMBOL){
			tokens.add(token.toString());
			token.delete(0, token.length());
			return SymbolType.SPLIT_SYMBOL;
		}else{
			token.append(charact);
			return SymbolType.TOKEN;
		}
		
	}

}
