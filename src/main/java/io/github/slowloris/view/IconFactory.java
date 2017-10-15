package io.github.slowloris.view;

import java.net.URL;

import javax.swing.ImageIcon;

public class IconFactory {

        private static final String ICON_PATH = "icons/";
                
        /**
         * 
         * @param name
         * @return
         */
        public static ImageIcon getIcon(String name) {
                ClassLoader loader = IconFactory.class.getClassLoader();
                URL url = loader.getResource(ICON_PATH + name);
                return new ImageIcon(url);
        }
        
}
