import models.DataDictionary;
import models.PhraseProcessor;
import models.PhraseAnalysisResult;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by shirirave on 21/12/2017.
 *
 * Test class for phrase-processor class.
 *
 * ** Comment: To identify the 'longest sub-phrase' in the phrase, longer phrases are returned sooner. hence the length-sort
 * That these tests are based on (we can tell that 'Marketing' is expected before 'Sales' in the result as the phrase is longer.) **
 */
public class PhraseProcessorTest {

    /**
     * - Test method for phrase analysis :
     * - Expected result: Identify 'Vice President' and prefer it over 'President'; identify 'Sales', 'Marketing'.
     *   Identify and return expected offsets in phrase.
     */
    @Test
    public void testPhraseProcessor(){

        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        data_dictionary.init();
        HashSet data = data_dictionary.getWordDictionary();
        String phrase = "Vice President of Sales and Marketing";
        PhraseProcessor prase_processor = new PhraseProcessor();
        Vector<PhraseAnalysisResult> results = prase_processor.aggregatePhraseResults(phrase,data);

        assert (results.size() == 3);
        assert (results.get(0).getTerm().equals("Vice President"));
        assert (results.get(0).getOffset().equals(0));
        assert (results.get(1).getTerm().equals("Marketing"));
        assert (results.get(1).getOffset().equals(28));
        assert (results.get(2).getTerm().equals("Sales"));
        assert (results.get(2).getOffset().equals(18));

    }

    @Test
    public void testPhraseProcessor2(){

        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        data_dictionary.init();
        HashSet data = data_dictionary.getWordDictionary();
        String phrase = "Vice President of Sales and Marketinging";
        PhraseProcessor prase_processor = new PhraseProcessor();
        Vector<PhraseAnalysisResult> results = prase_processor.aggregatePhraseResults(phrase,data);

        assert (results.size() == 2);
        assert (!results.get(1).getTerm().equals("Marketing"));

    }

    @Test
    public void testContainsWords(){

        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        data_dictionary.init();
        HashSet data = data_dictionary.getWordDictionary();
        String phrase = "Vice President of Sales and Marketinging";
        PhraseProcessor prase_processor = new PhraseProcessor();
        assert (prase_processor.phraseContainsWholeWords(phrase,"Vice President") == true);
        assert (prase_processor.phraseContainsWholeWords(phrase,"Vice Pres") == false);
        assert (prase_processor.phraseContainsWholeWords(phrase,"Marketing") == false);
        assert (prase_processor.phraseContainsWholeWords(phrase,"Sales") == true);
        assert (prase_processor.phraseContainsWholeWords(phrase,"Sale") == false);
        assert (prase_processor.phraseContainsWholeWords(phrase,"sales") == false);

    }

    /**
     * - Test for phrase analysis :
     * - Expected result: Do not Identify 'Vice Pres' as valid term (not whole)
     *   While 'Vice president' is the value in the data dictionary.
     */
    @Test
    public void testPhraseProcessorWholeWord(){

        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        data_dictionary.init();
        HashSet data = data_dictionary.getWordDictionary();
        String phrase = "Vice Pres of Sales and Marketing";
        PhraseProcessor prase_processor = new PhraseProcessor();
        Vector<PhraseAnalysisResult> results = prase_processor.aggregatePhraseResults(phrase,data);

        assert (results.size() == 2);
        assert (!results.get(0).getTerm().equals("Vice Pres"));
        assert (results.get(0).getTerm().equals("Marketing"));
        assert (results.get(1).getTerm().equals("Sales"));

    }

    /**
     * - Test method for phrase analysis :
     * - Expected result: 'Shiris Test' 'Business' , are phrases identified (present in the dictionary) and returned.
     */

    @Test
    public void testPhraseProcessor3() throws Exception{
        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        File file_written2 = CreateTestsData.createThreeWordFileForDataDictionaryTestReplace();
        data_dictionary.replaceDataDictionaryByFile(FileUtils.openInputStream(file_written2));
        PhraseProcessor prase_processor = new PhraseProcessor();
        HashSet data = data_dictionary.getWordDictionary();
        String phrase = "Shiris Test in Business .. checking A B C ...";
        Vector<PhraseAnalysisResult> results = prase_processor.aggregatePhraseResults(phrase,data);

        assert (results.size() == 2);
        assert (results.get(0).getTerm().equals("Shiris Test"));
        assert (results.get(1).getTerm().equals("Business"));

    }
}
