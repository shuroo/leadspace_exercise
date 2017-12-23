package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.libs.Json;

import java.util.Vector;

/**
 * Created by shirirave on 20/12/2017.
 *
 * - Class to represent the result returned by the analysis process.
 * - Contains: word/sub-phrase matches dictionary value from the total phrase,
 *   Offset found in the total phrase,
 *   Offset in the remaining phrase to analyze in each step ( -The sub-phrase ).
 *
 */
public class PhraseResult {

    // Offset found in the total phrase
    private Integer offset;

    // Word/sub-phrase matches dictionary value from the total phrase
    private String word;

    //Offset in the remaining phrase to analyze in each step ( -The sub-phrase ).
    private Integer sub_phrase_offset;

    public Integer getSubPhraseOffset() {
        return sub_phrase_offset;
    }

    /**
     * Getter for 'Offset' property
     * @return String
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * Getter for 'Word' property
     * @return String
     */
    public String getWord() {
        return word;
    }

    public PhraseResult(Integer offset,Integer sub_phrase_offset,String word){
        this.offset = offset;
        this.sub_phrase_offset = sub_phrase_offset;
        this.word = word;
    }



    @Override
    public String toString(){
        return resultAsJson().toString();
    }

    /**
     * Method to build json-representation of a single result
     * @return JsonNode - Json-Object
     */
    public JsonNode resultAsJson() {

            return Json.newObject().put("word",word).put("offset",offset);
        }

    /**
     * Method to build json-representation of a bulk of results (phrases found by 'categorize' algorithm)
     * @return JsonNode - Json-Array
     */
    public static JsonNode resultsToJson(Vector<PhraseResult> results){

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode json_arr = mapper.createArrayNode();
        for(PhraseResult result : results) {
            json_arr.add(result.resultAsJson());
        }
        return json_arr;
    }
}
