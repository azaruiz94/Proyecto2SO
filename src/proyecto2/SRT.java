/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author usuario
 */
public class SRT extends CPUScheduler implements Runnable{

    @Override
    public void schedule(List <JTextArea> logs) {
        _logs= logs;
        t1.start();
    }
    
    @Override
    public void run() {
        List<BCP> procesos= this.getListaProcesos();
        List<BCP> espera= this.getListaEspera();
        Collections.sort(procesos);
        int cpu_txtarea= 0;
        int fin_txtarea= 2;
        int cpu_txtpos, fin_txtpos;
        BCP shortest;
        updateListaEspera(procesos);
        //se cargan todos los procesos que llegan al instante t=0
        while(!getListaEspera().isEmpty()){
            updateListaEspera(procesos);
            updateListaProcesos(espera);
            shortest= getShortestJob(this.getListaEspera());
            cpu_txtpos = _logs.get(cpu_txtarea).getCaretPosition();
            _logs.get(cpu_txtarea).insert("t= "+ getTime() +" Ejecutando proceso: "+ shortest.getNombre() + " CCPU:" + shortest.getCantRafagas()+"\n", cpu_txtpos);
            shortest.decrementarRafaga();
            try {
                Thread.sleep(this.getCiclo());
            } catch (InterruptedException ex) {
                Logger.getLogger(SRT.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.incTime();
            if(shortest.getCantRafagas() == 0){
                addListaFinalizados(shortest);
                fin_txtpos = _logs.get(fin_txtarea).getCaretPosition();
                _logs.get(fin_txtarea).insert(shortest.getNombre()+ ", "+ shortest.getCantRafagas()+ "\n", fin_txtpos);
                removeFromListaEspera(shortest);
            }
        }
    }
    
    private BCP getShortestJob(List<BCP> lista){
        int menor=1000;
        BCP shortest = null;
        for(BCP bcp: lista){
            if(bcp.getCantRafagas() < menor && bcp.getCantRafagas() > 0){
                shortest= bcp;
                menor= bcp.getCantRafagas();
            }
        }
        return shortest;
    }
    
    private void updateListaEspera(List<BCP> procesos){
        int ready_textarea= 1;
        int ready_txtpos;
        for(BCP bcp: procesos){
            if(bcp.getTiempoLlegada() <= this.getTime() && !this.getListaEspera().contains(bcp)){
                this.addColaEspera(bcp);
                ready_txtpos = _logs.get(ready_textarea).getCaretPosition();
                _logs.get(ready_textarea).insert(bcp.getNombre()+ ", "+ bcp.getCantRafagas()+ "\n", ready_txtpos);
            }
        }
    }
    
    private void updateListaProcesos(List<BCP> lista_espera){
        for(BCP bcp: lista_espera){
            this.removeFromListaProcesos(bcp);
        }
    }
    
    private Thread t1= new Thread(this);
    private List<JTextArea> _logs;
}
