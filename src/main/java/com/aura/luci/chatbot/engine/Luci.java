/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.engine;

import com.aura.lematizador.lematizador.Lematizador;
import com.aura.lematizador.lematizador.Pair;
import com.aura.lematizador.lematizador.SynSet;
import com.aura.lematizador.lematizador.Word;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

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
    
    private static String relacionSemantica = "hiperonimo";
    private static double umbralSimilaridad = 0.5f;
    
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
                    List<Pattern> p = null;
                    Template t = null;
                    List<Precondition> precondiciones = new ArrayList<Precondition>();
                    List<SetVar> sets = new ArrayList<SetVar>();
                    for(int j = 0; j < hijo.getChildNodes().getLength();j++){
                        Node nieto = hijo.getChildNodes().item(j);
                        switch(nieto.getNodeName()){
                            case "patterns":
                                p = PatternBuild.buildPatterns(nieto);
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
                    categoria.setPatrones(p);
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
    

    
    private boolean sonSimilares(String palabra1, String palabra2) {
    	Set<SynSet> lemas1 = lematizador.encontrarLema(new Word(palabra1));
    	if(lemas1.equals(Collections.emptySet())) {
    		SynSet lemaNuevo = new SynSet(palabra1.toUpperCase());
    		Set<Word> instancias = new HashSet<>();
    		instancias.add(new Word(palabra1.toUpperCase()));
    		lemaNuevo.setInstancias(instancias);
    		lemas1.add(lemaNuevo);
    	} else {
    		String breakpoint = "hola1";
    	}
    	
    	Set<SynSet> lemas2 = lematizador.encontrarLema(new Word(palabra2));
    	if(lemas2.equals(Collections.emptySet())) {
    		SynSet lemaNuevo = new SynSet(palabra2.toUpperCase());
    		Set<Word> instancias = new HashSet<>();
    		instancias.add(new Word(palabra2.toUpperCase()));
    		lemaNuevo.setInstancias(instancias);
    		lemas2.add(lemaNuevo);
    	} else {
    		String breakpoint = "hola2";
    	}
    	
    	lemas1.retainAll(lemas2);
    	if(!lemas1.equals(Collections.emptySet())) {
    		return true;
    	}
    	return false;
    	
    }
    
    private void actualizarEstado(PatternReadItem pat, String valor) {
    	if(pat.getVar() != null) {
    		this.estado.put(pat.getVar(), valor);
    	}
    }
    
    /*
     * Código para matchear un input tokenizado con una lista de patterns de palabras y asteriscos
     */
    private boolean regexMatch (List<String> tokens, List<PatternItem> patrones) {
    	//Armo la regex
    	String regexStart = ".*";
    	String regexEnd = ".*";
    	String regex = "";
    	List<PatternReadItem> groups = new ArrayList<>();
    	for(PatternItem pi : patrones) {
    		if(pi instanceof PatternTextItem) {
    			PatternTextItem pti = (PatternTextItem) pi;
    			regex = regex + pti.getWord().toUpperCase() + "\\s*";
    		}
    		if(pi instanceof PatternReadItem) {
    			PatternReadItem pri = (PatternReadItem) pi;
    			regex = regex + "(.*)\\s*";
    			groups.add(pri);
    		}
    	}
    	//regex = regexStart + regex + regexEnd;
    	String tokensJuntos = tokens.stream().map(s->s.toUpperCase()).collect(Collectors.joining(" "));
    	if(tokensJuntos.matches(regex)) {
    		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
    		Matcher m = p.matcher(tokensJuntos);
    		while(m.find()) {
    			for(int i=0;i<groups.size();i++) {
    				PatternReadItem pri = groups.get(i);
    				actualizarEstado(pri, m.group(i+1));
    			}
    		}
    		return true;
    	}
    	return false;
    }
    
    /*
     * Matcheo por palabras de diccionario.
     */
    private boolean dictionaryMatch (List<String> tokens, List<PatternItem> patrones) {
    	outer:
    	for(int i = 0;i<tokens.size();i++) {
	    	Iterator<String> iteradorTokens = tokens.subList(i, tokens.size()).iterator();
	    	Iterator<PatternItem> iteradorPatrones = patrones.iterator();
	    	List<PatternReadItem> reads = new ArrayList<>();
	    	List<String> values = new ArrayList<>();
	    	while(iteradorPatrones.hasNext()) {
	    		PatternItem pi = iteradorPatrones.next();
	    		if(pi instanceof PatternTextItem) {
	    			PatternTextItem pti = (PatternTextItem) pi;
	    			if(!iteradorTokens.hasNext())
	    				continue outer;
	    			String token = iteradorTokens.next();
	    			if(!sonSimilares(token, pti.getWord()))
	    				continue outer;
	    		}else if (pi instanceof PatternReadItem) {
	    			if(!iteradorTokens.hasNext())
	    				continue outer;
	    			reads.add((PatternReadItem) pi);
	    			values.add(iteradorTokens.next());
	    		}
	    	}
	    	for(int j = 0;j<reads.size();j++) {
	    		actualizarEstado(reads.get(j), values.get(j));
	    	}
	    	return true;
    	}
    	return false;
    }
    
    private double similaridadWuPalmer(String palabra1, String palabra2) {
    	Set<SynSet> lemas1 = lematizador.encontrarLema(new Word(palabra1));
    	Set<SynSet> lemas2 = lematizador.encontrarLema(new Word(palabra2));
    	/*
    	 * La eficiencia de este algoritmo aumenta considerablemente al escribir
    	 * un arbol de conceptos en lugar de un látice, y al tener muy poca
    	 * polisemia.
    	 */
    	double maxSimilarity = 0;
		for(SynSet lema1 : lemas1) {
			for(SynSet lema2:lemas2) {
				SynSet lcs = lematizador.lcs("hiperonimo", lema1, lema2);
				int profundidadLcs = lematizador.profundidad(relacionSemantica, lcs);
				int profundidad1 = lematizador.profundidad(relacionSemantica, lema1);
				int profundidad2 = lematizador.profundidad(relacionSemantica, lema2);
				double wupalmer = 2 * (double) profundidadLcs / ((double) profundidad1 + (double) profundidad2);
				maxSimilarity = Math.max(wupalmer, maxSimilarity);
			}
		}
    	return maxSimilarity;
    	
    }
    
    private double similarityMatch(List<String> tokens, List<PatternItem> patron) {
    	//Primero filtro los que son patternReadItem
    	if(patron.stream().anyMatch(pattern -> pattern instanceof PatternReadItem)) {
    		return 0;
    	}
    	double average = 0;
    	int combinaciones = 0;
    	for(String token : tokens) {
    		for(PatternItem pi : patron) {
    			if(pi instanceof PatternReadItem) {
    				continue;
    			}
    			PatternTextItem text = (PatternTextItem) pi;
                        if(text.getWord().equalsIgnoreCase(token)){
                            combinaciones++;
                            average = average + patron.size() - 1;
                            continue;
                        }
    			combinaciones++;
    			average = average + similaridadWuPalmer(token, text.getWord());
    		}
    	}
    	
    	return average/combinaciones;
    }
    
    private List<String> tokenizarEntrada(String stringOriginal){
        List<String> ret = new ArrayList<>();
        
        String delims = "[\\s,\\.;:]+";
        String rubbish = "\"'?!";
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
    		habilitado = habilitado && p.getValue().equals(estado.get(p.getVariable()));
    	}
    	return habilitado;
    }
    
    public String responder(String input){
        List<String> entradaTokenizada =  tokenizarEntrada(input);
        for(Category cat : this.categorias) {
        	for(Pattern patron : cat.getPatrones()) {
	        	if(this.habilitada(cat) && this.dictionaryMatch(entradaTokenizada, patron.getItems())) {
	        		String respuesta = applyTemplate(cat.getTemplate()); //calculo la respuesta antes de los sets.
	        		for(SetVar s : cat.getSetVars()) {
	        			this.estado.put(s.getVariable(), s.getValor());
	        		}
	        		return respuesta;
	        	}
        	}
        }
        
        //Si no devolvió la respuesta antes, vamos con similaridad de oraciones. 
        Category maxCat = null;
        double maxSim = 0;
        
        for(Category cat : this.categorias) {
                if(this.habilitada(cat)) {
                    for(Pattern p : cat.getPatrones()) {

                            double similaridad = similarityMatch(entradaTokenizada, p.getItems());
                            if(similaridad > 0.5 && similaridad > maxSim) {
                                    maxCat = cat;
                                    maxSim = similaridad;
                            }
                    }
        	}
        }
        
        if(maxCat != null) {
        	String respuesta = applyTemplate(maxCat.getTemplate());
        	for(SetVar s : maxCat.getSetVars()) {
        		this.estado.put(s.getVariable(), s.getValor());
        	}
        	return respuesta;
        }
        return null;
    }
    
}
