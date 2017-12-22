import models.DataDictionary;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

/**
 * Created by shirirave on 22/12/2017.
 *
 * Test class for endpoint: reset data dictionary
 */
public class ResetDataDictionaryTest {

    @Test
    public void testResetDictionary() throws Exception{
        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();

        assert (data_dictionary.getWordDictionary().size() == 8);

        File file_written = CreateTestsData.createTwoWordFileForDataDictionaryTestReplace();

        data_dictionary.updateDataDictionaryFromFile(FileUtils.openInputStream(file_written));
        assert (data_dictionary.getWordDictionary().size() == 2);
        data_dictionary.init();
        assert (data_dictionary.getWordDictionary().size() == 8);

    }
}
