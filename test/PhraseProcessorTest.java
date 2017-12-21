import business_logic.PhraseProcessor;
import business_logic.PhraseResult;
import org.junit.Test;

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
