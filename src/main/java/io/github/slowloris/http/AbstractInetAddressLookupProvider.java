package io.github.slowloris.http;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractInetAddressLookupProvider implements LookupProvider {

        private String uri;
        
        /**
         * 
         * @param uri
         */
        public AbstractInetAddressLookupProvider(String uri) {
                this.uri = uri;
        }
        
        @Override
        public String lookup() throws IOException {
                Document document = Jsoup.connect(uri).get();
                return parse(document);
        }
        
        /**
         * 
         * @param document
         * @return
         */
        abstract String parse(Document document);
        
}
