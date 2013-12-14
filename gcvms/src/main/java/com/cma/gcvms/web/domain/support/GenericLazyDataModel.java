package com.cma.gcvms.web.domain.support;

import static com.cma.gcvms.web.conversation.ConversationHolder.getCurrentConversation;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.cma.gcvms.domain.Identifiable;
import com.cma.gcvms.printer.support.TypeAwarePrinter;
import com.cma.gcvms.repository.support.GenericRepository;
import com.cma.gcvms.repository.support.OrderBy;
import com.cma.gcvms.repository.support.OrderByDirection;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.util.ResourcesUtil;
import com.cma.gcvms.web.conversation.ConversationCallBack;
import com.cma.gcvms.web.conversation.ConversationContext;
import com.cma.gcvms.web.util.MessageUtil;
import com.cma.gcvms.web.util.PrimeFacesUtil;

/**
 * Extends PrimeFaces {@link LazyDataModel} in order to support server-side pagination, row selection, multi select etc.
 */
public abstract class GenericLazyDataModel<E extends Identifiable<PK>, PK extends Serializable, F extends GenericSearchForm<E, PK, F>> extends LazyDataModel<E> {
    private static final long serialVersionUID = 1L;

    @Inject
    protected ResourcesUtil resourcesUtil;
    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected TypeAwarePrinter printer;

    private E selectedRow;
    private E[] selectedRows;
    private boolean bypassFirstOffset = true;

    protected GenericRepository<E, PK> repository;
    protected GenericController<E, PK> controller;
    protected GenericSearchForm<E, PK, F> searchForm;
    protected GenericExcelExporter<E> excelExporter;

    public GenericLazyDataModel(GenericRepository<E, PK> repository, GenericController<E, PK> controller, GenericSearchForm<E, PK, F> searchForm,
            GenericExcelExporter<E> excelExporter) {
        this.repository = repository;
        this.controller = controller;
        this.searchForm = searchForm;
        this.excelExporter = excelExporter;
    }

    @Override
    public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        E example = searchForm.getEntity();
        SearchParameters sp = populateSearchParameters(first, pageSize, sortField, sortOrder, filters);
        setRowCount(repository.findCount(example, sp));
        return repository.find(example, sp);
    }

    /**
     * _HACK_
     * Call it from your view when a <code>search</code> event is triggered to bypass offset sent by primefaces paginator.
     */
    public void onSearch() {
        bypassFirstOffset = true;
    }

    @Override
    public void setRowCount(int rowCount) {
        super.setRowCount(rowCount);
        PrimeFacesUtil.updateSearchResultsRegion(resourcesUtil.getPluralableProperty("search_results_status", rowCount), rowCount);
    }

    /**
     * Applies the passed parameters to the passed SearchParameters.
     * @return the passed searchParameters
     */
    protected SearchParameters populateSearchParameters(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        SearchParameters sp = searchForm.toSearchParameters();
        sp.setFirst(bypassFirstOffset ? 0 : first);
        bypassFirstOffset = false;
        sp.setPageSize(pageSize);

        if (isNotEmpty(sortField)) {
            return sp.orderBy(new OrderBy(convert(sortOrder), sortField, repository.getType()));
        } else {
            return controller.defaultOrder(sp);
        }
    }

    // ---------------------
    // Select row
    // ---------------------

    /**
     * Returns the currently selected row.
     */
    public E getSelectedRow() {
        return selectedRow;
    }

    /**
     * Set the currently selected row.
     */
    public void setSelectedRow(E selectedRow) {
        this.selectedRow = selectedRow;
    }

    /**
     * Set the selectedRow to null.
     */
    public void resetSelectedRow() {
        this.selectedRow = null;
    }

    // ---------------------
    // Multi select
    // ---------------------

    public void setSelectedRows(E[] selectedRows) {
        this.selectedRows = selectedRows;
    }

    public E[] getSelectedRows() {
        return selectedRows;
    }

    public String multiSelect() {
        return getCallBack().selected(getSelectedRows());
    }

    /**
     * Convert PrimeFaces {@link SortOrder} to our {@link OrderByDirection}.
     */
    protected OrderByDirection convert(SortOrder order) {
        return order == SortOrder.DESCENDING ? OrderByDirection.DESC : OrderByDirection.ASC;
    }

    // ---------------------
    // Actions
    // ---------------------

    /**
     * Action to create a new entity.
     */
    public String create() {
        return controller.create();
    }

    /**
     * Action to edit the selected entity.
     */
    public String edit() {
        return controller.edit(getRowData());
    }

    /**
     * Action to view the selected entity.
     */
    public String view() {
        return controller.view(getRowData());
    }

    /**
     * Action invoked from sub search pages used to select the target of an association.
     */
    public String select() {
        return select(getRowData());
    }

    protected String select(E selectedRow) {
        return getCallBack().selected(selectedRow);
    }

    /**
     * React to mouse click and force the navigation depending on the context.
     * When in sub mode, the select action is invoked otherwise the edit action is invoked.
     */
    public void onRowSelect(SelectEvent event) {
        E selected = getSelectedRow();
        if (selected != null) {
            if (getCurrentConversation().getCurrentContext().isSub()) {
                Faces.navigate(controller.select(selected));
            } else if (controller.getPermission().canEdit(selected)) {
                Faces.navigate(controller.edit(selected));
            } else {
                Faces.navigate(controller.view(selected));
            }
        }
    }

    /**
     * Ajax action listener to delete the selected entity.
     */
    public void delete() {
        E selected = getSelectedRow();
        if (selected != null) {
            repository.delete(selected);
            messageUtil.infoEntity("status_deleted_ok", selected);
            resetSelectedRow();
        }
    }

    @Override
    public String getRowKey(E item) {
        return String.valueOf(item.hashCode());
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getRowData(String rowKey) {
        for (E item : ((List<E>) getWrappedData())) {
            if (rowKey.equals(getRowKey(item))) {
                return item;
            }
        }
        return null;
    }

    private ConversationCallBack<E> getCallBack() {
        return getCurrentConversation() //
                .<ConversationContext<E>> getCurrentContext() //
                .getCallBack();
    }

    public void onExcel() throws IOException {
        excelExporter.onExcel(getExcelFilename(), repository.find(searchForm.toSearchParameters()));
    }

    protected String getExcelFilename() {
        return repository.getType().getSimpleName() + "-" + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new Date()) + ".xls";
    }
}