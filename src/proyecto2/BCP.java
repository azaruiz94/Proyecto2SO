/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

/**
 *
 * @author usuario
 */
public class BCP implements Comparable<BCP>{
    public BCP(){
        
    }
    
    public BCP(String nombre, int t_llegada, int cant_rafagas, int prioridad){
        _nombre= nombre;
        _t_llegada= t_llegada;
        _cant_rafagas= cant_rafagas;
        _prioridad= prioridad;
    }
    
    public String getNombre(){
        return _nombre;
    }
    
    public Integer getTiempoLlegada(){
        return _t_llegada;
    }
    
    public void setTiempoLlegada(int tiempo){
        _t_llegada= tiempo;
    }
    
    public int getCantRafagas(){
        return _cant_rafagas;
    }
    
    public void decrementarRafaga(){
        _cant_rafagas--;
    }
    
    public int getPrioridad(){
        return _prioridad;
    }

    private String _nombre;
    private int _t_llegada;
    private int _cant_rafagas;
    private int _prioridad;

    @Override
    public int compareTo(BCP o) {
        return this.getTiempoLlegada().compareTo(o.getTiempoLlegada());
    }
    
    @Override
    public String toString() {
        return "Proceso: " + this.getNombre() +"llegada=" + this.getTiempoLlegada();
    }
}
