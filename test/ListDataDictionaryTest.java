import models.DataDictionary;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

/**
 * Created by shirirave on 22/12/2017.
 */
public class ListDataDictionaryTest {

    @Test
    public void listDataDictionaryTest() throws Exception{
        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        assert(data_dictionary.dictionaryContentToString().contains("Vice President"));
        assert(data_dictionary.dictionaryContentToString().contains("Sales"));
        assert(data_dictionary.dictionaryContentToString().contains("IT"));

        File file_written2 = CreateTestsData.createThreeWordFileForDataDictionaryTestReplace();
        data_dictionary.updateDataDictionaryFromFile(FileUtils.openInputStream(file_written2));
        assert(!data_dictionary.dictionaryContentToString().contains("Vice President"));
        assert(!data_dictionary.dictionaryContentToString().contains("Sales"));
        assert(!data_dictionary.dictionaryContentToString().contains("IT"));

        assert(data_dictionary.dictionaryContentToString().contains("President"));
        assert(data_dictionary.dictionaryContentToString().contains("Shiris Test"));
        assert(data_dictionary.dictionaryContentToString().contains("Business"));

        File file_written = CreateTestsData.createTwoWordFileForDataDictionaryTestReplace();
        data_dictionary.updateDataDictionaryFromFile(FileUtils.openInputStream(file_written));

        assert(data_dictionary.dictionaryContentToString().contains("President"));
        assert(data_dictionary.dictionaryContentToString().contains("Shiris Test"));
        assert(!data_dictionary.dictionaryContentToString().contains("Business"));
    }
}
