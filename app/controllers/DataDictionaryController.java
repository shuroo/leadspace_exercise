package controllers;

import models.DataDictionary;
import models.DataDictionaryUpdateResult;
import models.PhraseProcessor;
import models.PhraseResult;
import com.google.inject.Inject;
import play.Logger;
import play.mvc.*;

import java.io.*;
import java.util.Vector;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class DataDictionaryController extends Controller {

    @Inject
    DataDictionary data_dictionary ;

    @Inject
    PhraseProcessor prase_processor;

    public Result index() {

        return ok("Your new application is ready.");
    }

    public Result updateDataDictionary(String fileNameAndPath) {

            try {

                FileInputStream fstream = new FileInputStream(fileNameAndPath);
                DataDictionaryUpdateResult upload_result = data_dictionary.updateDataDictionaryFromFile(fstream);
                String upload_message = upload_result.getMessage();
                return upload_result.uploadSucceeded()? ok(upload_message) : badRequest(upload_message);
            }
            catch(FileNotFoundException e){
                String msg = "Failed to find required file with name:"+fileNameAndPath+", Exception thrown with message:"+e.getMessage()+",And stack " +
                        "trace:"+e.getStackTrace().toString();
                Logger.error(msg);
                return badRequest(msg);
            }
            catch(Exception e){
                String msg = "Failed to perform data-dictionary-load from file to file with name:"+fileNameAndPath+". Exception thrown with message:"+e
                        .getMessage()+" And stack-trace:"+e.getStackTrace().toString();
                Logger.error(msg);
                return badRequest(msg);
            }

        }


    // http://localhost:9000/categorize?phrase=Vice+President+of+Sales+and+Marketing
    public Result categorize(String phrase) {
        System.out.println("+++"+data_dictionary.getWordDictionary().size());
        Vector<String> data = data_dictionary.getWordDictionary();
        Vector<PhraseResult> results = prase_processor.aggregatePhraseResults(phrase,data);
        return ok(PhraseResult.resultsToJson(results));
    }

}
