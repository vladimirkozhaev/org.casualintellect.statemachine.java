package org.casualintellect.statemachine.script.split;

import java.util.List;

public interface ISplitProcessor {
	SymbolType processState(final List<String> tokens, final StringBuffer token, final String charact);
}
