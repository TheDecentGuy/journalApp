package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {
    private static final String API_KEY = "7ac78dc026694f7097195104252702";
    private static final String URL = "http://api.weatherapi.com/v1/current.json";


    private RestTemplate restTemplate;
    @Autowired
    private void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String uriString = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("key", API_KEY)
                .queryParam("q", city)
                .toUriString();

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(uriString, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
