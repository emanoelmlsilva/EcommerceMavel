package com.example.ecommercemarvel.model;

import java.util.List;

public class Data {

    private int limit;
    private List<Comic> results;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Comic> getResults() {
        return results;
    }

    public void setResults(List<Comic> results) {
        this.results = results;
    }
}
