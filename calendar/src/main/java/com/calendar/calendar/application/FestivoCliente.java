package com.calendar.calendar.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.calendar.calendar.domain.DTOs.FestivoDto;

@Service
public class FestivoCliente {
    @Autowired
    private RestTemplate restTemplate;

    public List<FestivoDto> obtenerFestivos(int año, String url) {
        // String url = "http://localhost:3030/festivos/" + año;
        ResponseEntity<String> response = restTemplate.exchange(url + año, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {

                });
        String[] arr_fechas_tmp = response.getBody().replace("[", "").replace("]", "").replace("\"", "")
                .split(",");
        List<FestivoDto> fechas = new ArrayList<>();
        for (String fecha : arr_fechas_tmp) {
            fechas.add(new FestivoDto(LocalDate.parse(fecha)));
        }
        return fechas;
    }
}
