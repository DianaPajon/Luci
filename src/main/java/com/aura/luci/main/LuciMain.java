/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.main;

import com.aura.lematizador.lematizador.Pair;
import com.aura.luci.chatbot.luciml.Category;
import com.aura.luci.chatbot.luciml.Pattern;
import com.aura.luci.chatbot.luciml.Template;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class LuciMain {
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException{
        File cfgFile = new File("resources/luci.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document document = (Document) dBuilder.parse(cfgFile);
                
        //Parseo por estructura:
        
        NodeList lucis = document.getElementsByTagName("luciml");
        
        Node luci = lucis.item(0);
        NodeList hijosDeLuci = luci.getChildNodes();
        
        List<Category> categorias = new ArrayList<>();
        for(int i = 0 ; i < hijosDeLuci.getLength() ; i++ ){
            Node hijo = hijosDeLuci.item(i);
            switch(hijo.getNodeName()){
                case "lematizador" :
                    break;
                case "category" :
                    Category categoria = new Category();
                    Node pre = hijo.getAttributes().getNamedItem("pre");
                    if(pre != null){
                        categoria.setPre(Arrays.asList(pre.getTextContent().split(",")));
                    } else {
                        categoria.setPre(null);
                    }
                    Pattern p = null;
                    Template t = null;
                    for(int j = 0; j < hijo.getChildNodes().getLength();j++){
                        Node nieto = hijo.getChildNodes().item(j);
                        switch(nieto.getNodeName()){
                            case "pattern":
                                p = Pattern.buildPattern(nieto);
                                break;
                            case "template":
                                t = new Template(nieto);
                                break;
                        }
                    }
                    categoria.setPatron(p);
                    categoria.setTemplate(t);
                    categorias.add(categoria);
                    break;
                default:
                    break;
            }
            
        }
        System.out.println(categorias);
        
    }
    
    
    
    
}
