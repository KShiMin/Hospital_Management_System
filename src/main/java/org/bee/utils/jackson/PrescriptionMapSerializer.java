package org.bee.utils.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bee.hms.medical.Medication;

import java.io.IOException;
import java.util.Map;

/**
 * Custom Jackson serializer for converting a {@code Map<Medication, Integer>} into a JSON object.
 * <p>
 * The map is serialized into a JSON object where:
 * <ul>
 *     <li>Each key is the {@code drugCode} of the {@link Medication} as a string.</li>
 *     <li>Each value is the corresponding quantity as an integer.</li>
 * </ul>
 * </p>
 *
 * <pre>
 * Example Map:
 * {
 *     Medication("MED001") -> 5,
 *     Medication("MED002") -> 10
 * }
 * </pre>
 * 
 * Will be serialized into:
 * 
 * <pre>
 * {
 *     "MED001": 5,
 *     "MED002": 10
 * }
 * </pre>
 */
public class PrescriptionMapSerializer extends JsonSerializer<Map<Medication, Integer>> {

    /**
     * Serializes a {@code Map<Medication, Integer>} into a JSON object.
     * <p>
     * Writes {@code null} if the input map is {@code null}.
     * Otherwise, each {@link Medication#getDrugCode()} is used as the JSON field name
     * and the integer quantity is used as the value.
     * </p>
     *
     * @param value        The map of {@link Medication} objects and their quantities to serialize.
     * @param gen          The {@link JsonGenerator} used to output the JSON content.
     * @param serializers  The {@link SerializerProvider} that can be used for custom serializers (not used here).
     * @throws IOException if an I/O error occurs during serialization.
     */
    @Override
    public void serialize(Map<Medication, Integer> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        gen.writeStartObject();
        for (Map.Entry<Medication, Integer> entry : value.entrySet()) {
            gen.writeNumberField(entry.getKey().getDrugCode(), entry.getValue());
        }
        gen.writeEndObject();
    }
}
