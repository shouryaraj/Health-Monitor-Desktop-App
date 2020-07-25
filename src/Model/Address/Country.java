package Model.Address;

import java.util.ArrayList;
import java.util.List;

public class Country {
    /**
     * Country class to define the country a patient is from
     */

    private String name;

    public Country(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
