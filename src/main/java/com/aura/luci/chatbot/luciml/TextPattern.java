/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

import java.util.List;

/**
 *
 * @author diana
 */
public class TextPattern implements Pattern{
    private List<PatternItem> items;
    
    public TextPattern(List<PatternItem> items){
        this.items = items;
    }
}
