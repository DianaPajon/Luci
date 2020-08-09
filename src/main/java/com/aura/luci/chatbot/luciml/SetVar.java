package com.aura.luci.chatbot.luciml;

public class SetVar {
	private String variable;
	private String valor;
	
	
	
	public SetVar(String variable, String valor) {
		super();
		this.variable = variable;
		this.valor = valor;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	 
}
