package motorcycleraces.repository;

public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}