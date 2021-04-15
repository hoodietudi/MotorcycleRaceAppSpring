package repository;

import domain.Team;

public interface TeamIRepository extends IRepository<Long, Team>{
    Team findOne(String teamName);

    //long getMaxId();

}
