package business_logic;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONArray;
import org.json.JSONObject;
import play.libs.Json;
import utils.CustomObjectMapper;

import java.util.Vector;

/**
 * Created by shirirave on 20/12/2017.
 *
 * Class to represent the offset found of partial 
 */
public class PhraseResult {

    private Integer offset;
    private String word;

    //private field for inner usage only - rercursively update the sub_phrase to scan next..
    private Integer sub_phrase_offset;

    public Integer getSubPhraseOffset() {
        return sub_phrase_offset;
    }

    public Integer getOffset() {
        return offset;
    }

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

    public JsonNode resultAsJson() {

            return Json.newObject().put("word",word).put("offset",offset);
        }

    public static JsonNode resultsToJson(Vector<PhraseResult> results){

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode json_arr = mapper.createArrayNode();
        for(PhraseResult result : results) {
            json_arr.add(result.resultAsJson());
        }
        return json_arr;
    }
}
