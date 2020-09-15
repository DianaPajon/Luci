/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.luciml;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author diana
 */
public class Category {
    List<Pattern> patrones;
    Template template;
    List<Precondition> preconditions;
    List<SetVar> setVars;


    public List<Pattern> getPatrones() {
		return patrones;
	}

	public void setPatrones(List<Pattern> patrones) {
		this.patrones = patrones;
	}

	public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

	public List<Precondition> getPreconditions() {
		return preconditions;
	}

	public void setPreconditions(List<Precondition> preconditions) {
		this.preconditions = preconditions;
	}

	public List<SetVar> getSetVars() {
		return setVars;
	}

	public void setSetVars(List<SetVar> setVars) {
		this.setVars = setVars;
	}
    
    
}
