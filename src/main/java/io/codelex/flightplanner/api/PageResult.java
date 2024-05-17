package io.codelex.flightplanner.api;

import io.codelex.flightplanner.flight.Flight;

public class PageResult {
    protected int page;
    protected int totalItems;
    protected Flight[] items;

    public PageResult(int page, int totalItems, Flight[] items) {
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

    public Flight[] getItems() {
        return items;
    }

    public void setItems(Flight[] items) {
        this.items = items;
    }
}
