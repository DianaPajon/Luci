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
public class PatternTextItem extends PatternItem{
    String word;
    
    public PatternTextItem(String word){
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    
}
