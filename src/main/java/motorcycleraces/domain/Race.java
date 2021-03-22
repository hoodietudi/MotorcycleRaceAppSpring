package motorcycleraces.domain;

import java.util.List;

public class Race extends Entity<Long>{

    String location;
    String name;
    int maxEngineSize;
    List<Participant> participants;

    public Race(String location, String name, int maxEngineSize, List<Participant> participants) {
        this.location = location;
        this.name = name;
        this.maxEngineSize = maxEngineSize;
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxEngineSize() {
        return maxEngineSize;
    }

    public void setMaxEngineSize(int maxEngineSize) {
        this.maxEngineSize = maxEngineSize;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
