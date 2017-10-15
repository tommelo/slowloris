package io.github.slowloris.http;

public enum LookupStrategy {

        /**
         * 
         */
        AKAMAI(new AkamaiLookupProvider());
        
        private LookupProvider provider;
        
        /**
         * 
         * @param provider
         */
        LookupStrategy(LookupProvider provider) {
                this.provider = provider;
        }
        
        /**
         * 
         * @return
         */
        public static LookupStrategy strategy() {
                return null;
        }
        
        /**
         * 
         * @return
         */
        public LookupProvider getLookupProvider() {
                return provider;
        }
}
