/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

import com.aura.lematizador.lematizador.Pair;
import com.aura.luci.chatbot.luciml.Pattern;
import com.aura.luci.chatbot.luciml.PatternItem;
import java.util.List;

/**
 *
 * @author diana
 */
public class TextPattern extends Pattern {
    private List<PatternItem> items;
    
    public TextPattern(List<PatternItem> items){
        this.items = items;
    }
}
