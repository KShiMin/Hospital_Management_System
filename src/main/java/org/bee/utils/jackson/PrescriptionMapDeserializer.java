package org.bee.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bee.hms.medical.Medication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom Jackson deserializer for converting a JSON object into a {@code Map<Medication, Integer>}.
 * <p>
 * The JSON file is expected to be an object where the keys are medication codes (as strings)
 * and the values are integers representing the quantity of the medication.
 * </p>
 * 
 * <pre>
 * Example JSON:
 * {
 *     "MED001": 5,
 *     "MED002": 10
 * }
 * </pre>
 * 
 * This will be deserialized into:
 * <pre>
 * Map&lt;Medication, Integer&gt; map = {
 *     Medication(MED001) -> 5,
 *     Medication(MED002) -> 10
 * }
 * </pre>
 */
public class PrescriptionMapDeserializer extends JsonDeserializer<Map<Medication, Integer>> {

    /**
     * Deserializes a JSON object into a {@code Map<Medication, Integer>}.
     * <p>
     * Expects the current token to be either {@link JsonToken#VALUE_NULL} or {@link JsonToken#START_OBJECT}.
     * If the JSON is empty or null, returns an empty map.
     * Otherwise, each key-value pair is converted into a {@code Medication} and its corresponding quantity.
     * </p>
     *
     * @param p     The {@link JsonParser} used to read the JSON input.
     * @param ctxt  The {@link DeserializationContext} that provides access to additional deserialization configuration.
     * @return A {@code Map<Medication, Integer>} containing the medications and their quantities.
     * @throws IOException if the JSON structure is invalid or cannot be parsed correctly.
     */
    @Override
    public Map<Medication, Integer> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<Medication, Integer> result = new HashMap<>();

        if (p.currentToken() == JsonToken.VALUE_NULL) {
            return result;
        }

        if (p.currentToken() != JsonToken.START_OBJECT) {
            throw new IOException("Expected START_OBJECT token for medication map, got " + p.currentToken());
        }

        while (p.nextToken() != JsonToken.END_OBJECT) {
            String drugCode = p.getCurrentName();
            p.nextToken();
            int quantity = p.getIntValue();

            Medication medication = Medication.createFromCode(drugCode);
            result.put(medication, quantity);
        }

        return result;
    }
}
