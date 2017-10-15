package io.github.slowloris.event;

public interface TorGatewayChangeListener {

        /**
         * 
         * @param isRouted
         */
        void onTorGatewayChange(boolean isRouted);
}
