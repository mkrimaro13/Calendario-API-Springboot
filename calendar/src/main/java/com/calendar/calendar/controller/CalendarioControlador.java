package com.calendar.calendar.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calendar.calendar.core.interfaces.services.ICalendarioServicio;
import com.calendar.calendar.domain.Calendario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioControlador {
    @Autowired
    private ICalendarioServicio servicio;

    @GetMapping("/festivos/{año}")
    public List<LocalDate> listarFestivos(@PathVariable int año) {
        return servicio.listarFestivos(año);
    }

    @GetMapping("/generar/{año}")
    public boolean agregarCalendario(@PathVariable int año) {
        return servicio.agregarCalendario(año);
    }

    @GetMapping("/listar/{año}")
    public List<Calendario> listarCalendario(@PathVariable int año) {
        return servicio.listarCalendario(año);
    }

}
