package org.practicas.backCapgemini.author.model;

import org.practicas.backCapgemini.common.pagination.PageableRequest;

/**
 * @author ccsw
 *
 */
public class AuthorSearchDto {

    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
