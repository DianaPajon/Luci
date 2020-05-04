/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aura.luci.chatbot.engine;

import com.aura.lematizador.lematizador.Lematizador;
import com.aura.lematizador.lematizador.Pair;
import com.aura.luci.chatbot.luciml.Category;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author diana
 */
public class Luci {
    private Lematizador lematizador;
    private Set<Category> categorias;
    private Set<Pair<String, String>> estado;
    
    public Luci(Lematizador lematizador, Set<Category> categorias) {
        this.lematizador = lematizador;
        this.categorias = categorias;
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
    
    
    private Set<Category> respuestasDisponibles(){
        Set<String> precondicion = new HashSet<>();
        
        for(Pair<String, String> estadoValor: estado){
            precondicion.add(estadoValor.getFirst());
        }
        
        Set<Category> disponibles = new HashSet<>();
        
        for(Category c : categorias){
            if(precondicion.containsAll(c.getPre())){
                disponibles.add(c);
            }
        }
        return disponibles;
    }
    
    public String responder(String pregunta){
        return "Hola";
    }
    
}
