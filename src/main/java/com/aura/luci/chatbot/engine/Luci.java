/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.engine;

import com.aura.lematizador.lematizador.Lematizador;
import com.aura.lematizador.lematizador.Pair;
import com.aura.luci.chatbot.luciml.Category;
import com.aura.luci.chatbot.luciml.Pattern;
import com.aura.luci.chatbot.luciml.PatternBuild;
import com.aura.luci.chatbot.luciml.PatternItem;
import com.aura.luci.chatbot.luciml.PatternReadItem;
import com.aura.luci.chatbot.luciml.PatternTextItem;
import com.aura.luci.chatbot.luciml.Precondition;
import com.aura.luci.chatbot.luciml.SetVar;
import com.aura.luci.chatbot.luciml.Template;
import com.aura.luci.chatbot.luciml.TemplateGetItem;
import com.aura.luci.chatbot.luciml.TemplateItem;
import com.aura.luci.chatbot.luciml.TemplateTextItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    private Lematizador lematizador;
    private List<Category> categorias;
    private Map<String, String> estado;
    
    public Luci(Document luciml) throws SAXException, ParserConfigurationException, IOException{
        NodeList lucis = luciml.getElementsByTagName("luciml");
        
        Node luci = lucis.item(0);
        NodeList hijosDeLuci = luci.getChildNodes();
        List<Category> categorias = new ArrayList<>();
        for(int i = 0 ; i < hijosDeLuci.getLength() ; i++ ){
            Node hijo = hijosDeLuci.item(i);
            switch(hijo.getNodeName()){
                case "lematizador" :
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    File dictFile = new File(hijo.getAttributes().getNamedItem("diccionario").getNodeValue());
                    Document dictDocument = (Document) dBuilder.parse(dictFile);
                    lematizador = new Lematizador(dictDocument);
                    break;
                case "category" :
                    Category categoria = new Category();
                    Pattern p = null;
                    Template t = null;
                    List<Precondition> precondiciones = new ArrayList<Precondition>();
                    List<SetVar> sets = new ArrayList<SetVar>();
                    for(int j = 0; j < hijo.getChildNodes().getLength();j++){
                        Node nieto = hijo.getChildNodes().item(j);
                        switch(nieto.getNodeName()){
                            case "pattern":
                                p = PatternBuild.buildPattern(nieto);
                                break;
                            case "template":
                                t = new Template(nieto);
                                break;
                            case "precondition":
                            	for(int k = 0; k < nieto.getChildNodes().getLength(); k++) {
                            		Node item = nieto.getChildNodes().item(k);
                            		if("pi".equals(item.getNodeName())) {
	                            		String var = item.getAttributes().getNamedItem("var").getNodeValue();
	                            		String val = item.getAttributes().getNamedItem("val").getNodeValue();
	                            		precondiciones.add(new Precondition(var,val));
                            		}
                            	}
                            case "setvars":
                            	for(int k = 0; k < nieto.getChildNodes().getLength(); k++) {
                            		Node item = nieto.getChildNodes().item(k);
                            		if("set".equals(item.getNodeName())) {
	                            		String var = item.getAttributes().getNamedItem("var").getNodeValue();
	                            		String val = item.getAttributes().getNamedItem("val").getNodeValue();
	                            		sets.add(new SetVar(var,val));
                            		}
                            	}
                            
                        	default:
                            		break;
                        }
                    }
                    categoria.setPatron(p);
                    categoria.setTemplate(t);
                    categoria.setSetVars(sets);
                    categoria.setPreconditions(precondiciones);
                    categorias.add(categoria);
                    break;
                default:
                    break;
            }
            
        }

        this.estado = new HashMap<String, String>();
        this.estado.put("inicio", "si");
        this.categorias = new ArrayList<Category>(categorias);
    }
    
    
    
    public Lematizador getLematizador() {
        return lematizador;
    }

    public void setLematizador(Lematizador lematizador) {
        this.lematizador = lematizador;
    }

    public List<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Category> categorias) {
        this.categorias = categorias;
    }

    public Map<String, String> getEstado() {
        return estado;
    }

    public void setEstado(Map<String, String> estado) {
        this.estado = estado;
    }
    
    //TODO: Mock del  llamado ak lematizador
    private String lema(String word) {
    	return word;
    }
    
    private void actualizarEstado(PatternReadItem pat, String valor) {
    	if(pat.getVar() != null) {
    		this.estado.put(pat.getVar(), valor);
    	}
    }
    
    /*
     * Código para matchear un input tokenizado con una lista de patterns de palabras y asteriscos
     */
    private boolean match (List<String> tokens, List<PatternItem> patrones) {
    	//TODO: El código puede dar nullpointerexception, se trabajan las excepciones mas adelante.
    	
    	if(tokens.size() == 0) {
    		if(patrones.size() == 0)
    			return true;
    		else
    			return false;
    	}
    	
    	if(patrones.size() == 0 && tokens.size() != 0) {
    		return false;
    	}
    	
    	PatternItem primerPatron = patrones.get(0);
    	String primerToken = tokens.get(0);
    	if(primerPatron instanceof PatternTextItem) {
    		PatternTextItem ppT = (PatternTextItem) primerPatron;
    		String patternWord = ppT.getWord();
    		if(Objects.equals(lema(primerToken), lema(patternWord))) {
    			List<String>  nuevosTokens = tokens.subList(1, tokens.size());
    			List<PatternItem> nuevosPatrones = patrones.subList(1, patrones.size());
    			//TODO: recursión de cola. Java no la optimiza, pero puedo optimizarla 
    			//bien fácil mas adelante si tengo stack overflows.
    			return match(nuevosTokens, nuevosPatrones); 
    		}
    		
    	}
    	if(primerPatron instanceof PatternReadItem) {
    		PatternReadItem ppR = (PatternReadItem) primerPatron;
    		List<String> multiTokens = new ArrayList<String>(); //Lista que guarda lo matcheado.
    		List<String>  nuevosTokens = tokens.subList(1, tokens.size());
    		List<PatternItem> nuevosPatrones = patrones.subList(1, patrones.size());
    		multiTokens.add(primerToken);
    		while(nuevosTokens.size() > 0) {
    			
    			
    			/*
    			 * Esta llamada recursiva va a terminar teniendo la profundidad
    			 * (si obviamos recursión de cola) de la cantidad de asteriscos
    			 */
    			
    			if(match(nuevosTokens, nuevosPatrones)) {
    				this.actualizarEstado(ppR, String.join(" ", multiTokens));
    				return true;
    			}
    			else {
    				multiTokens.add(nuevosTokens.remove(0));
    			}
    			
    		}
    		if(nuevosPatrones.size() == 0) {
				this.actualizarEstado(ppR, String.join(" ", multiTokens));
				return true;
			}
    	}
    	return false;
    }
    
    private List<String> tokenizarEntrada(String stringOriginal){
        List<String> ret = new ArrayList<>();
        
        String delims = "[\\s,\\.;:]+";
        String rubbish = "\"'";
        String[] splitted = stringOriginal.split(delims);
        
        for(String s : splitted){
            String cleanString = s.replaceAll("[" + rubbish + "]+", "");
            ret.add(cleanString);
        }
        return ret;
    }
    

    private String applyTemplate(Template template) {
    	List<String> tokensRespuesta = new ArrayList<String>();
    	for(TemplateItem item : template.getItems()) {
    		if(item instanceof TemplateTextItem) {
    			TemplateTextItem text = (TemplateTextItem) item;
    			tokensRespuesta.add(text.getText());
    		}
    		if(item instanceof TemplateGetItem) {
    			TemplateGetItem get = (TemplateGetItem) item;
    			String var = get.getVar();
    			String val = estado.get(var);
    			if(val == null)
    				continue;
    			tokensRespuesta.add(val);
    		}
    	}
    	return String.join(" ", tokensRespuesta);
    }
    
    private boolean habilitada(Category cat) {
    	boolean habilitado = true;
    	for(Precondition p : cat.getPreconditions()) {
    		habilitado = habilitado && p.getValue() == estado.get(p.getVariable());
    	}
    	return habilitado;
    }
    
    public String responder(String input){
        List<String> entradaTokenizada =  tokenizarEntrada(input);
        for(Category cat : this.categorias) {
        	if(this.habilitada(cat) && this.match(entradaTokenizada, cat.getPatron().getItems())) {
        		String respuesta = applyTemplate(cat.getTemplate()); //calculo la respuesta antes de los sets.
        		for(SetVar s : cat.getSetVars()) {
        			this.estado.put(s.getVar(), s.getNewValue());
        		}
        		return respuesta;
        	}
        }
        return null;
    }
    
}
