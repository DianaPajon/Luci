/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

/**
 *
 * @author diana
 */
public class EventPattern  implements Pattern {
    private String event;
    private Pattern innerPattern;
    
    public EventPattern(String event, Pattern innerPattern){
        this.event = event;
        this.innerPattern = innerPattern;
    }

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Pattern getInnerPattern() {
		return innerPattern;
	}

	public void setInnerPattern(Pattern innerPattern) {
		this.innerPattern = innerPattern;
	}
    
    
}
