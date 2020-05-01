/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.main;

import com.aura.lematizador.lematizador.Pair;
import com.aura.luci.chatbot.Pattern;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author diana
 */
public class Luci {
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException{
        File cfgFile = new File("resources/luci.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document document = (Document) dBuilder.parse(cfgFile);
                


        
        NodeList patternNodes = document.getElementsByTagName("pattern");
        List<Pattern> patternList = new ArrayList<>();
        for(int i = 0; i < patternNodes.getLength();i ++ ){
            Node patternNode = patternNodes.item(i);
            patternList.add(Pattern.buildPattern(patternNode));
        }
        
        String breakPoint = patternList.toString();
                
    }
    
    
    
    
}
