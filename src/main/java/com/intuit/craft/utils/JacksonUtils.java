package com.intuit.craft.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;

/**
 * The type Jackson utils.
 */
@Component
public class JacksonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtils.class);
    private static final String CONVERSION_EXCEPTION_MSG = "Issue seems with entered data. Please verify and try again";
    private static final String ERROR_KEY = "errors";

    private ObjectMapper mapper;
    private ObjectMapper nonNullMapper;

    /**
     * Init mapper.
     */
    @PostConstruct
    void initMapper() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        nonNullMapper = new ObjectMapper();
        nonNullMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        nonNullMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        nonNullMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        nonNullMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        nonNullMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        nonNullMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Object to json json node.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @return json node
     * @throws IOException
     */
    public <T> JsonNode objectToJson(T t) {
        JsonNode jsonNode = null;
        try {
            String jsonInString = mapper.writeValueAsString(t);
            jsonNode = mapper.readTree(jsonInString);
        } catch (IOException e) {
            ObjectNode errorObj = mapper.createObjectNode();
            errorObj.put(ERROR_KEY, CONVERSION_EXCEPTION_MSG);
            jsonNode = errorObj;
        }
        return jsonNode;
    }

    /**
     * Object to json str string.
     *
     * @param <T>            the type parameter
     * @param t              the t
     * @param includeNonNull the include non null
     * @return string
     * @throws IOException
     */
    public <T> String objectToJsonStr(T t, boolean includeNonNull) {
        String jsonInString = null;
        try {
            if (t instanceof String) {
                return String.valueOf(t);
            }
            jsonInString = (includeNonNull) ? mapper.writeValueAsString(t) : nonNullMapper.writeValueAsString(t);
        } catch (IOException e) {
            LOGGER.error(CONVERSION_EXCEPTION_MSG, e);
        }
        return jsonInString;
    }

    /**
     * Object to json str string.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @return string
     * @throws IOException
     */
    public <T> String objectToJsonStr(T t) {
        return objectToJsonStr(t, true);
    }


    /**
     * Method to convert a json string to java object
     *
     * @param <T>          the type parameter
     * @param jsonInString : json string
     * @param classType    : Java class type
     * @return : Converted java object of type classType
     */
    public <T> T jsonStrToObject(String jsonInString, Class<T> classType) {
        T convertedObj = null;
        try {
            if (!Objects.toString(jsonInString, "").trim().isEmpty()) {
                convertedObj = mapper.readValue(jsonInString, classType);
            }
        } catch (IOException e) {
            LOGGER.error(CONVERSION_EXCEPTION_MSG, e);
        }
        return convertedObj;
    }


    /**
     * String to json json node.
     *
     * @param jsonInString the json in string
     * @return json node
     * @throws IOException
     */
    public JsonNode stringToJson(String jsonInString) {
        JsonNode jsonNode = null;
        try {
            if (StringUtils.hasText(jsonInString)) {
                jsonNode = mapper.readTree(jsonInString);
            }
        } catch (IOException e) {
            ObjectNode errorObj = mapper.createObjectNode();
            errorObj.put(ERROR_KEY, CONVERSION_EXCEPTION_MSG);
            jsonNode = errorObj;
        }
        return jsonNode;
    }

    /**
     * String to object node object node.
     *
     * @param jsonInString the json in string
     * @return the object node
     */
    public ObjectNode stringToObjectNode(String jsonInString) {
        ObjectNode objectNode = null;
        try {
            objectNode = (ObjectNode) mapper.readTree(jsonInString);
        } catch (IOException e) {
            ObjectNode errorObj = mapper.createObjectNode();
            errorObj.put(ERROR_KEY, CONVERSION_EXCEPTION_MSG);
            objectNode = errorObj;
        }
        return objectNode;
    }


    /**
     * Is empty json boolean.
     *
     * @param jsonStr the json str
     * @return the boolean
     */
    public boolean isEmptyJson(String jsonStr) {
        JsonNode jsonNode = jsonStrToObject(jsonStr, JsonNode.class);
        if (jsonNode == null) {
            return true;
        }
        return jsonNode.isEmpty();
    }

    /**
     * Method to convert a java object to java object
     *
     * @param <T>       the type parameter
     * @param object    the object
     * @param classType : Java class type
     * @return : Converted java object of type classType
     */
    public <T> T objectToObject(Object object, Class<T> classType) {
        T convertedObj = null;
        try {
            convertedObj = mapper.convertValue(object, classType);
        } catch (Exception e) {
            LOGGER.error(CONVERSION_EXCEPTION_MSG, e);
        }
        return convertedObj;
    }


}
