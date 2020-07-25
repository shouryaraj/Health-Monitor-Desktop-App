package Model.Address;

import java.util.List;

public class Address {
    /**
     * Address class to define the patient's address
     * Contains a street address (name and number), postcode and a city within which the street resides
     */
    private String street;
    private String postcode;
    private City city;


    public Address(String street, String postcode, City city) {
        this.street = street;
        this.postcode = postcode;
        this.city = city;
    }

    public Address(String street, String postcode, String extension) {
        this.street = street;
        this.postcode = postcode;
        String[] extensionArray = (extension.split("/"));
        String city = extensionArray[0];
        List<String> currentCityNames = Addresses.getCityNames();
        if (currentCityNames.contains(city)){
            int cityLocation = currentCityNames.indexOf(city);
            this.city = Addresses.getCities().get(cityLocation);
        }
        else{
            this.city = Addresses.addCity(extension);
        }

    }

    @Override
    public String toString() {
        return street.toString() + " " +city.toString();
    }
}
