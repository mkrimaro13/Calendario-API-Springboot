package com.calendar.calendar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calendar.calendar.core.interfaces.services.IFestivoServicio;
import com.calendar.calendar.domain.DTOs.FestivoDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/festivos")
public class FestivoControlador {

    private IFestivoServicio servicio;

    public FestivoControlador(IFestivoServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{año}")
    public List<FestivoDto> listar(@PathVariable int año) {
        return servicio.obtenerFestivos(año);
    }

}
