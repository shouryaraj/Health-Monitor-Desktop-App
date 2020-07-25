package Model.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all of the addresses currently created
 */
public class Addresses {

    private static List<Country> countries = new ArrayList<>();
    private static List<String> countryNames = new ArrayList<>();
    private static List<State> states = new ArrayList<>();
    private static List<String> stateNames = new ArrayList<>();
    private static List<City> cities = new ArrayList<>();
    private static List<String> cityNames = new ArrayList<>();

    public static List<City> getCities() {
        return cities;
    }
    public static List<String> getCityNames() {
        return cityNames;
    }

    public static City addCity(String extension) {
        City newCity;

        String[] extensionArray = (extension.split("/"));
        String city = extensionArray[0];
        String state = extensionArray[1];
        if (stateNames.contains(state)){
            int stateLocation = stateNames.indexOf(state);
            newCity = new City(city,states.get(stateLocation));
        }
        else{
            newCity = new City(city,addState(extension));
        }
        cities.add(newCity);
        return newCity;
    }

    public static State addState(String extension) {
        State newState;

        String[] extensionArray = (extension.split("/"));
        String city = extensionArray[0];
        String state = extensionArray[1];
        String country = extensionArray[2];
        if (countryNames.contains(country)){
            int countryLocation = countryNames.indexOf(country);
            newState = new State(state, countries.get(countryLocation));
        }
        else{
            newState = new State(state,new Country(country));
        }
        states.add(newState);
        return newState;
    }
}
