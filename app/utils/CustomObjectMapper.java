package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.Json;

/**
 * Created by shirirave on 21/12/2017.
 *
 * Custom mapper to parse objects to json in play framework
 */
public class CustomObjectMapper {

    public CustomObjectMapper() {
            ObjectMapper mapper = Json.newDefaultMapper()
                    // enable features and customize the object mapper here ...
                    .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // etc.
            Json.setObjectMapper(mapper);
        }

    }


