/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.awt.TextArea;
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
        int pos0, pos1, pos2, pos3;
        List<BCP> sorted_list= this.getListaProcesos();
        Collections.sort(sorted_list);
        try {
            for(BCP bcp: sorted_list){
                
                //t=0
                /*if(this.getTime() >= bcp.getTiempoLlegada()){
                   this.addColaEspera(bcp);
                   //Collections.sort(sorted_list);
                }*/
                //System.out.println(this.getListaEspera()+"\n");
                
                for(int i=bcp.getCantRafagas(); i>0; i--){
                    //System.out.println("t= "+ getTime() +" Ejecutando proceso: " + bcp.getNombre() + " CCPU:" + bcp.getCantRafagas()+"\n");
                    updateReadyList(sorted_list);
                    pos0 = _logs.get(0).getCaretPosition();
                    _logs.get(0).insert("t= "+ getTime() +" Ejecutando proceso: "+ bcp.getNombre() + " CCPU:" + bcp.getCantRafagas()+"\n", pos0);
                    bcp.decrementarRafaga();
                    Thread.sleep(1000);
                    this.incTime();
                }
                //System.out.println(this.getListaEspera()+"\n");
                this.addListaFinalizados(bcp);
                pos2 = _logs.get(2).getCaretPosition();
                _logs.get(2).insert(bcp.getNombre()+ ", "+ bcp.getCantRafagas()+ "\n", pos2 );
                //this.removeFromListaEspera(bcp);
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
    private void updateReadyList(List<BCP> lista_procesos){
        int pos;
        for(BCP ready: lista_procesos){
            if(this.getTime() >= ready.getTiempoLlegada() && !this.getListaEspera().contains(ready)){
                this.addColaEspera(ready);
                //this.removeFromListaProcesos(ready);
                pos = _logs.get(1).getCaretPosition();
                _logs.get(1).insert(ready.getNombre()+ ", "+ ready.getCantRafagas()+ "\n", pos );
            }
        }
        Collections.sort(this.getListaEspera());

        
        //System.out.println(this.getListaEspera()+"\n");
        //return this.getListaEspera();
    }
    
    Thread t1= new Thread(this);
    List<JTextArea> _logs;
}
