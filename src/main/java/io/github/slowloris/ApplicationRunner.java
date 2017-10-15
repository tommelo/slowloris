package io.github.slowloris;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.github.slowloris.event.Broadcaster;
import io.github.slowloris.http.AkamaiLookupProvider;
import io.github.slowloris.http.CheckTorProjectLookupProvider;
import io.github.slowloris.view.MainFrame;

@Component
public class ApplicationRunner implements CommandLineRunner {

        @Autowired
        private MainFrame mainFrame;
                
        @Autowired
        private Broadcaster broadcaster;
        
        private AkamaiLookupProvider hostLookupProvider = new AkamaiLookupProvider();
        private CheckTorProjectLookupProvider torLookupprovider = new CheckTorProjectLookupProvider();
        
        public void run(String... args) throws Exception {
                
                SwingUtilities.invokeLater(new Runnable() {		
                        @Override
                        public void run() {
                                mainFrame.setVisible(true);				
                        }
                });
                
                // lookup external ip		
                String ip = hostLookupProvider.lookup();
                broadcaster.broadcastExternalAddressChange(ip);
                
                // checking tor gateway
                boolean isRouted = torLookupprovider.isRoutedThroughTor();
                broadcaster.broadcastTorGatewayChange(isRouted);
        }

}
