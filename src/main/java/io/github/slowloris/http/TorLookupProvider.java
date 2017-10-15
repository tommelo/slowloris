package io.github.slowloris.http;

import java.io.IOException;

public interface TorLookupProvider {

        /**
         * 
         * @return
         * @throws IOException
         */
        boolean isRoutedThroughTor() throws IOException;
}
