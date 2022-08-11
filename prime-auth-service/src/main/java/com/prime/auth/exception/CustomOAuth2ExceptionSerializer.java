package com.prime.auth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Arrays;

public class CustomOAuth2ExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {
    private static final long serialVersionUID = -1030200415299027494L;

    public CustomOAuth2ExceptionSerializer() {
        super(CustomOAuth2Exception.class);
    }

    @Override
    public void serialize(CustomOAuth2Exception value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("errors", Arrays.asList(value.getError()));
        jsonGenerator.writeEndObject();
    }
}
