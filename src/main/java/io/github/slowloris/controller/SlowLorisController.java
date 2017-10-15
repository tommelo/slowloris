package io.github.slowloris.controller;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.slowloris.domain.SocketStatus;
import io.github.slowloris.domain.Target;
import io.github.slowloris.domain.WorkerAction;
import io.github.slowloris.domain.WorkerStatus;
import io.github.slowloris.event.Broadcaster;
import io.github.slowloris.http.SlowHttpRequest;

@Component
public class SlowLorisController {

        private boolean keepRunning = true;
        private ExecutorService executor;
        private List<SlowHttpRequest> workers;	
        
        private Broadcaster broadcaster;
        
        @Autowired
        public SlowLorisController(Broadcaster broadcaster) {
                this.broadcaster = broadcaster;
        }
                
        public void start(Target target) throws UnknownHostException, InterruptedException, MalformedURLException {		
                broadcaster.broadcastStatus("Resolving Host...");
                broadcaster.broadcastWorkerStatus(new WorkerStatus(WorkerAction.RESET));
                
                keepRunning = true;
                
                URL url = new URL(target.getDomain());		
                String ip = InetAddress.getByName(url.getHost()).getHostAddress();
                target.setIp(ip);
                
                broadcaster.broadcastStatus("Host Resolved: " + ip);
                
                workers = new ArrayList<>();
                executor = Executors.newFixedThreadPool(target.getThreads());
                
                for (int i = 1, size = target.getThreads(); i <= size; i++) {
                        workers.add(new SlowHttpRequest(target, i, broadcaster));
                        
                        WorkerStatus status = new WorkerStatus();
                        status.setId(i);
                        status.setAction(WorkerAction.CREATE);
                        status.setSocketStatus(SocketStatus.WAITING);
                        status.setCurrentRequest("");
                        status.setDescription(SocketStatus.WAITING.toString());
                        status.setDomain(target.getDomain());
                        status.setPort(target.getPort());
                        
                        broadcaster.broadcastWorkerStatus(status);
                }
                
                // do not slam the server
                // slowly starting all threads
                broadcaster.broadcastStatus("Starting Threads...");
                Thread.sleep(3000);
                
                List<Future<Void>> future = new ArrayList<>();
                for (SlowHttpRequest req : workers) {
                        future.add(executor.submit(req));
                        Thread.sleep(3000);
                }
                
                broadcaster.broadcastStatus("Working");
                
                while(keepRunning) {
                        
                        if (executor.isShutdown())
                                break;
                        
                        for (int i = 0, size = future.size(); i < size; i++) {
                                Future<Void> job = future.get(i);
                                if (job.isDone()) {			
                                        
                                        WorkerStatus status = new WorkerStatus();
                                        status.setId(i + 1);
                                        status.setAction(WorkerAction.UPDATE);
                                        status.setSocketStatus(SocketStatus.WAITING);
                                        status.setCurrentRequest("");
                                        status.setDescription(SocketStatus.WAITING.toString());
                                                                                
                                        broadcaster.broadcastWorkerStatus(status);
                                        
                                        future.remove(i);
                                        Future<Void> worker = executor.submit(workers.get(i));
                                        future.add(i, worker);
                                }
                        }
                        
                        Thread.sleep(5000);
                }
                
                executor.shutdown();
                broadcaster.broadcastStatus("All threads stopped.");
        }
                
        public void stop() {
                keepRunning = false;	
                
                try {
                        
                        executor.shutdownNow();
                        
                } catch (Exception e) {
                        e.printStackTrace(System.out);
                }
                                
                for (SlowHttpRequest req : workers) {
                        WorkerStatus status = new WorkerStatus();			
                        status.setId(req.getThreadId());
                        status.setAction(WorkerAction.UPDATE);
                        status.setSocketStatus(SocketStatus.DISCONNECTED);
                        status.setCurrentRequest("");
                        status.setDescription(SocketStatus.DISCONNECTED.toString());
                                                
                        broadcaster.broadcastWorkerStatus(status);
                }
                                
        }
        
}
