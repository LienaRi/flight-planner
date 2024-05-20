package io.codelex.flightplanner.api;

public class PageResult<T> {
    protected int page;
    protected int totalItems;
    protected T[] items;

    public PageResult(int page, int totalItems, T[] items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

    public PageResult() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public T[] getItems() {
        return items;
    }

    public void setItems(T[] items) {
        this.items = items;
    }
}
