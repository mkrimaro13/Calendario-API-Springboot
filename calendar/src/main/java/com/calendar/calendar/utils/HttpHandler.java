package com.calendar.calendar.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.calendar.calendar.domain.dtos.FestivoDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class HttpHandler {
    private final String url;
    private final String apiPath;

    public HttpHandler(String url, String apiPath) {
        this.url = url;
        this.apiPath = apiPath;
    }

    public List<FestivoDto> getFestivos() {
        try {
            // Creo del objeto request que se va a enviar.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(
                            URI.create(this.url + this.apiPath))
                    .GET()
                    .build();
            // Creación del objeto cliente que va a manejar el request
            HttpClient client = HttpClient.newBuilder()
                    .version(Version.HTTP_2)
                    .build();
            // Creación del objeto respuesta que va a almacenar la respuesta en tipo String
            // sin importar como llegue
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<FestivoDto> festivos = mapper.readValue(response.body(), new TypeReference<List<FestivoDto>>() {
            });
            return festivos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    };

}
