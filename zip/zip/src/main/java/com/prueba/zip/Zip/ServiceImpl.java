package com.prueba.zip.Zip;

import org.springframework.stereotype.Service;

@Service
public class ServiceImpl implements zipService {
    private Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Ruta create(Ruta ruta) {
        return ruta;
    }

}
