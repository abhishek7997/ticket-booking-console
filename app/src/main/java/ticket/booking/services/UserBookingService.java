package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    private User user = null;
    private List<User> users = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService() throws IOException {
        users = loadUsers();
    }

    private List<User> loadUsers() throws IOException {
        File usersFile = new File(USERS_PATH);
        return objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
    }

    public Boolean login(String username, String password) {
        Optional<User> foundUser = users.stream().filter(u -> StringUtils.equalsIgnoreCase(u.getName(), username) && UserServiceUtil.checkPassword(password, u.getHashedPassword())).findFirst();
        if (foundUser.isPresent()) {
            this.user = foundUser.get();
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public Boolean signup(User user) {
        try {
            this.user = user;
            users.add(user);
            saveUserlistToFile();
            return Boolean.TRUE;
        } catch (Exception e) {
            System.out.print("Exception during signup: " + e);
        }

        return Boolean.FALSE;
    }

    private void saveUserlistToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, users);
    }

    public Boolean cancelTicket(String ticketId) throws IllegalAccessException {
        if (user == null) {
            throw new IllegalAccessException("No user is logged in.");
        }

        Optional<Ticket> foundTicket = user.getBookedTickets().stream().filter(t -> StringUtils.equals(ticketId, t.getTicketId())).findFirst();
        if (foundTicket.isEmpty()) {
            System.out.printf("No ticket found with ticketId=" + ticketId);
            return Boolean.FALSE;
        }

        user.getBookedTickets().remove(foundTicket.get());
        try {
            saveUserlistToFile();
        } catch (IOException e) {
            System.out.print("Exception occurred while cancelling: " + e);
        }
        return Boolean.FALSE;
    }

    public void fetchBookings() {
        if (user != null) {
            user.displayTickets();
        } else {
            System.out.println("ERROR: No user logged in");
        }
    }

    public String getUserId() throws IllegalAccessException {
        if (user == null) {
            throw new IllegalAccessException("No user is logged in.");
        }

        return user.getUserId();
    }

    public void bookTicket(Ticket ticket) throws IllegalAccessException {
        if (user == null) {
            throw new IllegalAccessException("No user is logged in.");
        }

        user.getBookedTickets().add(ticket);
        try {
            saveUserlistToFile();
        } catch (IOException e) {
            System.out.print("Exception occurred while saving user configuration: " + e);
        }
    }
}
