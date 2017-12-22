package controllers;

import com.google.inject.Inject;
import models.DataDictionary;
import models.DataDictionaryReplaceResult;
import models.PhraseProcessor;
import models.PhraseResult;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Vector;

/**
 * Main controller to display data-dictionary endpoints.
 */
public class DataDictionaryController extends Controller {

    @Inject
    DataDictionary data_dictionary;

    @Inject
    PhraseProcessor prase_processor;

    private Result handleFileUploadError(String msg) {

        Logger.error(msg);
        return badRequest(msg);
    }

    private Result updateDictionaryFromFIS(FileInputStream fstream) throws Exception{
        DataDictionaryReplaceResult upload_result = data_dictionary.updateDataDictionaryFromFile(fstream);
        String upload_message = upload_result.getMessage();
        return upload_result.uploadSucceeded() ? ok(upload_message) : badRequest(upload_message);
    }


    /**
     * Default display method for 'index.html' page
     *
     * @return Result
     */
    public Result index() {

        return ok("Welcome to Leadspace-exercise.\n\nBy Shiri Rave.\n\nSince 21/12/17");
    }

    // http://localhost:9000/categorize?phrase=Vice+President+of+Sales+and+Marketing
    public Result categorize(String phrase) {
        HashSet<String> data = data_dictionary.getWordDictionary();
        Vector<PhraseResult> results = prase_processor.aggregatePhraseResults(phrase, data);
        return ok(PhraseResult.resultsToJson(results));
    }
    public Result listDataDictionaryContent() {

        return ok(data_dictionary.dictionaryContentToString());
    }

    public Result resetDictionary() {
        data_dictionary.init();
        return ok("Data dictionary reset to default 9 values. see the full list under {base_url}/list_dictionary_content");
    }

    public Result replaceDataDictionaryByPath(String fileName) {

        try {

            FileInputStream fstream = new FileInputStream(fileName);
            return updateDictionaryFromFIS(fstream);
        } catch (FileNotFoundException e) {
            String msg = "Failed to find required file with name:" + fileName + ", Exception thrown with message:" + e.getMessage() + ",And stack " +
                    "trace:" + e.getStackTrace().toString();
            return handleFileUploadError(msg);
        } catch (Exception e) {
            String msg = "Failed to perform data-dictionary-load from file to file with name:" + fileName + ". Exception thrown with message:" + e
                    .getMessage() + " And stack-trace:" + e.getStackTrace().toString();

            return handleFileUploadError(msg);
        }

    }

    public Result replaceDataDictionary() {

        try {
            File file_to_upload = request().body().asRaw().asFile();
            if (file_to_upload == null) {
                String msg = "Failed to find required file. check the file uploaded and try again";
                return handleFileUploadError(msg);
            }

            FileInputStream fstream = new FileInputStream(file_to_upload);
            return updateDictionaryFromFIS(fstream);
        } catch (Exception e) {
            String msg = "Failed to perform data-dictionary-load from file to file. Exception thrown with message:" + e
                    .getMessage() + " And stack-trace:" + e.getStackTrace().toString();
            Logger.error(msg);
            return handleFileUploadError(msg);
        }

    }


}
