package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherService {
    private static final String apiKey = "7ac78dc026694f7097195104252702";
    private static String url = "http://api.weatherapi.com/v1/current.json";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String uriString = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", apiKey)
                .queryParam("q", city)
                .toUriString();

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(uriString, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

//    public WeatherResponse setWeather(Users user) { this is demo code for performing POST to external api. Dont uncomment it will not run
//    HttpHeaders httpHeaders = new HttpHeaders();
//    httpHeaders.set("key",value);
//    HttpEntity<Users> requestEntity = new HttpEntity<>(user,httpHeaders);
//        ResponseEntity<WeatherResponse>response= restTemplate.exchange(uriString, HttpMethod.POST,requestEntity, WeatherResponse.class);
//        return response.getBody();
//    }
}
