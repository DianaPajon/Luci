/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

import com.aura.luci.chatbot.luciml.PatternItem;

/**
 *
 * @author diana
 */
public class PatternGetItem extends PatternItem{
    String var;

    public PatternGetItem(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
    
}
