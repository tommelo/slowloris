package io.github.slowloris.http;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractTorLookupProvider implements TorLookupProvider {

private String uri;
        
        /**
         * 
         * @param uri
         */
        public AbstractTorLookupProvider(String uri) {
                this.uri = uri;
        }
        
        @Override
        public boolean isRoutedThroughTor() throws IOException {
                Document document = Jsoup.connect(uri).get();
                return parse(document);
        }
        
        /**
         * 
         * @param document
         * @return
         */
        abstract boolean parse(Document document);
}
