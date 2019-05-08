/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public class MonitorUI extends JFrame{
    
    public MonitorUI(){
        initComponents();
        this.setTitle("MonitorUI");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(mainPanel);
        //this.setSize(WIDTH, HEIGHT);
        //this.setResizable(false);
        this.getRootPane().setDefaultButton(runBtn);
        this.pack();
        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setLocation((int)d.getWidth()/2 - (int)this.getPreferredSize().getWidth()/2,
                //(int)d.getHeight()/2 - (int)this.getPreferredSize().getHeight()/2);
        this.setVisible(true);
    }
    
    private void initComponents(){
//        scheduler= new CPUScheduler();
        logs= new ArrayList<>();
        model = new DefaultTableModel(new String[]{ "Nombre", "T. Llegada", "T. CPU", "Prioridad"}, 0);
        cargarTabla("MisProcesos.txt");
        table= new JTable(model){
            public boolean isCellEditable(int row, int column){
                switch(column){
                    case 1:
                        return false;
                    default: return true;}
                }};
        table.setFillsViewportHeight(true);
        tablePane= new JScrollPane(table);
        tableBorder= BorderFactory.createTitledBorder("Lista de Procesos");
        tablePane.setBorder(tableBorder);
        tablePane.setBounds(25, 25, 450, 250);
        
        
        sendBtn= new JButton("Enviar");
        sendBtn.setBounds(25, 280, 85, 25);
        sendBtn.setVisible(false);
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String nombre = (String) model.getValueAt(row, 0);
                int ccpu = Integer.parseInt((String) model.getValueAt(row, 2));
                int prioridad= Integer.parseInt((String) model.getValueAt(row, 3));
                System.out.println(nombre + " "+ scheduler.getTime()+ " " + ccpu+ " " + prioridad );
                scheduler.addListaProcesos(new BCP(nombre, scheduler.getTime(), ccpu, prioridad));
             }
        });
        
        addBtn = new JButton("Agregar");
        addBtn.setBounds(300, 280, 85, 25);
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new String[]{"", "-", "", ""});
            } 
        });
        
        removeBtn = new JButton("Eliminar");
        removeBtn.setBounds(390, 280, 85, 25);
        removeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row > -1) {
                    model.removeRow(row);
                }
            }
        });
        
        process_log= new JTextArea();
        processPane= new JScrollPane(process_log);
        processBorder = BorderFactory.createTitledBorder("Log del procesador");
        processPane.setBorder(processBorder);
        processPane.setBounds(25, 310, 450, 100);
        
        ready_log= new JTextArea();
        readyPane= new JScrollPane(ready_log);
        readyBorder = BorderFactory.createTitledBorder("Cola de espera");
        readyPane.setBorder(readyBorder);
        readyPane.setBounds(500, 25, 150, 385);
        
        finished_log= new JTextArea();
        finishedPane= new JScrollPane(finished_log);
        finishedBorder = BorderFactory.createTitledBorder("Finalizados");
        finishedPane.setBorder(finishedBorder);
        finishedPane.setBounds(650, 25, 150, 385);
        
        rescheduled_log= new JTextArea();
        rescheduledPane= new JScrollPane(rescheduled_log);
        rescheduledBorder = BorderFactory.createTitledBorder("Replanificados");
        rescheduledPane.setBorder(rescheduledBorder);
        rescheduledPane.setBounds(800, 25, 150, 385);
        
        ciclos_lbl = new JLabel("Ciclo de proceso en ms:");
        ciclos_lbl.setBounds(25, 420, 180, 25);
        ciclos_field = new JTextField();
        ciclos_field.setBounds(150, 420, 180, 25);
        ciclos_field.setText("1000");
        
        options = new JComboBox(new String[]{"FCFS", "SJF", "RR"});
        options.setBounds(390, 420, 85, 20);
        
        logs.add(process_log);
        logs.add(ready_log);
        logs.add(finished_log);
        logs.add(rescheduled_log);
        
        runBtn = new JButton("Run");
        runBtn.setBounds(390, 450, 85, 25);
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendBtn.setVisible(true);
                process_log.setText("");
                ready_log.setText("");
                finished_log.setText("");
                String eleccion= (String) options.getSelectedItem();
                switch(eleccion){
                    case "FCFS":
                        scheduler= new FCFS();
                    break;
                    case "SJF":
                        scheduler= new SRT();
                    break;
                    case "RR":
                        String q = JOptionPane.showInputDialog(this, "Ingrese el quantum");
                        if (q == null) {
                            q="4";
                        }
                        int quantum= Integer.parseInt(q);
                        scheduler= new RR();
                    break;
                    default:
                        return;
                }
                cargarListaProcesos(scheduler);
                scheduler.setCiclo(Integer.parseInt(ciclos_field.getText()));
                scheduler.schedule(logs);
            }
        });
        mainPanel= new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.add(tablePane);
        mainPanel.add(readyPane);
        mainPanel.add(finishedPane);
        mainPanel.add(rescheduledPane);
        mainPanel.add(addBtn);
        mainPanel.add(removeBtn);
        mainPanel.add(processPane);
        mainPanel.add(ciclos_lbl);
        mainPanel.add(ciclos_field);
        mainPanel.add(options);
        mainPanel.add(runBtn);
        mainPanel.add(sendBtn);
    }
    
    /**
     * Carga los procesos en la tabla de la GUI y en una estructura de datos
     * @param archivo
     */
    public void cargarTabla(String archivo){
        BufferedReader reader= null;
        try {
            reader = new BufferedReader(new FileReader(archivo));
            String linea= reader.readLine();
            while(linea != null){
                String[] partes=linea.split(",");
                model.addRow(new String[]{partes[0], partes[1], partes[2], partes[3]});
                linea= reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CPUScheduler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CPUScheduler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(CPUScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void cargarListaProcesos(CPUScheduler scheduler){
        for (int i = 0; i < model.getRowCount(); i++){
            String nombre = (String) model.getValueAt(i, 0);
            int llegada;
            if((String)model.getValueAt(i, 1) == "-"){
                llegada = scheduler.getTime();
            }else{
                llegada = Integer.parseInt((String) model.getValueAt(i, 1));
            }
            int ccpu = Integer.parseInt((String) model.getValueAt(i, 2));
            int prioridad= Integer.parseInt((String) model.getValueAt(i, 3));
            scheduler.addListaProcesos(new BCP(nombre, llegada, ccpu, prioridad));
            //System.out.println(nombre +" "+ llegada+" "+ccpu+" "+prioridad);
        }
    }
    
    private final int HEIGHT= 500;
    private final int WIDTH= 1000;
    private JTable table;
    private JScrollPane tablePane;
    private JScrollPane processPane;
    private TitledBorder processBorder;
    private TitledBorder tableBorder;
    private JTextArea process_log;
    private JScrollPane readyPane;
    private TitledBorder readyBorder;
    private JTextArea ready_log;
    private JScrollPane finishedPane;
    private TitledBorder finishedBorder;
    private JTextArea finished_log;
    private JScrollPane rescheduledPane;
    private TitledBorder rescheduledBorder;
    private JTextArea rescheduled_log;
    private JPanel mainPanel;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton runBtn;
    private JButton sendBtn;
    private JComboBox options;
    private JLabel ciclos_lbl;
    private JTextField ciclos_field;
    private DefaultTableModel model;
    private List<JTextArea> logs;
    private String send_nombre;
    private int send_ccpu, send_prioridad;
    private CPUScheduler scheduler;
    private Thread t1;
}
