package ru.work.graduatework.dto;

import java.util.Collection;

public class ResponseWrapper<A> {
    private int count;
    private Collection <A> result;

    public static <A> ResponseWrapper<A> of(Collection<A> results) {
        ResponseWrapper<A> responseWrapper= new ResponseWrapper<>();
        if (results == null) {
            return responseWrapper;
        }
        responseWrapper.result=results;
        responseWrapper.count=results.size();
        return responseWrapper;
    }


}
