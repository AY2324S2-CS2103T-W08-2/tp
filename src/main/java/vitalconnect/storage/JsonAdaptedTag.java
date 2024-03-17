package vitalconnect.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import vitalconnect.commons.exceptions.IllegalValueException;
import vitalconnect.model.allergytag.AllergyTag;

/**
 * Jackson-friendly version of {@link AllergyTag}.
 */
class JsonAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(AllergyTag source) {
        tagName = source.allergy;
    }

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public AllergyTag toModelType() throws IllegalValueException {
        if (!AllergyTag.isValidTagName(tagName)) {
            throw new IllegalValueException(AllergyTag.MESSAGE_CONSTRAINTS);
        }
        return new AllergyTag(tagName);
    }

}
