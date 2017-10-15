package io.github.slowloris.http;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CheckTorProjectLookupProvider extends AbstractTorLookupProvider {

        private static final String TOR_ENDPOINT = "https://check.torproject.org/";
        
        public CheckTorProjectLookupProvider() {
                super(TOR_ENDPOINT);
        }

        @Override
        boolean parse(Document document) {
                Elements elements = document.select("div.content").select("h1.on");
                return elements.size() > 0;		
        }

}
