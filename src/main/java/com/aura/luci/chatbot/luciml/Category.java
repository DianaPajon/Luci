/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author diana
 */
public class Category {
    Pattern patron;
    Template template;
    Map<String, String> pre;
    
    public Pattern getPatron() {
        return patron;
    }

    public void setPatron(Pattern patron) {
        this.patron = patron;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

	public Map<String, String> getPre() {
		return pre;
	}

	public void setPre(Map<String, String> pre) {
		this.pre = pre;
	}

    
    
}
