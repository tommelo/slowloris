package io.github.slowloris.view;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import io.github.slowloris.domain.SocketStatus;

public class ColorTableCellRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = -3681406698064130393L;
        
        private static final Map<SocketStatus, Color> COLOR_MAP = new HashMap<>();
        static {
                COLOR_MAP.put(SocketStatus.WAITING, Color.YELLOW);
                COLOR_MAP.put(SocketStatus.CONNECTED, Color.GREEN);
                COLOR_MAP.put(SocketStatus.DISCONNECTED, Color.RED);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                        int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                                
                if (column == 4) {
                        SocketStatus status = SocketStatus.valueOf(table.getValueAt(row, 4).toString());
                        component.setBackground(COLOR_MAP.get(status));
                } else {
                        component.setBackground(Color.WHITE);
                }
                                
                return component;
        }

}
