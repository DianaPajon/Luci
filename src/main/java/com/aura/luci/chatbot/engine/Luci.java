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
import com.aura.luci.chatbot.luciml.Template;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    private Set<Category> categorias;
    private Set<Pair<String, String>> estado;
    
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
                    Node pre = hijo.getAttributes().getNamedItem("pre");
                    if(pre != null){
                        categoria.setPre(new HashSet<String>(Arrays.asList(pre.getNodeValue().split(","))));
                    } else {
                        categoria.setPre(new HashSet<String>());
                    }
                    Pattern p = null;
                    Template t = null;
                    for(int j = 0; j < hijo.getChildNodes().getLength();j++){
                        Node nieto = hijo.getChildNodes().item(j);
                        switch(nieto.getNodeName()){
                            case "pattern":
                                p = PatternBuild.buildPattern(nieto);
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
    }
    
    public Lematizador getLematizador() {
        return lematizador;
    }

    public void setLematizador(Lematizador lematizador) {
        this.lematizador = lematizador;
    }

    public Set<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Category> categorias) {
        this.categorias = categorias;
    }

    public Set<Pair<String, String>> getEstado() {
        return estado;
    }

    public void setEstado(Set<Pair<String, String>> estado) {
        this.estado = estado;
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
    

    
    public String responder(String input){
        return tokenizarEntrada(input).toString();
    }
    
}
