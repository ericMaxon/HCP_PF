package com.example.pf_hpa4.services;

import com.example.pf_hpa4.configurations.retrofit.RetrofitConfig;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractService <T> {
    private Class<? extends T> Tc;
    private T API_SERVICE;

    @SuppressWarnings("unchecked")
    protected AbstractService() {
        //Anexo autor, esto fue agregado ya que no tenia forma de manejar una clase generica pero gracias a Dios
        // esta en stackOverFlow
        // https://stackoverflow.com/questions/3437897/how-do-i-get-a-class-instance-of-generic-type-t
        Tc = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected T getApiService(){
        if (API_SERVICE == null){
            API_SERVICE = RetrofitConfig.getInstance().create(Tc);
        }
        return API_SERVICE;
    }
}
