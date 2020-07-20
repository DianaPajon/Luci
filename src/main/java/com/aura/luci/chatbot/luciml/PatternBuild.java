/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;


import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;

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
public abstract class PatternBuild {
            
   
    public static Pattern buildPattern(Node patternNode){

        List<PatternItem> items = new ArrayList<>();

        for(int j = 0; j < patternNode.getChildNodes().getLength();j++){
            Node currentNode = patternNode.getChildNodes().item(j);
            switch(currentNode.getNodeName()){
                case "#text":
                    //TODO: implementar tokenización y decidir subpatrón de regex.
                    String[] strings = currentNode.getNodeValue().replace(",", "").split(" ");
                    for(String palabra : strings){
                        if(palabra.matches("\\*")){
                            items.add(new PatternReadItem(null));
                        } else {
                        	items.add(new PatternTextItem(palabra));
                        }
                    }
                    break;

                case "get":
                    String getVar = currentNode.getAttributes().getNamedItem("name").getNodeValue();
                    items.add(new PatternGetItem(getVar));
                    break;
                case "read":
                    String readVar = currentNode.getAttributes().getNamedItem("name").getNodeValue();
                    items.add(new PatternReadItem(readVar));
                    break;
                case "event":
                    return new EventPattern(currentNode.getAttributes().getNamedItem("name").getTextContent(), PatternBuild.buildPattern(currentNode));
                default:
                    break;
            }

        }
        
        return new TextPattern(items);
        
    }
}
