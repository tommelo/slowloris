package io.github.slowloris.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.miginfocom.swing.MigLayout;

@Component
public class MainFrame extends JFrame {
        
        private static final long serialVersionUID = 2292695250843394149L;
        
        private FormPanel formPanel;
        private ThreadStatusPanel threadStatusPanel;
        private StatusBar statusBar;	
        
        @Autowired
        public MainFrame(FormPanel formPanel, ThreadStatusPanel threadStatusPanel, StatusBar statusBar) {		
                this.formPanel = formPanel;
                this.threadStatusPanel = threadStatusPanel;
                this.statusBar = statusBar;		
                
            init();
        }
        
        private void init() {
          this.setTitle("SlowLoris | DoS Atack");		
          this.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
          this.setDefaultCloseOperation(EXIT_ON_CLOSE);
          
          Toolkit toolKit = Toolkit.getDefaultToolkit();
          Dimension d = toolKit.getScreenSize();
                
          Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
                
          int width = d.width - (in.left + in.top);
          int height = d.height - (in.top + in.bottom);	
          int x = in.left;
          int y = in.top; 
                
          this.pack();
          this.setSize( width, height );
          this.setLocation(x, y);
                  
          this.add(formPanel, "grow, wrap");
          this.add(threadStatusPanel, "grow, wrap");
          this.add(statusBar, "grow");	  	 
        }

}
