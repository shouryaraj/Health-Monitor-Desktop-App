/**
 * Iterator for a list of patients
 */
package Model;
public class PatientIterator implements Iterator {
    private PatientList patientList;

    public PatientIterator(PatientList newPatientList) {
        this.patientList = newPatientList;
    }

    int index = 0;
    @Override
    // returns true unless the end of the collection has been reached
    public boolean hasNext() {
        return index != patientList.length();
    }

    @Override
    // returns the next Patient in the collection unless the end has been reached, when it returns null
    public Patient next() {
        if (hasNext()) {
            // return next in collection
            index += 1;
            return patientList.get(index-1);
        } else {
            return null;
        }
    }
}
