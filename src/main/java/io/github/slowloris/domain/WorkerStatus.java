package io.github.slowloris.domain;

public class WorkerStatus {

        private int id;
        private WorkerAction action;
        private SocketStatus socketStatus;
        private String description;
        private String currentRequest;
        private String domain;
        private int port;
        
        public WorkerStatus() {
                
        }
        
        public WorkerStatus(WorkerAction action) {
                this.action = action;
        }
        
        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public WorkerAction getAction() {
                return action;
        }

        public void setAction(WorkerAction action) {
                this.action = action;
        }

        public SocketStatus getSocketStatus() {
                return socketStatus;
        }

        public void setSocketStatus(SocketStatus socketStatus) {
                this.socketStatus = socketStatus;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getCurrentRequest() {
                return currentRequest;
        }

        public void setCurrentRequest(String currentRequest) {
                this.currentRequest = currentRequest;
        }

        public String getDomain() {
                return domain;
        }

        public void setDomain(String domain) {
                this.domain = domain;
        }

        public int getPort() {
                return port;
        }

        public void setPort(int port) {
                this.port = port;
        }

}
