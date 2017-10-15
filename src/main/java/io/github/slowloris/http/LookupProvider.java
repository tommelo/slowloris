package io.github.slowloris.http;

import java.io.IOException;

public interface LookupProvider {

        /**
         * 
         * @return
         * @throws IOException
         */
        String lookup() throws IOException;
        
}
