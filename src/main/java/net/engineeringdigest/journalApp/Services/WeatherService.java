package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Cache.AppCache;
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

    @Autowired
    private AppCache appCache;


    private RestTemplate restTemplate;
    @Autowired
    private void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${weather.APIKEY}")
    private String APIKEY;


    public WeatherResponse getWeather(String city) {
        String uriString = UriComponentsBuilder.fromHttpUrl(appCache.APP_CACHE.get("weatherAPI"))
                .queryParam("key", APIKEY)
                .queryParam("q", city)
                .toUriString();

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(uriString, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
