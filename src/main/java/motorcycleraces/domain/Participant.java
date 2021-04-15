package motorcycleraces.domain;

import java.util.Objects;

public class Participant extends Entity<Long>{

    String firstName;
    String lastName;
    String team;
    float engineSize;
    Race race;

    private static long id = 0;


    public Participant(Long id,String firstName, String lastName, String team,float engineSize,Race race) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.engineSize = engineSize;
        this.team = team;
        this.race = race;

    }

    public Participant(String firstName, String lastName, String team,float engineSize,Race race) {

        this.setId(generateId());
        this.firstName = firstName;
        this.lastName = lastName;
        this.engineSize = engineSize;
        this.team = team;
        this.race = race;

    }

    private Long generateId() {
        id = id + 1;
        return id;
    }


    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public float getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(float engineSize) {
        this.engineSize = engineSize;
    }



    public static void setId(long id) {
        Participant.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant participant = (Participant) o;
        return Objects.equals(firstName, participant.firstName) &&
                Objects.equals(lastName, participant.lastName) &&
                Objects.equals(team, participant.team) &&
                Objects.equals(engineSize, participant.engineSize) &&
                Objects.equals(race, participant.race) &&
                Objects.equals(this.getId(), participant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), firstName,lastName,team,engineSize,race);
    }

}

