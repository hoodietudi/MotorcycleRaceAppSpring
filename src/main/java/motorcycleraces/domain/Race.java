package motorcycleraces.domain;

import java.util.List;
import java.util.Objects;

public class Race extends Entity<Long>{

    String location;
    String name;
    float maxEngineSize;
    int nrParticipants;


    public Race(Long id, String location, String name, float maxEngineSize, int nrParticipants) {
        this.setId(id);
        this.location = location;
        this.name = name;
        this.maxEngineSize = maxEngineSize;
        this.nrParticipants = nrParticipants;
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

    public float getMaxEngineSize() {
        return maxEngineSize;
    }

    public void setMaxEngineSize(float maxEngineSize) {
        this.maxEngineSize = maxEngineSize;
    }

    public int getNrParticipants() {
        return nrParticipants;
    }

    public void setNrParticipants(int nrParticipants) {
        this.nrParticipants = nrParticipants;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return Objects.equals(location, race.location) &&
                Objects.equals(name, race.name) &&
                Objects.equals(maxEngineSize, race.maxEngineSize) &&
                Objects.equals(nrParticipants, race.nrParticipants) &&
                Objects.equals(this.getId(), race.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(),location,name,maxEngineSize,nrParticipants);
    }
}
