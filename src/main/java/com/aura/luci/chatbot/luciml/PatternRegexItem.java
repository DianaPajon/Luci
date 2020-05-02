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
public class PatternRegexItem extends PatternItem {
    String regex;
    public PatternRegexItem(String regex){
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
    
}
