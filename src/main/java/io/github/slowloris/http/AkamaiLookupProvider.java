package io.github.slowloris.http;

import org.jsoup.nodes.Document;

public class AkamaiLookupProvider extends AbstractInetAddressLookupProvider {
        
        private static final String AKAMAI_ENDPOINT = "http://whatismyip.akamai.com/";
        
        /**
         * The default constructor
         */
        public AkamaiLookupProvider() {
                super(AKAMAI_ENDPOINT);
        }

        @Override
        String parse(Document document) {		
                return document.text();
        }		

}
