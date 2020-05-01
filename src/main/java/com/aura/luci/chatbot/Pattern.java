/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot;

import com.aura.lematizador.lematizador.Pair;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author diana
 * 
 * Son esencialmente UNA EXPRESIÓN REGULAR. Tienen set y get.
 * 
 * También hay patterns de EVENTOS, que pueden tener una expresión regular en ellos.
 * 
 * Funcionan así:
 * -Se procesan los set y get.
 * -Se junta todo en una regex.
 * -Se procesa la regex.
 */
public abstract class Pattern {
            
    
    
    public static Pattern buildPattern(Node patternNode){

        List<Pair<String, String>> listOfSets = new ArrayList<>();
        List<Pair<String,String>> listOfReads = new ArrayList<>();
        String patternText = "";

        for(int j = 0; j < patternNode.getChildNodes().getLength();j++){
            Node currentNode = patternNode.getChildNodes().item(j);
            switch(currentNode.getNodeName()){
                case "#text":
                    patternText = patternText + currentNode.getTextContent();
                    break;
                case "set":
                    String setVar = currentNode.getAttributes().getNamedItem("name").getTextContent();
                    String setValue = currentNode.getTextContent();
                    listOfSets.add(new Pair(setVar, setValue));
                    break;
                case "read":
                    String readVar = currentNode.getAttributes().getNamedItem("name").getTextContent();
                    String readValue = currentNode.getTextContent();
                    listOfReads.add(new Pair(readVar, readValue));
                    patternText = patternText + "(" + currentNode.getTextContent() + ")";
                    break;
                case "event":
                    return new EventPattern(currentNode.getAttributes().getNamedItem("name").getTextContent(), Pattern.buildPattern(currentNode));
                default:
                    break;
            }

        }
        
        return new TextPattern(listOfSets, listOfReads, patternText);
        
    }
}
