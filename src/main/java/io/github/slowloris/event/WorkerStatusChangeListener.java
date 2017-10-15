package io.github.slowloris.event;

import io.github.slowloris.domain.WorkerStatus;

public interface WorkerStatusChangeListener {

        /**
         * 
         * @param status
         */
        public void onWorkerStatusChange(WorkerStatus status);
}
