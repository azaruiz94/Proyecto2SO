/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author usuario
 */
public abstract class CPUScheduler {
    
    public CPUScheduler(){
        this.lista_procesos = new ArrayList<>();
        this.cola_espera = new ArrayList<>();
        this.actual = new BCP();
        this.finalizados = new ArrayList<>();
        this.reprogramados = new ArrayList<>();
    }
    
    public abstract void schedule(JTextArea log);
    
    public void addListaProcesos(BCP bcp){
        lista_procesos.add(bcp);
    }
    
    public List getListaProcesos(){
        return lista_procesos;
    }
    
    public void addColaEspera(BCP bcp){
        cola_espera.add(bcp);
    }
    
    public List getListaEspera(){
        return cola_espera;
    }
    
    public void setProcesoActual(BCP bcp){
        actual= bcp;
    }
    
    public BCP getProcesoActual(){
        return actual;
    }
    
    public void addListaFinalizados(BCP bcp){
        finalizados.add(bcp);
    }
    
    public List getListaFinalizados(){
        return finalizados;
    }
    
    public void addListaReprogramados(BCP bcp){
        reprogramados.add(bcp);
    }
    
    public List getListaReprogramados(){
        return reprogramados;
    }
    
    public int getTotalRafagas(){
        int suma=0;
        for(BCP bcp: lista_procesos){
            suma+= bcp.getCantRafagas();
        }
        return suma;
    }
    
    private final List<BCP> lista_procesos;
    private final List<BCP> cola_espera;
    private BCP actual;
    private final List<BCP> finalizados;
    private final List<BCP> reprogramados;
    
}
