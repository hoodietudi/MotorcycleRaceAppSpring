package motorcycleraces.domain;

import java.util.List;
import java.util.Objects;

public class Team extends Entity<Long>{

    String name;

    public Team(Long id,String name) {
        this.setId(id);

        this.name = name;
    }

    public Team(String name) {

        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name) &&
                Objects.equals(this.getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(),name);
    }

}
