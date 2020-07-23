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
public class Pattern {
    private List<PatternItem> items;
    
    public Pattern(List<PatternItem> items){
        this.items = items;
    }

    public List<PatternItem> getItems() {
        return items;
    }

    public void setItems(List<PatternItem> items) {
        this.items = items;
    }
    
    
}
