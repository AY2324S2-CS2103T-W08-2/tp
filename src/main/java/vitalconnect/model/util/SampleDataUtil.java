package vitalconnect.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import vitalconnect.model.Clinic;
import vitalconnect.model.ReadOnlyClinic;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;
import vitalconnect.model.allergytag.AllergyTag;
import vitalconnect.model.person.medicalinformation.Height;
import vitalconnect.model.person.medicalinformation.Weight;

/**
 * Contains utility methods for populating {@code Clinic} with sample data.
 */
public class SampleDataUtil {

    /**
     * Retrieves an array of sample persons.
     *
     * @return an array of sample persons
     */
    public static Person[] getSamplePersons() {
        return new Person[]{
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends"), new Height("123.11"), new Weight("111")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends"), new Height("123.11"), new Weight("111")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours"), new Height("123.11"), new Weight("111")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family"), new Height("123.11"), new Weight("111")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates"), new Height("123.11"), new Weight("111")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"), new Height("123.11"), new Weight("111"))
        };
    }

    /**
     * Retrieves a sample clinic populated with sample persons.
     *
     * @return a sample clinic
     */
    public static ReadOnlyClinic getSampleClinic() {
        Clinic sampleAb = new Clinic();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the specified strings.
     *
     * @param strings the strings to include in the tag set
     * @return a tag set containing the specified strings
     */
    public static Set<AllergyTag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(AllergyTag::new)
                .collect(Collectors.toSet());
    }
}
