package ru.work.graduatework.Entity;

import java.util.List;

public class ResponseWrapperAds {
    private Integer count;
    private List<Ads> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Ads> getResults() {
        return results;
    }

    public void setResults(List<Ads> results) {
        this.results = results;
    }
}
