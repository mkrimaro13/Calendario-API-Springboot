package com.calendar.calendar.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpHandler {
    private final String url;
    private final String apiPath;

    public HttpHandler(String url, String apiPath) {
        this.url = url;
        this.apiPath = apiPath;
    }

    public String getRequest() {
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
            // Creación del objeto respuesta que va a almacenar la respuesta en tipo String sin importar como llegue
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return body;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    };

}
