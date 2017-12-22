import models.DataDictionary;
import models.PhraseProcessor;
import models.PhraseResult;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import play.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

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
