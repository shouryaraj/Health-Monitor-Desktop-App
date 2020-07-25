package Model.src.main.java.test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;

public class test {
    public static void main(String[] args) {

        // Create a context
        FhirContext ctx = FhirContext.forR4();

        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir");

        // Read a patient with the given ID
        Patient patient = client.read().resource(Patient.class).withId("1").execute();

        Bundle observation = client.search()
                .byUrl("Observation?patient=3689&code=2093-3&_sort=date&_count=13")
                .encodedJson()
                .returnBundle(Bundle.class)
                .execute();

        List<Bundle.BundleEntryComponent> entry = observation.getEntry();
        System.out.println(entry.get(1).getResource());

/*        observation.getEntry().forEach(Entry -> System.out.println(Entry.getResource().));

        List entry = observation.getEntry();
        Object item = entry.;
        String itemString = item;
        System.out.println(item);*/

        // Print the output
        String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(observation);
        //System.out.println(string);

    }
}
