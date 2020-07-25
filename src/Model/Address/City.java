package Model.Address;

public class City {
    /**
     * City class to define the city a patient is from
     * Contains a state within which the city resides
     */

    private String name;
    private State state;

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }

    @Override
    public String toString() {
        return name +" " + state;
    }
}
