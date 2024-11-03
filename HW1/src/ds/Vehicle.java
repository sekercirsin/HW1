package ds;

public class Vehicle extends CargoPackage {
    private DoublyLinkedList<CargoPackage> cargoPackages;

    private float priority;

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public Vehicle(String vehicleId, float priority) {
        super(vehicleId);
        cargoPackages = new DoublyLinkedList<>();
        this.priority = priority;
    }

    public void loadCargo(CargoPackage cargo) {
        cargoPackages.addLast(cargo);
    }

    public CargoPackage unloadCargo() {
        return cargoPackages.removeLast();
    }
}
