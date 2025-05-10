package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Train {
    @JsonProperty("train_id")
    private String trainId;
    @JsonProperty("train_no")
    private Long trainNumber;
    @JsonProperty("seats")
    private List<List<Integer>> seats;
    @JsonProperty("station_times")
    private Map<String, String> stationSchedules;
    @JsonProperty("stations")
    private List<String> stations;
    @JsonProperty("train_info")
    private String trainInfo;
}
