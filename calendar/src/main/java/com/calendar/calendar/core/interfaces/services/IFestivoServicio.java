package com.calendar.calendar.core.interfaces.services;

import java.util.List;

import com.calendar.calendar.domain.DTOs.FestivoDto;

public interface IFestivoServicio {
    public List<FestivoDto> obtenerFestivos(int año);
}
