package com.mm.system.domain.dto;


public class PageResultDTO<T> extends BaseDTO {
    private PageInfoDTO<T> page;

    public PageResultDTO() {
    }

    public PageResultDTO(PageInfoDTO<T> page) {
        this.page = page;
    }

    public PageInfoDTO<T> getPage() {
        return this.page;
    }

    public void setPage(PageInfoDTO<T> page) {
        this.page = page;
    }
}
