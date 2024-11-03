
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ds.CargoPackage;
import ds.City;
import ds.DoublyLinkedList;
import ds.Vehicle;

public class App {
    public static void main(String[] args) throws Exception {
        // Read cities as string from file
        List<String> cityStrings = Files.readAllLines(Paths.get(args[0]));

        // Read city strings into City objects for each city string
        City firstCity = new City(cityStrings.get(0));
        City secondCity = new City(cityStrings.get(1));
        City thirdCity = new City(cityStrings.get(2));

        // Read package strings from package file
        List<String> packagesStrings = Files.readAllLines(Paths.get(args[1]));

        // Assign packages to relevant cities' stacks
        for (String packageString : packagesStrings) {
            String packageId = packageString.split(" ")[0];
            String city = packageString.split(" ")[1];

            if (city.equals(firstCity.getName()))
                firstCity.getPackages().push(new CargoPackage(packageId));

            else if (city.equals(secondCity.getName()))
                secondCity.getPackages().push(new CargoPackage(packageId));

            else {
                thirdCity.getPackages().push(new CargoPackage(packageId));

            }
        }

        // Read vehicles as strings from vehicle file
        List<String> vehicleStrings = Files.readAllLines(Paths.get(args[2]));

        List<Vehicle> firstVehicleStrings = new ArrayList<Vehicle>();
        List<Vehicle> secondVehicleStrings = new ArrayList<Vehicle>();
        List<Vehicle> thirdVehicleStrings = new ArrayList<Vehicle>();

        // Assign each string representation of vehicle to relevant list
        for (String vehicleString : vehicleStrings) {
            String vehicleId = vehicleString.split(" ")[0];
            String city = vehicleString.split(" ")[1];
            float priority = Float.parseFloat(vehicleString.split(" ")[2]);

            if (city.equals(firstCity.getName()))
                firstVehicleStrings.add(new Vehicle(vehicleId, priority));

            else if (city.equals(secondCity.getName()))
                secondVehicleStrings.add(new Vehicle(vehicleId, priority));

            else {
                thirdVehicleStrings.add(new Vehicle(vehicleId, priority));

            }
        }

        // Define a comparator to compare vehicles based on priority
        Comparator<Vehicle> vehicleComparator = new Comparator<Vehicle>() {

            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return Float.compare(o1.getPriority(), o2.getPriority());

            }

        };

        // Sort vehicles in each city in ascending order before enqueuing them
        Collections.sort(firstVehicleStrings, vehicleComparator);
        Collections.sort(secondVehicleStrings, vehicleComparator);
        Collections.sort(thirdVehicleStrings, vehicleComparator);

        // Enqueue vehicles to queues of each city
        firstVehicleStrings.forEach(vehicle -> firstCity.getVehicles().enqueue(vehicle));
        secondVehicleStrings.forEach(vehicle -> firstCity.getVehicles().enqueue(vehicle));
        thirdVehicleStrings.forEach(vehicle -> firstCity.getVehicles().enqueue(vehicle));

        // Read mission directives from file
        String[] missionDirectives = Files.readAllLines(Paths.get(args[3])).get(0).split(",");

        String[] cityOrder = missionDirectives[0].split("-");

        String sourceString = cityOrder[0];
        String middleString = cityOrder[1];
        String destString = cityOrder[2];

        City sourceCity = sourceString.equals(firstCity.getName()) ? firstCity
                : sourceString.equals(secondCity.getName()) ? secondCity : thirdCity;
        City middleCity = middleString.equals(firstCity.getName()) ? firstCity
                : middleString.equals(secondCity.getName()) ? secondCity : thirdCity;

        City destCity = destString.equals(firstCity.getName()) ? firstCity
                : destString.equals(secondCity.getName()) ? secondCity : thirdCity;

        DoublyLinkedList<CargoPackage> convoy = new DoublyLinkedList<CargoPackage>();

        int sourceLoad = Integer.parseInt(cityOrder[3]);
        int middleLoad = Integer.parseInt(cityOrder[4]);

        convoy.addLast(sourceCity.getVehicles().dequeue());

        for (int i = 0; i < sourceLoad; i++) {
            convoy.addLast(sourceCity.getPackages().pop());
        }

        // Arrive at middleCity

        for (int i = 0; i < middleLoad; i++) {
            convoy.addLast(middleCity.getPackages().pop());
        }

        for (int i = 1; i < missionDirectives.length; i++) {
            int indexToRemove = Integer.parseInt(missionDirectives[i]);
            CargoPackage packageToRemove = convoy.removeAt(indexToRemove + 1);

            middleCity.getPackages().push(packageToRemove);
        }

        // Arrive at destination

        // Remove vehicle and add it to vehicle queue in dest city
        Vehicle vehicle = (Vehicle) convoy.removeAt(0);

        destCity.getVehicles().enqueue(vehicle);

        while (convoy.isEmpty()) {
            CargoPackage cargoPackage = convoy.removeLast();
            destCity.getPackages().push(cargoPackage);
        }

    }
}
