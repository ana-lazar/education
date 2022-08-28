import domain.Race;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class RaceRestClient {
    public static final String URL = "http://localhost:8080/races";

    private final RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public List<Race> getAll() {
        Race[] races = execute(() -> restTemplate.getForObject(URL, Race[].class));
        return Arrays.asList(races);
    }

    public Race getById(int id) {
        String urlGetById = String.format("%s/%d", URL, id);
        ResponseEntity<Race> response = execute(() -> restTemplate.getForEntity(urlGetById, Race.class));
        return response.getBody();
    }

    public Race create(Race race) {
        return execute(() -> restTemplate.postForObject(URL, race, Race.class));
    }

    public void update(Race race) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, race.getId()), race);
            return null;
        });
    }

    public void delete(int id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
