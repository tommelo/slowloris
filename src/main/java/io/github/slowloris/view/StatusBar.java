package io.github.slowloris.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.slowloris.event.Broadcaster;
import io.github.slowloris.event.ExternalAddressChangeListener;
import io.github.slowloris.event.StatusChangeListener;
import io.github.slowloris.event.TorGatewayChangeListener;
import net.miginfocom.swing.MigLayout;

@Component
public class StatusBar extends JPanel 
        implements StatusChangeListener, 
                           ExternalAddressChangeListener,
                           TorGatewayChangeListener {

        private static final long serialVersionUID = 8149553201141373194L;
        
        private JLabel statusLabel = new JLabel("None");
        private JLabel torStatusLabel = new JLabel("Checking...");
        private JLabel ipLabel = new JLabel();
        
        private Broadcaster broadcaster;
        
        @Autowired
        public StatusBar(Broadcaster broadcaster) {
                this.broadcaster = broadcaster;
                init();
        }
        
        private void init() {
                this.setLayout(new MigLayout("", "[][grow][][grow][][]", "[]"));
                this.add(new JLabel("Task:"));
                this.add(statusLabel);
                this.add(new JLabel("IP:"));
                this.add(ipLabel);
                this.add(new JLabel(IconFactory.getIcon("tor.png")));
                this.add(torStatusLabel);
                this.setBorder(BorderFactory.createTitledBorder("Status"));
                
                broadcaster.registerStatusListener(this);
                broadcaster.registerExternalAddressChangeListener(this);
                broadcaster.registerTorGatewayChangeListener(this);
        }

        @Override
        public void onStatusChange(String status) {
                statusLabel.setText(status);		
        }

        @Override
        public void onExternalAddressChange(String ip) {
                ipLabel.setText(ip);		
        }

        @Override
        public void onTorGatewayChange(boolean isRouted) {
                String text = isRouted ? "USING TOR": "NOT USING TOR";
                Color color = isRouted ? Color.GREEN : Color.RED;
                
                torStatusLabel.setForeground(color);
                torStatusLabel.setText(text);
        }
}
