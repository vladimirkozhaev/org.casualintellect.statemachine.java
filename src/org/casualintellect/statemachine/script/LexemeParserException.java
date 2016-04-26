package org.casualintellect.statemachine.script;

public class LexemeParserException extends Exception {

	protected String token;
	public LexemeParserException(String token) {
		super();
		this.token = token;
	}

	public LexemeParserException(String token,String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.token = token;
	}

	public LexemeParserException(String token,String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.token = token;
	}

	public LexemeParserException(String token, String arg0) {
		super(arg0);		
		this.token = token;
	}

	public LexemeParserException(String token,Throwable arg0) {
		super(arg0);		
		this.token = token;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
