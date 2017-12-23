package controllers;

import com.google.inject.Inject;
import models.DataDictionary;
import models.DataDictionaryReplaceResult;
import models.PhraseAnalysisResult;
import models.PhraseProcessor;
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

    /**
     * private method - process file input stream to result
     * @param fstream - file input stream
     * @return Result (HTTP result)
     * @throws Exception
     */
    private Result replaceDictionaryByFIS(FileInputStream fstream) throws Exception{
        DataDictionaryReplaceResult upload_result = data_dictionary.replaceDataDictionaryByFile(fstream);
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

    /**
     * Main method for 'categorise' endpoint.
     * Given a phrase, find words in data-dictionary.
     * Prioritize longer phrases ('Vice President') over shorter ('President')
     * Access URL:  GET https://leadspace-exercise.herokuapp.com/categorize?phrase={{some_phrase}}
     * Example input: GET https://leadspace-exercise.herokuapp.com/categorize?phrase=Vice+President+of+Sales+and+Marketing
     * @param phrase - the phrase to analyze
     * @return Result
     */
    public Result categorize(String phrase) {
        HashSet<String> data = data_dictionary.getWordDictionary();
        Vector<PhraseAnalysisResult> results = prase_processor.aggregatePhraseResults(phrase, data);
        return ok(PhraseAnalysisResult.resultsToJson(results));
    }

    /**
     * Method to display the current data-dictionary content.
     * Access URL:  GET https://leadspace-exercise.herokuapp.com/list_dictionary_content
     * @return Result
     */
    public Result listDataDictionaryContent() {

        return ok(data_dictionary.toString());
    }

    /**
     * Method to reset the current data-dictionary to default.
     * Access URL: GET  https://leadspace-exercise.herokuapp.com/reset_dictionary
     * @return Result
     */
    public Result resetDictionary() {
        data_dictionary.init();
        return ok("Data dictionary reset to default "+data_dictionary.getWordDictionary().size()+" values. see the full list under " +
                "{base_url}/list_dictionary_content");
    }

    /**
     * Method to replace the current data-dictionary by inner file placed on the server.
     * Access URL:  GET https://leadspace-exercise.herokuapp.com/replace_dictionary?{{fileName}}
     * Example:  GET https://leadspace-exercise.herokuapp.com/replace_dictionary?sample_data_dictionary.txt
     * @param fileName - The name of the file + relative-path to search and replace (if found)
     * @return Result - Ok if done, BadRequest otherwise.
     */
    public Result replaceDataDictionaryByPath(String fileName) {

        try {

            FileInputStream fstream = new FileInputStream(fileName);
            return replaceDictionaryByFIS(fstream);
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
    /**
     * Method to replace the current data-dictionary by a text file passed in the request body.
     * Access URL:  POST https://leadspace-exercise.herokuapp.com/replace_dictionary
     * Example:  POST https://leadspace-exercise.herokuapp.com/replace_dictionary
     * @return Result - Ok if done, BadRequest otherwise.
     */
    public Result replaceDataDictionary() {

        try {
            File file_to_upload = request().body().asRaw().asFile();
            if (file_to_upload == null) {
                String msg = "Failed to find required file. check the file uploaded and try again";
                return handleFileUploadError(msg);
            }

            FileInputStream fstream = new FileInputStream(file_to_upload);
            return replaceDictionaryByFIS(fstream);
        } catch (Exception e) {
            String msg = "Failed to perform data-dictionary-load from file to file. Exception thrown with message:" + e
                    .getMessage() + " And stack-trace:" + e.getStackTrace().toString();
            Logger.error(msg);
            return handleFileUploadError(msg);
        }

    }


}
