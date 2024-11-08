package com.calendar.calendar.core.interfaces.services;

import java.util.List;

import com.calendar.calendar.domain.Calendario;
import com.calendar.calendar.domain.dtos.FestivoDto;

public interface ICalendarioServicio {

    // Cambio a LocalDate para no tener que manejar horas.
    public List<FestivoDto> listarFestivos(int año);

    public boolean agregarCalendario(int año);

    public List<Calendario> listarCalendario(int año);
}
