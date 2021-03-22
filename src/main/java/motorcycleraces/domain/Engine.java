package motorcycleraces.domain;

public class Engine extends Entity<Long>{

    int capacity;
    int nrOfCylindres;
    String placement;

    public Engine(int capacity, int nrOfCylindres, String placement) {
        this.capacity = capacity;
        this.nrOfCylindres = nrOfCylindres;
        this.placement = placement;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNrOfCylindres() {
        return nrOfCylindres;
    }

    public void setNrOfCylindres(int nrOfCylindres) {
        this.nrOfCylindres = nrOfCylindres;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
}
