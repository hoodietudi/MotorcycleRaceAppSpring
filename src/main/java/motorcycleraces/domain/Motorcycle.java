package motorcycleraces.domain;

public class Motorcycle extends Entity<Long>{

    String brand;
    String type;
    int fabricationDate;
    Engine engine;

    public Motorcycle(String brand, String type, int fabricationDate, Engine engine) {
        this.brand = brand;
        this.type = type;
        this.fabricationDate = fabricationDate;
        this.engine = engine;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFabricationDate() {
        return fabricationDate;
    }

    public void setFabricationDate(int fabricationDate) {
        this.fabricationDate = fabricationDate;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
