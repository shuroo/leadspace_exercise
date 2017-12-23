package models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by shirirave on 20/12/2017.
 *
 * Class to process the phrase content, to return pairs: (offset + phrase) per step.
 */
public class PhraseProcessor {

    /**
     * - Aid method for 'categorize' endpoint's implementation.
     * - Returns the maximum phrase found in the dictionary which also exists in a given sub-phrase.
     * - Prioritizes longer phrases over short sub-phrase of them (like 'Vice President' and 'President')
     * @param phrase - the total phrase to analyze
     * @param sub_phrase - the sub-phrase to analyse (this is used to make sure sub-phrases (like 'President')
     * Are not researched after including longer phrases containing them (like 'Vice President')
     * In the analysis result
     *
     * *** The algorithm is case-sensitive.  ***
     *
     * @param data - the data-dictionary to search in.
     * @return PhraseResult - Struct
     */
    private PhraseResult getMaximalWordInDictionary(String sub_phrase,String phrase,HashSet<String> data){
        Iterator<String> word_iterator = data.iterator();
        Integer max_word_length = 0;
        // Max word present in the data-store -
        String max_word = null;

        while(word_iterator.hasNext()){
            String current_word = word_iterator.next();
            if(sub_phrase.contains(current_word) && max_word_length < current_word.length()){
                max_word = current_word;
                max_word_length = current_word.length();

            }
        }

        Integer offset = (max_word != null ? phrase.indexOf(max_word) : -1);
        Integer sub_phrase_offset = (max_word != null ? sub_phrase.indexOf(max_word) : -1);

        return new PhraseResult(offset,sub_phrase_offset,max_word);
    }

    /**
     * - Main method to implement data-dictionary phrase identification analysis
     *   (Business logic for 'categorize' endpoint)
     * - Fetch from the given phrase, sub-phrases present in the given data dictionary.
     * - In case of dillema (having one phrase as another's sub-phrase)
     * - Prioritize the results given by length (Return the longer sub-phrase).
     *
     * *** The algorithm is case-sensitive.  ***
     *
     * @param phrase - The phrase to search in.
     * @param data - The data-dictionary given.
     * @return Vector<PhraseResult> : Collection of found phrases (sorted by length in a descending order).
     */
    public Vector<PhraseResult> aggregatePhraseResults(String phrase,HashSet<String> data){
        Vector<PhraseResult> phrase_results = new Vector<PhraseResult>() ;
        String sub_phrase = phrase;
        PhraseResult next_word = getMaximalWordInDictionary(sub_phrase,phrase,data);
        while(next_word.getWord()!= null){
            phrase_results.add(next_word);
            // Reduce the current word from the original phrase..
            sub_phrase = sub_phrase.substring(0,next_word.getSubPhraseOffset())+sub_phrase.substring(next_word.getSubPhraseOffset() + next_word.getWord().length(),
                    sub_phrase.length
                    ());
            // Update next_word
            next_word = getMaximalWordInDictionary(sub_phrase,phrase,data);
        }

        return phrase_results;
    }
}
