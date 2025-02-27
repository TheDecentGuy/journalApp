package net.engineeringdigest.journalApp.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WeatherResponse {

    private Current current;
    @Data
    public static class Current{
        @JsonProperty("last_updated_epoch")
        public int lastUpdatedEpoch;
        @JsonProperty("last_updated")
        public String lastUpdated;
        @JsonProperty("temp_c")
        public double tempC;
        @JsonProperty("temp_f")
        public double tempF;
        @JsonProperty("is_day")
        public int isDay;

    }
}
