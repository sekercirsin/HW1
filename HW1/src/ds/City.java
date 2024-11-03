package ds;

public class City {
    private String name;
    private Stack<CargoPackage> packages;
    private Queue<Vehicle> vehicles;

    public City(String name) {
        this.name = name;
        packages = new Stack<>();
        vehicles = new Queue<>();
    }

    public String getName() {
        return name;
    }

    public Stack<CargoPackage> getPackages() {
        return packages;
    }

    public Queue<Vehicle> getVehicles() {
        return vehicles;
    }
}
