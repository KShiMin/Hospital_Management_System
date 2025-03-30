package org.bee.utils.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bee.hms.wards.*;

import java.io.IOException;

/**
 * Custom Jackson serializer for {@link Ward} and its subclasses.
 * <p>
 * This serializer converts a {@link Ward} instance into a JSON object,
 * including type information, name, daily rate, and an inferred {@link WardClassType} based on the concrete
 * ward type and its daily rate.
 * </p>
 *
 * <p>
 * The following concrete types are supported:
 * <ul>
 *     <li>{@link LabourWard}</li>
 *     <li>{@link ICUWard}</li>
 *     <li>{@link DaySurgeryWard}</li>
 *     <li>{@link GeneralWard}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example output JSON:
 * <pre>
 * {
 *     "type": "labour",
 *     "name": "Labour Ward A1",
 *     "dailyRate": 1500,
 *     "classType": "LABOUR_CLASS_A"
 * }
 * </pre>
 * </p>
 */
public class WardSerializer extends JsonSerializer<Ward> {

    /**
     * Serializes a {@link Ward} into a JSON object.
     * <p>
     * The serialization includes:
     * <ul>
     *     <li>Ward type (e.g., labour, icu, daySurgery, general)</li>
     *     <li>Ward name</li>
     *     <li>Ward daily rate</li>
     *     <li>Inferred {@link WardClassType} if it can be determined</li>
     * </ul>
     * </p>
     *
     * @param ward         The {@link Ward} instance to serialize.
     * @param gen          The {@link JsonGenerator} used to output the JSON.
     * @param serializers  The {@link SerializerProvider} (not used directly).
     * @throws IOException if an I/O error occurs during serialization.
     */
    @Override
    public void serialize(Ward ward, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        // Write the type information that matches the names in @JsonSubTypes
        if (ward instanceof LabourWard) {
            gen.writeStringField("type", "labour");
        } else if (ward instanceof ICUWard) {
            gen.writeStringField("type", "icu");
        } else if (ward instanceof DaySurgeryWard) {
            gen.writeStringField("type", "daySurgery");
        } else if (ward instanceof GeneralWard) {
            gen.writeStringField("type", "general");
        }

        // Write basic properties
        gen.writeStringField("name", ward.getWardName());
        gen.writeNumberField("dailyRate", ward.getDailyRate());

        // Determine the WardClassType based on ward type and daily rate
        WardClassType inferredClassType = inferWardClassType(ward);
        if (inferredClassType != null) {
            gen.writeStringField("classType", inferredClassType.name());
        }

        // Write beds information if needed
        // ... additional serialization logic

        gen.writeEndObject();
    }

    /**
     * Infers the {@link WardClassType} based on the {@link Ward} type and its daily rate.
     * <p>
     * This method uses hardcoded mappings to associate daily rates with known {@code WardClassType} values.
     * Returns {@code null} if no match is found.
     * </p>
     *
     * @param ward The {@link Ward} instance to infer the class type for.
     * @return The corresponding {@link WardClassType} or {@code null} if unknown.
     */
    private WardClassType inferWardClassType(Ward ward) {
        double rate = ward.getDailyRate();

        switch (ward) {
            case LabourWard ignored1 -> {
                if (rate == 1500) return WardClassType.LABOUR_CLASS_A;
                if (rate == 1000) return WardClassType.LABOUR_CLASS_B1;
                if (rate == 500) return WardClassType.LABOUR_CLASS_B2;
                if (rate == 250) return WardClassType.LABOUR_CLASS_C;
            }
            case ICUWard ignored -> {
                return WardClassType.ICU;
            }
            case DaySurgeryWard ignored -> {
                if (rate == 300) return WardClassType.DAYSURGERY_CLASS_SEATER;
                if (rate == 250) return WardClassType.DAYSURGERY_CLASS_COHORT;
                if (rate == 200) return WardClassType.DAYSURGERY_CLASS_SINGLE;
            }
            case GeneralWard ignored -> {
                if (rate == 500) return WardClassType.GENERAL_CLASS_A;
                if (rate == 250) return WardClassType.GENERAL_CLASS_B1;
                if (rate == 200) return WardClassType.GENERAL_CLASS_B2;
                if (rate == 150) return WardClassType.GENERAL_CLASS_C;
            }
            default -> {
            }
        }

        return null;
    }
}
