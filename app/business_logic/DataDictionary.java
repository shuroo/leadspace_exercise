package business_logic;

import play.Logger;

import java.io.*;
import java.util.Vector;
/**
 * Created by shirirave on 20/12/2017.
 *
 * This better be replaced by a database / datastore in "real life", for bigger amounts of data.
 * Fore a first implementation we will store the data-in-memory using internal data sturcture (Vector)
 */
public class DataDictionary {

    private Vector<String> word_dictionary = new Vector<String>();

    public Vector<String> getWordDictionary() {
        return word_dictionary;
    }

    public DataDictionary(){
        this.init();
    }
    /**
     * Use this endpoint related to update the data-distionary to the following default values:
     */
    public void init(){
        Vector<String> data = new Vector();
        data.add("President");
        data.add("Vice President");
        data.add("Sales");
        data.add("Marketing");
        data.add("President");
        data.add("IT");
        data.add("CFO");
        data.add("CTO");
        data.add("Banking");

        word_dictionary.clear();
        word_dictionary.addAll(data);
        Logger.info("Successfully updated default-values of 9 words to the word-dictionary.");
    }

    public DataDictionaryUpdateResult updateDataDictionaryFromFile (FileInputStream file_is)   throws Exception {


    BufferedReader br = new BufferedReader(new InputStreamReader(file_is));
        // initially clear the local data-store to update it
        //word_dictionary.clear();
        Vector word_dictionary_clone = new Vector();
        String strLine;

        //Read File Line By Line
        try {
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                word_dictionary_clone.add(strLine);
            }
        }
        catch(Exception e){
            String message = "Failed to perform update-data-dictionary operation. operation aborted. Exception thrown with " +
                    "Message:"+e.getMessage()+",Stack trace:"+e.getStackTrace().toString();
            Logger.error(message);
            return new DataDictionaryUpdateResult(false,message);
        }
        finally{
            //Close the input stream
            br.close();
        }

        // Assuming the operation succeeded, copy the temp-vector's content to the data-store
        if(word_dictionary_clone.isEmpty()){
            String message = "Attempt to update empty dictionary detected. no words found - or file read operation failed. Operation Aborted.";
            Logger.error(message);
            return new DataDictionaryUpdateResult(false,message);
        }
        word_dictionary.clear();
        word_dictionary.addAll(word_dictionary_clone);
        String message = "Data-Dictionary Upload operation succeded. Updated:"+word_dictionary.size()+" words successfully";
        // In production - we may wish to reduce this logger to a lower level to reduce stress on some related systems. we can use 'trace' instead.
        Logger.info(message);
        return new DataDictionaryUpdateResult(true,message);


    }


}
