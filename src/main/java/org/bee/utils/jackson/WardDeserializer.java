package org.bee.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.bee.hms.wards.Ward;
import org.bee.hms.wards.WardClassType;
import org.bee.hms.wards.WardFactory;

import java.io.IOException;

/**
 * Custom Jackson deserializer for {@link Ward} objects.
 * <p>
 * This deserializer converts a JSON object representing a ward into a {@link Ward} instance.
 * The expected JSON structure must contain:
 * <ul>
 *     <li><b>name</b> - the name of the ward (string)</li>
 *     <li><b>classType</b> - the class type of the ward (string, matching {@link WardClassType})</li>
 * </ul>
 * </p>
 *
 * <pre>
 * Example JSON:
 * {
 *     "name": "General Ward 1",
 *     "classType": "GENERAL"
 * }
 * </pre>
 *
 * This will be deserialized into a {@code Ward} instance using {@link WardFactory#getWard(String, WardClassType)}.
 */
public class WardDeserializer extends JsonDeserializer<Ward> {

    /**
     * Deserializes a {@link Ward} object from a JSON representation.
     * <p>
     * Reads the {@code name} and {@code classType} fields from the JSON and creates a {@link Ward}
     * using the {@link WardFactory}.
     * </p>
     *
     * @param p     The {@link JsonParser} for reading the JSON input.
     * @param ctxt  The {@link DeserializationContext} that can provide additional context for deserialization.
     * @return A {@link Ward} object representing the data in the JSON.
     * @throws IOException if the JSON cannot be parsed correctly or required fields are missing.
     */
    @Override
    public Ward deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String name = node.get("name").asText();
        String classTypeStr = node.get("classType").asText();

        WardClassType wardClassType = WardClassType.fromString(classTypeStr);

        return WardFactory.getWard(name, wardClassType);
    }
}
