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
public class EventPattern  extends Pattern {
    private String event;
    private Pattern innerPattern;
    
    public EventPattern(String event, Pattern innerPattern){
        this.event = event;
        this.innerPattern = innerPattern;
    }
}
