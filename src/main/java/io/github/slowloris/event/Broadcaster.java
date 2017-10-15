package io.github.slowloris.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.github.slowloris.domain.WorkerStatus;

@Component
public class Broadcaster {

        private List<StatusChangeListener> statusListeners = new ArrayList<>();
        private List<WorkerStatusChangeListener> workerListeners = new ArrayList<>();
        private List<ExternalAddressChangeListener> ipListeners = new ArrayList<>();
        private List<TorGatewayChangeListener> torGatewayListeners = new ArrayList<>();
        
        /**
         * 
         * @param listener
         */
        public void registerStatusListener(StatusChangeListener listener) {
                this.statusListeners.add(listener);
        }
        
        /**
         * 
         * @param listener
         */
        public void removeStatusListener(StatusChangeListener listener) {
                this.statusListeners.remove(listener);
        }
        
        /**
         * 
         * @param listener
         */
        public void registerWorkerStatusChangeListener(WorkerStatusChangeListener listener) {
                this.workerListeners.add(listener);
        }
        
        /**
         * 
         * @param listener
         */
        public void removeWorkerStatusChangeListener(WorkerStatusChangeListener listener) {
                this.workerListeners.remove(listener);
        }
        
        /**
         * 
         * @param listener
         */
        public void registerExternalAddressChangeListener(ExternalAddressChangeListener listener) {
                ipListeners.add(listener);
        }
        
        /**
         * 
         * @param listener
         */
        public void removeExternalAddressChangeListener(ExternalAddressChangeListener listener) {
                ipListeners.remove(listener);
        }
        
        /**
         * 
         * @param listener
         */
        public void removeTorGatewayChangeListener(TorGatewayChangeListener listener) {
                torGatewayListeners.remove(listener);
        }
        
        /**
         * 
         * @param listener
         */
        public void registerTorGatewayChangeListener(TorGatewayChangeListener listener) {
                torGatewayListeners.add(listener);
        }
        
        /**
         * 
         * @param status
         */
        public void broadcastStatus(String status) {
                statusListeners.forEach(l -> { l.onStatusChange(status); });
        }
        
        /**
         * 
         * @param status
         */
        public void broadcastWorkerStatus(WorkerStatus status) {
                workerListeners.forEach(w -> { w.onWorkerStatusChange(status); });
        }
        
        /**
         * 
         * @param ip
         */
        public void broadcastExternalAddressChange(String ip) {
                ipListeners.forEach(l -> { l.onExternalAddressChange(ip); });
        }
        
        /**
         * 
         * @param isRouted
         */
        public void broadcastTorGatewayChange(boolean isRouted) {
                torGatewayListeners.forEach(l -> { l.onTorGatewayChange(isRouted); });
        }
}
