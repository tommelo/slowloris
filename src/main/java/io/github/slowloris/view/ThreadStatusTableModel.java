package io.github.slowloris.view;

import javax.swing.table.AbstractTableModel;

public class ThreadStatusTableModel extends AbstractTableModel {

        private static final long serialVersionUID = -6256869322560793155L;

        private final String[] COLUMNS = { "ID", "Host", "Request", "Status" };
        private String[][] rows = new String[][] {};

        @Override
        public int getColumnCount() {
                return COLUMNS.length;
        }

        @Override
        public int getRowCount() {
                return rows.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
                return rows[row][col];
        }

        @Override
        public String getColumnName(int column) {
                return COLUMNS[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
        }

        /**
         * 
         * @param id
         * @param host
         * @param request
         * @param status
         */
        public void addRow(int id, String host, String request, String status) {
                setValueAt(id, getRowCount(), 0);
                setValueAt(host, getRowCount(), 1);
                setValueAt(request, getRowCount(), 2);
                setValueAt(status, getRowCount(), 3);

                fireTableDataChanged();
        }

}
