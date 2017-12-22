package controllers;

import models.DataDictionary;
import models.DataDictionaryUpdateResult;
import models.PhraseProcessor;
import models.PhraseResult;
import com.google.inject.Inject;
import play.Logger;
import play.mvc.*;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Main controller to display data-dictionary endpoints.
 */
public class DataDictionaryController extends Controller {

    @Inject
    DataDictionary data_dictionary;

    @Inject
    PhraseProcessor prase_processor;

    /**
     * Default display method for 'index.html' page
     *
     * @return Result
     */
    public Result index() {

        return ok("Welcome to Leadspace-exercise.\n\nBy Shiri Rave.\n\nSince 21/12/17");
    }

    public Result replaceDataDictionaryByPath(String fileName) {

        try {

            FileInputStream fstream = new FileInputStream(fileName);
            DataDictionaryUpdateResult upload_result = data_dictionary.updateDataDictionaryFromFile(fstream);
            String upload_message = upload_result.getMessage();
            return upload_result.uploadSucceeded() ? ok(upload_message) : badRequest(upload_message);
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

    private Result handleFileUploadError(String msg) {

        Logger.error(msg);
        return badRequest(msg);
    }

    public Result replaceDataDictionary() {

        try {
            File file_to_upload = request().body().asRaw().asFile();
            if (file_to_upload == null) {
                String msg = "Failed to find required file. check the file uploaded and try again";
                return handleFileUploadError(msg);
            }

            FileInputStream fstream = new FileInputStream(file_to_upload);
            DataDictionaryUpdateResult upload_result = data_dictionary.updateDataDictionaryFromFile(fstream);
            String upload_message = upload_result.getMessage();
            return upload_result.uploadSucceeded() ? ok(upload_message) : badRequest(upload_message);
        } catch (Exception e) {
            String msg = "Failed to perform data-dictionary-load from file to file. Exception thrown with message:" + e
                    .getMessage() + " And stack-trace:" + e.getStackTrace().toString();
            Logger.error(msg);
            return handleFileUploadError(msg);
        }

    }


    // http://localhost:9000/categorize?phrase=Vice+President+of+Sales+and+Marketing
    public Result categorize(String phrase) {
        HashSet<String> data = data_dictionary.getWordDictionary();
        Vector<PhraseResult> results = prase_processor.aggregatePhraseResults(phrase, data);
        return ok(PhraseResult.resultsToJson(results));
    }


    public Result listDataDictionaryContent() {
        HashSet<String> data = data_dictionary.getWordDictionary();
        StringBuilder result_sb = new StringBuilder("Detected the following values in the data-dictionary:\n\n");
        Iterator data_iterator = data.iterator();
        while (data_iterator.hasNext()) {
            result_sb.append(data_iterator.next());
            if (data_iterator.hasNext()) {
                result_sb.append(", ");
            }
        }
        return ok(result_sb.toString());
    }

    public Result resetDictionary() {
        data_dictionary.init();
        return ok("Data dictionary reset to default 9 values. see the full list under {base_url}/list_dictionary_content");
    }

}
