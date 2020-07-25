
package Model.Address;

/**
 * State class to define the state a patient is from
 * Contains a country within which the state resides
 */
public class State {


    private String name;
    private Country country;

    public State(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return name + " " + country;
    }
}
