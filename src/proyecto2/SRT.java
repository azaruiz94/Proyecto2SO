/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author usuario
 */
public class SRT extends CPUScheduler implements Runnable{

    @Override
    public void schedule(List <JTextArea> logs) {
        t1.start();
        _logs= logs;
    }
    
    @Override
    public void run() {
        
    }
    
    private Thread t1= new Thread(this);
    private List<JTextArea> _logs;
}
