package io.github.slowloris.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.springframework.stereotype.Component;

@Component
public class MenuBar extends JMenuBar {

        private static final long serialVersionUID = -9126349475014996142L;

        public MenuBar() {
                init();
        }
        
        private void init() {
                JMenu settings = new JMenu("Settings");
                settings.add(new JMenuItem("Proxy"));
                settings.add(new JMenuItem("About"));
                this.add(settings);		
        }
}
