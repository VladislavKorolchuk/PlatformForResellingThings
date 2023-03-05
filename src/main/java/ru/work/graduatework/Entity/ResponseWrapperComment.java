package ru.work.graduatework.Entity;

import java.util.List;

public class ResponseWrapperComment {
    private Integer count;
    private List<Comment> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Comment> getResults() {
        return results;
    }

    public void setResults(List<Comment> results) {
        this.results = results;
    }
}
