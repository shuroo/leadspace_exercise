package models;

import play.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
/**
 * Created by shirirave on 20/12/2017.
 *
 * - Class to store the data-dictionary in memory.
 *   [ This better be replaced by a database / datastore in "real life", for bigger amounts of data. ]
 *   For a first implementation we will store the data-in-memory using internal data sturcture (HashSet - to avoid duplicate values )
 */
public class DataDictionary {

    private HashSet<String> word_dictionary = new HashSet<String>();

    public HashSet<String> getWordDictionary() {
        return word_dictionary;
    }

    public DataDictionary(){
        this.init();
    }

    /**
     * Private method to handle faiure in case of exception
     * @param e
     * @param message
     * @return
     */
    private DataDictionaryReplaceResult handleReplaceOperationFailure(Exception e,String message){
        Logger.error(message,e);
        return new DataDictionaryReplaceResult(false,message);
    }
    /**
     * - Method to define default values of the data dictionary and reset them. Values:
     *
     *   "President","Vice President", "Sales","Marketing","IT","CFO","CTO","Banking"
     */
    public void init(){
        HashSet<String> data = new HashSet();
        data.add("President");
        data.add("Vice President");
        data.add("Sales");
        data.add("Marketing");
        data.add("IT");
        data.add("CFO");
        data.add("CTO");
        data.add("Banking");

        word_dictionary.clear();
        word_dictionary.addAll(data);
        Logger.info("Successfully updated default-values of "+word_dictionary.size()+" words to the word-dictionary.");
    }

    /**
     * - Method to replace the current data-dictionary by fetching new values from text file and overriding it
     *   (Unless the file is empty or the fetch operation fails)
     *   @param file_is - file input steam
     *   @return DataDictionaryReplaceResult (Class)
     */
    public DataDictionaryReplaceResult replaceDataDictionaryByFile(FileInputStream file_is) {


        BufferedReader br = new BufferedReader(new InputStreamReader(file_is));
        // initially clear the local data-store to update it
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
            String message = "Failed to perform update-data-dictionary operation. operation aborted. . Exception thrown. Exception:"+e.toString()+",Message:"+e
                    .getMessage();
            return handleReplaceOperationFailure(e,message);
        }
        finally{
            //Try to Close the input stream.
            try {
                br.close();
            }
            catch(Exception e){
                String message = "Failed to perform update-data-dictionary operation. failed to close file - operation aborted.Exception thrown. Exception:"+e.toString()+",Message:"+e
                        .getMessage();
                return handleReplaceOperationFailure(e,message);
            }
        }

        // Assuming the operation succeeded, copy the temp-vector's content to the data-store
        if(word_dictionary_clone.isEmpty()){
            String message = "Attempt to update empty dictionary detected. no words found - or file read operation failed. Operation Aborted.";
            Logger.error(message);
            return new DataDictionaryReplaceResult(false,message);
        }
        word_dictionary.clear();
        word_dictionary.addAll(word_dictionary_clone);
        String message = "Data-Dictionary Upload operation succeded. Updated:"+word_dictionary.size()+" words successfully";
        // In production - we may wish to reduce this logger to a lower level to reduce stress on some related systems. we can use 'trace' instead.
        Logger.info(message);
        return new DataDictionaryReplaceResult(true,message);


    }

    /**
     * - Method to display the dictionary as string.
     * - Overrides the default 'toString' method.
     *   @return String
     *
     */
    @Override
    public String toString(){
        HashSet<String> data = this.getWordDictionary();
        StringBuilder result_sb = new StringBuilder("Detected the following values in the data-dictionary:\n\n");
        Iterator data_iterator = data.iterator();
        while (data_iterator.hasNext()) {
            result_sb.append(data_iterator.next());
            if (data_iterator.hasNext()) {
                result_sb.append(", ");
            }
        }

        return result_sb.toString();
    }

}
