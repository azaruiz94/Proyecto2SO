/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextArea;

/**
 *
 * @author usuario
 */
public class FCFS extends CPUScheduler{
    @Override
    public void schedule(JTextArea log) {
        int pos;
        List<BCP> sorted_list= this.getListaProcesos();
        Collections.sort(sorted_list);
        for(BCP bcp: sorted_list){
            for(int i=bcp.getCantRafagas(); i>0; i--){
                System.out.println("t= "+ time +" Ejecutando proceso: "+ bcp.getNombre() + " CCPU:" + bcp.getCantRafagas());
                bcp.decrementarRafaga();
                pos = log.getCaretPosition();
                log.insert("t= "+ time +" Ejecutando proceso: "+ bcp.getNombre() + " CCPU:" + bcp.getCantRafagas()+"\n", pos);
                time++;
                
            }
        }
    }
    
    private int time=0;
    
}
