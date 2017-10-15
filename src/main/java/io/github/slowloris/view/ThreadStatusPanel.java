package io.github.slowloris.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.slowloris.domain.WorkerAction;
import io.github.slowloris.domain.WorkerStatus;
import io.github.slowloris.event.Broadcaster;
import io.github.slowloris.event.WorkerStatusChangeListener;
import net.miginfocom.swing.MigLayout;

@Component
public class ThreadStatusPanel extends JPanel implements WorkerStatusChangeListener {
        
        private static final long serialVersionUID = -6663754492124627547L;
        
        private final String[] COLUMNS = { "ID", "Host", "Port", "Request Chunk", "Status" };
        private DefaultTableModel model;
        
        private JTable table;	
        private Broadcaster broadcaster;
                        
        @Autowired
        public ThreadStatusPanel(Broadcaster broadcaster) {
                this.broadcaster = broadcaster;
                init();
        }
        
        private void init() {
                this.setLayout(new MigLayout("", "[grow]", "[grow]"));
                
                model = new DefaultTableModel(COLUMNS, 0);
                table = new JTable(model);
                table.setEnabled(false);		
                
                table.getColumnModel().getColumn(0).setMaxWidth(200);
                table.getColumnModel().getColumn(2).setMaxWidth(200);
                table.getColumnModel().getColumn(4).setMinWidth(200);
                table.getColumnModel().getColumn(4).setMaxWidth(200);

                TableHeaderRenderer renderer = new TableHeaderRenderer(table);		
                table.getTableHeader().setDefaultRenderer(renderer);
                 
                ColorTableCellRenderer rightRenderer = new ColorTableCellRenderer();
                rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                TableColumnModel columnModel = table.getColumnModel();
                for (int i = 0; i < COLUMNS.length; i ++) {
                        columnModel.getColumn(i).setCellRenderer(rightRenderer);		
                }
                
                JScrollPane scroll = new JScrollPane(table);
                this.add(scroll, "span, grow");
        
                broadcaster.registerWorkerStatusChangeListener(this);		
        }

        @Override
        public void onWorkerStatusChange(WorkerStatus status) {				
                if (WorkerAction.CREATE == status.getAction()) {
                        Object [] row = new Object[] {
                                status.getId(), 
                                status.getDomain(),
                                status.getPort(), 
                                status.getCurrentRequest(), 
                            status.getDescription()
                        };
                        
                        model.addRow(row);
                        
                } else if(WorkerAction.RESET == status.getAction()) {
                        model.setRowCount(0);
                        
                } else {
                        int index = status.getId() - 1;
                        model.setValueAt(status.getCurrentRequest(), index, 3);
                        model.setValueAt(status.getDescription(), index, 4);	
                }
                                                
        }

}
