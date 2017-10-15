package io.github.slowloris.view;

import javax.swing.JDialog;

import org.springframework.stereotype.Component;

import net.miginfocom.swing.MigLayout;

@Component
public class SettingsDialog extends JDialog {
        
        private static final long serialVersionUID = -1819838926055213371L;
        
        public SettingsDialog() {
                init();
        }
        
        private void init() {
                this.setTitle("Settings");
                this.setLayout(new MigLayout("", "[grow]", "[][][][][][]"));
                
                this.setSize(400, 400);
                this.setModal(true);
        }
}
