package com.devices.Fixtures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;

import java.util.List;

public class JsonPatchFixture {

    public static JsonPatch updateField(String field, String value) throws JsonProcessingException {
        JsonPatchOperation jsonPatchOperation = new ReplaceOperation(JsonPointer.of(field), new ObjectMapper().readTree(value));
        return new JsonPatch(List.of(jsonPatchOperation));
    }
}
