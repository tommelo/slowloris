package io.github.slowloris.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import io.github.slowloris.controller.SlowLorisController;
import io.github.slowloris.domain.Target;
import io.github.slowloris.event.Broadcaster;
import net.miginfocom.swing.MigLayout;

@Component
public class FormPanel extends JPanel {

        private static final long serialVersionUID = -7841696560976719350L;
        
        private JTextField targetField = new JTextField();
        private JTextField pathField = new JTextField("/");
        private JTextField portField = new JTextField("80");
        private JTextField threadsField = new JTextField("2000");
        private JButton startButton = new JButton("Start");
        private JButton stopButton = new JButton("Stop");
        private JButton settingsButton = new JButton(IconFactory.getIcon("settings.gif"));
                        
        private SettingsDialog settingsDialog;
        
        private SlowLorisController controller;
        private Broadcaster broadcaster;
        
        @Autowired
        public FormPanel(SlowLorisController controller, Broadcaster broadcaster, SettingsDialog settingsDialog) {		
                this.controller = controller;
                this.broadcaster = broadcaster;
                this.settingsDialog = settingsDialog;
                
                init();
        }
        
        private void init() {
                this.setLayout(new MigLayout("", "[][grow][][][][][][][][]20[]20[]", "[]"));
                this.add(new JLabel("Host:"));
                this.add(targetField, "growx");
                this.add(new JLabel("Path:"));
                this.add(pathField, "width 120:120:120");
                this.add(new JLabel("Port:"));
                this.add(portField, "width 120:120:120");
                this.add(new JLabel("Threads:"));
                this.add(threadsField, "width 120:120:120");
                this.add(stopButton, "width 100:100:100");
                this.add(startButton, "width 100:100:100");
                this.add(new JSeparator(JSeparator.VERTICAL), "height 50:50:50");
                this.add(settingsButton);
                                
                this.stopButton.setEnabled(false);
                this.setBorder(BorderFactory.createTitledBorder("Target"));
                                
                startButton.addActionListener(new StartActionListener());
                stopButton.addActionListener(new StopActionListener());		
                settingsButton.addActionListener(new SettingsActionListener());
        }

        private void reset() {
                stopButton.setEnabled(false);
                startButton.setEnabled(true);	
        }
        
        private class SettingsActionListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                        settingsDialog.setLocationRelativeTo(null);
                        settingsDialog.setVisible(true);			
                }
                
        }
        /**
         * 
         * @author root
         *
         */
        private class StopActionListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                        broadcaster.broadcastStatus("Stoping all threads...");
                        controller.stop();		
                        reset();
                }
                
        }
        
        /**
         * 
         * @author root
         *
         */
        private class StartActionListener implements ActionListener, Runnable {

                private Target target;
                
                @Override
                public void actionPerformed(ActionEvent arg0) {					
                        broadcaster.broadcastStatus("Validating Form...");
                        
                        String domainText = targetField.getText().trim();
                        String pathText = pathField.getText().trim();
                        String portText = portField.getText().trim();
                        String threadsText = threadsField.getText().trim();
                        
                        if (!ResourceUtils.isUrl(domainText)) {
                                JOptionPane.showMessageDialog(null, "Invalid host.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                broadcaster.broadcastStatus("None");
                                return;
                        }
                        
                        if (StringUtils.isEmpty(pathText)) {
                                JOptionPane.showMessageDialog(null, "Invalid path.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                broadcaster.broadcastStatus("None");
                                return;
                        }
                        
                        if (!portText.chars().allMatch(Character::isDigit) || StringUtils.isEmpty(portText)) {
                                JOptionPane.showMessageDialog(null, "Invalid port.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                broadcaster.broadcastStatus("None");
                                return;
                        }
                        
                        if (!threadsText.chars().allMatch(Character::isDigit) || StringUtils.isEmpty(threadsText)) {
                                JOptionPane.showMessageDialog(null, "Invalid number of threads.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                broadcaster.broadcastStatus("None");
                                return;
                        }
                        
                        startButton.setEnabled(false);
                        stopButton.setEnabled(true);
                        
                        target = new Target();
                        target.setDomain(domainText);
                        target.setPath(pathText);
                        target.setPort(Integer.parseInt(portText));
                        target.setThreads(Integer.parseInt(threadsText));
                        
                        ExecutorService executor = Executors.newFixedThreadPool(1);
                        executor.execute(this);				
                }

                @Override
                public void run() {					
                        try {				
                                
                                controller.start(target);	
                                
                        } catch (UnknownHostException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Host not found.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                reset();
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Unable to start the requests", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                reset();
                        } catch (MalformedURLException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Invalid domain format", "Invalid Input", JOptionPane.ERROR_MESSAGE);				
                                reset();
                        }			
                }
                
        }
}
