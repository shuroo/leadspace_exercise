import com.google.inject.Inject;
import models.DataDictionary;
import models.PhraseProcessor;
import models.PhraseResult;
import org.junit.Test;

import java.util.HashSet;
import java.util.Vector;

/**
 * Created by shirirave on 21/12/2017.
 *
 * Test class for phrase-processor class.
 * Run with
 */
public class PhraseProcessorTest {

    @Test
    public void testPhraseProcessor(){

        // As the application did not start normally,
        // We have no ability to inject this DD class her (unless adding some test library like 'mockito' ..)
        DataDictionary data_dictionary = new DataDictionary();
        data_dictionary.init();
        HashSet data = data_dictionary.getWordDictionary();
        String phrase = "Vice President of Sales and Marketing";
        PhraseProcessor prase_processor = new PhraseProcessor();
        Vector<PhraseResult> results = prase_processor.aggregatePhraseResults(phrase,data);

        assert (results.size() == 3);
        assert (results.get(0).getWord().equals("Vice President"));
        assert (results.get(0).getOffset().equals(0));
        assert (results.get(1).getWord().equals("Marketing"));
        assert (results.get(1).getOffset().equals(28));
        assert (results.get(2).getWord().equals("Sales"));
        assert (results.get(2).getOffset().equals(18));

    }
}
