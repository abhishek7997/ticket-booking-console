package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @JsonProperty("ticket_id")
    private String ticketId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("source")
    private String source;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("date_of_travel")
    private String travelDate;
    @JsonProperty("train")
    private Train train;
    @JsonProperty("ticket_info")
    private String ticketInfo;

    public Ticket(String ticketId, String userId, String source, String destination, String travelDate, Train train) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.travelDate = travelDate;
        this.train = train;
        this.ticketInfo = ticketId + "$" + userId + "$" + source + "$" + destination + "$" + travelDate + "$" + train.getTrainNumber();
    }
}
