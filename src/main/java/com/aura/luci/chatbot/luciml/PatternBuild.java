/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

import com.aura.lematizador.lematizador.Pair;
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
                        if(palabra.matches("[a-zA-Z0-9]+")){
                            items.add(new PatternTextItem(palabra)); //Sólamente los patterntextitem pasan por lematizacion.
                        } else {
                            if(palabra.length() > 2)
                                items.add(new PatternMultiItem(palabra));
                        }
                    }
                    break;

                case "get":
                    String getVar = currentNode.getAttributes().getNamedItem("name").getNodeValue();
                    items.add(new PatternGetItem(getVar));
                    break;
                case "read":
                    String readVar = currentNode.getAttributes().getNamedItem("name").getNodeValue();
                    String readPattern = currentNode.getTextContent();
                    items.add(new PatternReadItem(readVar, readPattern));
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
