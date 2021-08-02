package net.kyaz0x1.dcautomaticstatus.view.table;

import net.kyaz0x1.dcautomaticstatus.api.models.Status;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StatusTableModel extends AbstractTableModel {

    private final List<Status> statusList;

    private final String[] columnNames = new String[]{ "text", "emoji" };
    private final Class[] columnClass = new Class[]{ String.class, String.class };

    public StatusTableModel(List<Status> statusList){
        this.statusList = statusList;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getRowCount() {
        return statusList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Status row = statusList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return row.getText();
            case 1:
                return row.getEmojiName();
        }
        return null;
    }

}