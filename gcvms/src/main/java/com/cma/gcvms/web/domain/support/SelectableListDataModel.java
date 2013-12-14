package com.cma.gcvms.web.domain.support;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.cma.gcvms.domain.Identifiable;

public class SelectableListDataModel<E extends Identifiable<?>> extends ListDataModel<E> implements SelectableDataModel<E>, Serializable {
    private static final long serialVersionUID = 1L;
    private E selectedRow;

    public SelectableListDataModel() {
    }

    public SelectableListDataModel(List<E> data) {
        super(data);
    }

    /**
     * Returns the currently selected row. To be called from your flow upon a <code>selectXxx</code> transition.
     */
    public E getSelectedRow() {
        return selectedRow;
    }

    /**
     * Set the currently selected row. To be called from your dataTable.
     */
    public void setSelectedRow(E selectedRow) {
        this.selectedRow = selectedRow;
    }

    /**
     * Set the selectedRow to null. To be called from your flow.
     */
    public void resetSelectedRow() {
        this.selectedRow = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getRowData(String rowKey) {
        for (E item : (List<E>) getWrappedData()) {
            if (rowKey.equals(String.valueOf(item.hashCode()))) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(E item) {
        return String.valueOf(item.hashCode());
    }
}
