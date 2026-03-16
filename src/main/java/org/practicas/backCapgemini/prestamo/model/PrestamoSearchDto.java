package org.practicas.backCapgemini.prestamo.model;

import org.practicas.backCapgemini.common.pagination.PageableRequest;

/**
 * @author ccsw
 *
 */
public class PrestamoSearchDto {

    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
