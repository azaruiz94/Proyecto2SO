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
public class FCFS extends CPUScheduler implements Runnable{
    
    @Override
    public void schedule(List<JTextArea> logs) {
        t1.start();
        _logs= logs;
    }

    @Override
    public void run() {
        int cpu_txtarea=0;
        int fin_txtarea=2;
        List<BCP> sorted_list= this.getListaProcesos();
        Collections.sort(sorted_list);
        try {
            for(BCP bcp: sorted_list){
                for(int i=bcp.getCantRafagas(); i>0; i--){
                    updateReadyList(sorted_list);
                    showLogMessage("t= "+ getTime() +" Ejecutando proceso: "+ bcp.getNombre() + " CCPU:" + bcp.getCantRafagas()+"\n", cpu_txtarea);
                    bcp.decrementarRafaga();
                    Thread.sleep(this.getCiclo());
                    this.incTime();
                }
                this.addListaFinalizados(bcp);
                showLogMessage(bcp.getNombre()+ ", "+ bcp.getCantRafagas()+ "\n", fin_txtarea);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(FCFS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retorna una lista ordenada de la lista de espera
     * @param lista_procesos
     * @return 
     */
    private List<BCP> updateReadyList(List<BCP> lista_procesos){
        int ready_textarea= 1;
        for(BCP ready: lista_procesos){
            if(this.getTime() >= ready.getTiempoLlegada() && !this.getListaEspera().contains(ready)){
                this.addColaEspera(ready);
                showLogMessage(ready.getNombre()+ ", "+ ready.getCantRafagas()+ "\n", ready_textarea);
            }
        }
        Collections.sort(this.getListaEspera());
        return this.getListaEspera();
    }
    
    private void showLogMessage(String msg, int textArea){
        int pos;
        pos = _logs.get(textArea).getCaretPosition();
        _logs.get(textArea).insert(msg, pos);
    }
    
    private Thread t1= new Thread(this);
    private List<JTextArea> _logs;
}
