/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.main;


import com.aura.luci.chatbot.engine.Luci;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
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
        
        Luci luci = new Luci(document);
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Hola!, soy luci, ¿cómo te llamás?");
        while(true){
        	String pregunta ;
        	pregunta = scan.nextLine();
        	if("salir".equals(pregunta))
        		break;
        	System.out.println(luci.responder(pregunta));
        }
    }
    
    
    
    
}
