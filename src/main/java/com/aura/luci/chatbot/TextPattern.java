/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot;

import com.aura.lematizador.lematizador.Pair;
import java.util.List;

/**
 *
 * @author diana
 */
public class TextPattern extends Pattern {
    private List<Pair<String, String>> sets;
    private List<Pair<String, String>> reads;
    private String text;
    
    public TextPattern(List<Pair<String, String>> sets,List<Pair<String, String>> reads,String text){
        this.sets = sets;
        this.reads = reads;
        this.text = text;
    }
}
