package io.github.slowloris.domain;

public class Target {

        private String domain;
        private String ip;
        private int port;
        private String path;
        private int threads;

        public String getDomain() {
                return domain;
        }

        public void setDomain(String domain) {
                this.domain = domain;
        }

        public String getIp() {
                return ip;
        }

        public void setIp(String ip) {
                this.ip = ip;
        }

        public int getPort() {
                return port;
        }

        public void setPort(int port) {
                this.port = port;
        }

        public String getPath() {
                return path;
        }

        public void setPath(String path) {
                this.path = path;
        }

        public int getThreads() {
                return threads;
        }

        public void setThreads(int threads) {
                this.threads = threads;
        }

}
