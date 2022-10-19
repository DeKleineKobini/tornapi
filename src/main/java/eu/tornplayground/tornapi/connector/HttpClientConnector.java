package eu.tornplayground.tornapi.connector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.Version.HTTP_1_1;
import static java.net.http.HttpClient.Version.HTTP_2;

public class HttpClientConnector implements ApiConnector {

    private final HttpClient client;

    public HttpClientConnector(boolean useHttp2) {
        client = HttpClient.newBuilder()
                .version(useHttp2 ? HTTP_2 : HTTP_1_1)
                .build();
    }

    public HttpClientConnector() {
        this(true);
    }

    @Override
    public JsonNode connect(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.body());
    }

}
