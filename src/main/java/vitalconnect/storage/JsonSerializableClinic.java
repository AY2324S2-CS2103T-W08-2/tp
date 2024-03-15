package vitalconnect.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import vitalconnect.commons.exceptions.IllegalValueException;
import vitalconnect.model.Clinic;
import vitalconnect.model.ReadOnlyClinic;
import vitalconnect.model.person.Person;

/**
 * An Immutable Clinic that is serializable to JSON format.
 */
@JsonRootName(value = "clinic")
class JsonSerializableClinic {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableClinic} with the given persons.
     */
    @JsonCreator
    public JsonSerializableClinic(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyClinic} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableClinic}.
     */
    public JsonSerializableClinic(ReadOnlyClinic source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this clinic into the model's {@code Clinic} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Clinic toModelType() throws IllegalValueException {
        Clinic clinic = new Clinic();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (clinic.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            clinic.addPerson(person);
        }
        return clinic;
    }

}
