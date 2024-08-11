package com.mm.system.domain.query;

import lombok.Data;

/**
 * @author:
 * Date: 2023/4/6 11:04
 * Desc:
 */
@Data
public abstract class PageQuery {
    private long curPagerNo = 1L;
    private long pageSize = 10L;
    private String platformNo;
    public long getOffset() {
        return (this.getCurPagerNo() - 1L) * this.getPageSize();
    }

}
