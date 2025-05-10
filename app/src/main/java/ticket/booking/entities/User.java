package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ticket.booking.utils.UserServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class User {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private String password;
    @JsonProperty("hashed_password")
    private String hashedPassword;
    @JsonProperty("tickets_booked")
    private List<Ticket> bookedTickets;

    public User(String name, String password) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.hashedPassword = UserServiceUtil.hash(password);
        this.bookedTickets = new ArrayList<>();
    }

    public void displayTickets() {
        if (bookedTickets.isEmpty()) {
            System.out.println("No tickets booked.");
        } else {
            for (Ticket ticket : bookedTickets) {
                System.out.println(ticket);
            }
        }
    }
}
