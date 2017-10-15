package io.github.slowloris;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SlowLoris {
        
    public static void main( String[] args ) {
        System.setProperty("java.awt.headless", "false");    	    
        
        try {
                
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        
                } catch (ClassNotFoundException | InstantiationException 
                                | IllegalAccessException | UnsupportedLookAndFeelException e) {
                        
                        e.printStackTrace();
                }
        
        new SpringApplicationBuilder(SlowLoris.class)
                .web(false)
                .run(args);    	
    }
}
