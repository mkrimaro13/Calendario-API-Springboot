package com.calendar.calendar.core.interfaces.services;

import java.time.LocalDate;
import java.util.List;

import com.calendar.calendar.domain.Calendario;

public interface ICalendarioServicio {

    // Cambio a LocalDate para no tener que manejar horas.
    public List<LocalDate> listarFestivos(int año);

    public boolean agregarCalendario(int año);

    public List<Calendario> listarCalendario(int año);
}
