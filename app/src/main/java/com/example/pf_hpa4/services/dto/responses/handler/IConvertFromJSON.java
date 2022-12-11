package com.example.pf_hpa4.services.dto.responses.handler;

public interface IConvertFromJSON<T> {
    public T GetFromJSON(String json);
}
