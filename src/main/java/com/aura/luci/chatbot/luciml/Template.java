/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

import com.aura.luci.chatbot.luciml.TemplateGetItem;
import com.aura.luci.chatbot.luciml.TemplateItem;
import com.aura.luci.chatbot.luciml.TemplateTextItem;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;

/**
 *
 * @author diana
 */
public class Template {
    List<TemplateItem> items;
    public Template(Node template){
        items = new ArrayList<>();
        assert(template.getNodeName().equals("template"));
        for(int i = 0; i<template.getChildNodes().getLength() ;i++){
            Node elemento = template.getChildNodes().item(i);
            switch(elemento.getNodeName()){
                case "#text":
                    items.add(new TemplateTextItem(elemento.getNodeValue()));
                    break;
                case "get":
                    items.add(new TemplateGetItem(elemento.getAttributes().getNamedItem("name").getNodeValue()));
            }
        }
    }
	public List<TemplateItem> getItems() {
		return items;
	}
	public void setItems(List<TemplateItem> items) {
		this.items = items;
	}
    
}
