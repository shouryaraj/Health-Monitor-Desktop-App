package Model.Vitals;

/**
 * Abstract biological parameter class to force all biological parameters to be read the same way
 */
public abstract class BiologicalParameter {
    public abstract Object getMeasurement();
    public abstract Object getEffectiveTimeDate();
    public abstract String toStringTime();

}
