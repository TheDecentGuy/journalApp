package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {
    @Value("${weather.APIKEY}")
    private String APIKEY;
    private static final String URL = "http://api.weatherapi.com/v1/current.json";


    private RestTemplate restTemplate;
    @Autowired
    private void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String uriString = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("key", APIKEY)
                .queryParam("q", city)
                .toUriString();

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(uriString, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
