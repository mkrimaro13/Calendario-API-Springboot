package com.calendar.calendar.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.calendar.core.interfaces.services.IFestivoServicio;
import com.calendar.calendar.domain.DTOs.FestivoDto;

@Service
public class FestivoServicio implements IFestivoServicio {

    @Autowired
    private FestivoCliente cliente;

    private String url = "http://localhost:3030/festivos/";

    @Override
    public List<FestivoDto> obtenerFestivos(int año) {
        return cliente.obtenerFestivos(año, url);
    }

}
