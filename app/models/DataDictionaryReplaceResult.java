package models;

/**
 * Created by shirirave on 21/12/2017.
 *
 * - Class to represent the 'struct' coming as a result of data dictionary replace operation:
 *
 *   @Param isSuccess - indication for replace operation success (Boolean)
 *   @Param message - the operation related message
 */
public class DataDictionaryReplaceResult {

    private boolean is_success;

    private String message;

    public DataDictionaryReplaceResult(
            boolean operation_result,
            String message){
        this.is_success = operation_result;
        this.message = message;
    }

    /**
     * Getter for 'isSuccess' property
     * @return Boolean
     */
    public boolean uploadSucceeded() {
        return is_success;
    }

    /**
     * Getter for 'Message' property
     * @return String
     */
    public String getMessage() {
        return message;
    }
}
