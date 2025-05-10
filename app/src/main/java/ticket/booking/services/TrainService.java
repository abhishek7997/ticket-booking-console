package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TrainService {
    private static final String TRAINS_PATH = "app/src/main/java/ticket/booking/localDb/trains.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private List<Train> trains = new ArrayList<>();

    public TrainService() throws IOException {
        this.trains = loadTrains();
    }

    private List<Train> loadTrains() throws IOException {
        File file = new File(TRAINS_PATH);
        return objectMapper.readValue(file, new TypeReference<List<Train>>() {});
    }

    public Train fetchTrain(Long trainNumber) {
        return trains.stream().filter(train -> Objects.equals(train.getTrainNumber(), trainNumber)).findFirst().orElse(null);
    }

    public List<Train> fetchTrains(String source, String destination) {
        return trains.stream().filter(train -> filterTrain(train, source, destination)).toList();
    }

    private boolean filterTrain(Train train, String source, String destination) {
        int sourceIdx = train.getStations().indexOf(source.toLowerCase());
        int destIdx = train.getStations().indexOf(destination.toLowerCase());

        return sourceIdx != -1 && destIdx != -1 && sourceIdx < destIdx;
    }

    public void saveTrainlistToFile() throws IOException {
        File trainsFile = new File(TRAINS_PATH);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(trainsFile, trains);
    }
}
