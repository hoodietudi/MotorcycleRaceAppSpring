package motorcycleraces.domain;

import java.util.List;

public class Team extends Entity<String>{
    List<Participant> participants;

    public Team(String name, List<Participant> participants) {
        this.participants = participants;
        setId(name);
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
