package com.mm.system.domain.dto;

import java.io.Serializable;
import java.util.List;


public class PageInfoDTO<T> extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 8572943675678665226L;
    public static final int EXPORT_LIMIT_SIZE = 20000;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int FIRST_PAGE_NO = 1;
    private long curPagerNo = 1L;
    private long pageSize = 10L;
    private long rowsCount;
    private long totalPageNumber;
    private List<T> list;
    private boolean first;
    private boolean last;

    public PageInfoDTO(long allCount, long curPagerNo, long pageSize, List list) {
        this.curPagerNo = curPagerNo;
        this.pageSize = pageSize;
        this.rowsCount = allCount;
        this.list = list;
        this.totalPageNumber = ((int) Math.ceil((double) allCount / (double) pageSize));
        this.first = this.checkFirst();
        this.last = this.checkLast();
    }

    public PageInfoDTO() {
        this.first = this.checkFirst();
        this.last = this.checkLast();
    }

    public PageInfoDTO(PageInfoDTO pager) {
        this.curPagerNo = pager.curPagerNo;
        this.pageSize = pager.pageSize;
        this.rowsCount = pager.rowsCount;
        this.list = pager.list;
        this.first = this.checkFirst();
        this.last = this.checkLast();
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public long getRowsCount() {
        return this.rowsCount;
    }

    public long getTotalPageNumber() {
        return this.totalPageNumber = ((int) Math.ceil((double) this.rowsCount / (double) this.pageSize));
    }

    public int first() {
        return 1;
    }

    public long last() {
        return this.totalPageNumber;
    }

    public long previous() {
        return this.curPagerNo - 1L < 1L ? 1L : this.curPagerNo - 1L;
    }

    public long next() {
        return this.curPagerNo + 1L > this.totalPageNumber ? this.totalPageNumber : this.curPagerNo + 1L;
    }

    public boolean isFirst() {
        return this.first;
    }

    public boolean isLast() {
        return this.last;
    }

    private boolean checkFirst() {
        return this.curPagerNo == 1L;
    }

    private boolean checkLast() {
        return this.curPagerNo == this.totalPageNumber || this.totalPageNumber == 0L;
    }

    public long getCurPagerNo() {
        return this.curPagerNo;
    }

    public void setCurPagerNo(long curPagerNo) {
        this.curPagerNo = curPagerNo;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public void setRowsCount(long rowsCount) {
        this.rowsCount = rowsCount;
    }

    public void setTotalPageNumber(long pageCount) {
        this.totalPageNumber = pageCount;
    }

    public void appendPage(PageInfoDTO pager) {
        if (pager != null) {
            this.curPagerNo = pager.curPagerNo;
            this.pageSize = pager.pageSize;
            this.rowsCount += pager.rowsCount;
            this.totalPageNumber = this.getTotalPageNumber();
            this.list.addAll(pager.list);
        }

    }

    public void setPageInfo(long pageNo, long pageSize) {
        this.curPagerNo = pageNo;
        this.pageSize = pageSize;
    }
}
