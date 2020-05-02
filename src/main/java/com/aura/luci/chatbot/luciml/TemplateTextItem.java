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
public class TemplateTextItem extends TemplateItem{
    String text;
    
    public TemplateTextItem(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
