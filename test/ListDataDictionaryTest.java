import models.DataDictionary;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

/**
 * Created by shirirave on 22/12/2017.
 */
public class ListDataDictionaryTest {

    /**
     * - Test method for phrase analysis :
     * - Expected result: The values are identical to those stored in the relevant files after each call to 'replaceDataDictionary'
     *   business logic and returned.
     */
    @Test
    public void testListDataDictionary() throws Exception{
        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        assert(data_dictionary.toString().contains("Vice President"));
        assert(data_dictionary.toString().contains("Sales"));
        assert(data_dictionary.toString().contains("IT"));

        File file_written2 = CreateTestsData.createThreeWordFileForDataDictionaryTestReplace();
        data_dictionary.replaceDataDictionaryByFile(FileUtils.openInputStream(file_written2));
        assert(!data_dictionary.toString().contains("Vice President"));
        assert(!data_dictionary.toString().contains("Sales"));
        assert(!data_dictionary.toString().contains("IT"));

        assert(data_dictionary.toString().contains("President"));
        assert(data_dictionary.toString().contains("Shiris Test"));
        assert(data_dictionary.toString().contains("Business"));

        File file_written = CreateTestsData.createTwoWordFileForDataDictionaryTestReplace();
        data_dictionary.replaceDataDictionaryByFile(FileUtils.openInputStream(file_written));

        assert(data_dictionary.toString().contains("President"));
        assert(data_dictionary.toString().contains("Shiris Test"));
        assert(!data_dictionary.toString().contains("Business"));
    }
}
