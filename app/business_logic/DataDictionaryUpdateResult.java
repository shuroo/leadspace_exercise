package business_logic;

/**
 * Created by shirirave on 21/12/2017.
 */
public class DataDictionaryUpdateResult {

    private boolean is_success;

    private String message;

    public DataDictionaryUpdateResult(
            boolean operation_result,
            String message){
        this.is_success = operation_result;
        this.message = message;
    }

    public boolean uploadSucceeded() {
        return is_success;
    }

    public String getMessage() {
        return message;
    }
}
