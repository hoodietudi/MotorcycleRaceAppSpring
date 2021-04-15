package repository;

import domain.Participant;

import java.util.List;

public interface ParticipantIRepository extends IRepository<Long, Participant>{
    List<Participant> filterByTeam(String teamName);
}
