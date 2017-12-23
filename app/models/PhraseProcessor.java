package models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by shirirave on 20/12/2017.
 *
 * Class to process the phrase content, to return pairs: (offset + term) per step.
 */
public class PhraseProcessor {

    private String reduceTermFromSubPhrase(PhraseAnalysisResult term,String sub_phrase){
        return sub_phrase.substring(0,term.getSubPhraseOffset())+sub_phrase.substring(term.getSubPhraseOffset()
                        + term.getTerm().length(),
                sub_phrase.length
                        ());
    }

    /**
     * - Aid method for 'categorize' endpoint's implementation.
     * - Returns the maximum phrase found in the dictionary which also exists in a given sub-phrase.
     * - Prioritizes longer phrases over short sub-phrase of them (like 'Vice President' and 'President')
     * @param phrase - The total phrase to analyze
     * @param sub_phrase - The remaining sub-phrase to analyse
     * (The sub-phrase exists to make sure sub-terms (like 'President')
     * Are not included in the result after including longer terms containing them (like 'Vice President')
     * In the analysis result)
     *
     * *** The algorithm is case-sensitive.  ***
     *
     * @param data - the data-dictionary to search in.
     * @return PhraseAnalysisResult - Struct to indicate and return found-terms + offsets.
     */
    private PhraseAnalysisResult getMaximalTermInDictionary(String sub_phrase, String phrase, HashSet<String> data){
        Iterator<String> word_iterator = data.iterator();
        Integer max_word_length = 0;
        // Max word/term present in the data-store -
        String max_term = null;

        while(word_iterator.hasNext()){
            String current_word = word_iterator.next();
            if(sub_phrase.contains(current_word) && max_word_length < current_word.length()){
                max_term = current_word;
                max_word_length = current_word.length();

            }
        }

        Integer offset = (max_term != null ? phrase.indexOf(max_term) : -1);
        Integer sub_phrase_offset = (max_term != null ? sub_phrase.indexOf(max_term) : -1);

        return new PhraseAnalysisResult(offset,sub_phrase_offset,max_term);
    }

    /**
     * - Main method to implement data-dictionary phrase identification analysis
     *   (Business logic for 'categorize' endpoint)
     * - Fetch from the given phrase, sub-phrases present in the given data dictionary.
     * - In case of dilemma (having one phrase as another's sub-phrase)
     * - Prioritize the results given by length (Return the longer sub-phrase).
     *
     * *** The algorithm is case-sensitive.  ***
     *
     * @param phrase - The phrase to search in.
     * @param data - The data-dictionary given.
     * @return Vector<PhraseAnalysisResult> : Collection of found terms (sorted by length in a descending order).
     */
    public Vector<PhraseAnalysisResult> aggregatePhraseResults(String phrase, HashSet<String> data){
        Vector<PhraseAnalysisResult> phrase_results = new Vector<PhraseAnalysisResult>() ;
        String sub_phrase = phrase;
        PhraseAnalysisResult next_term = getMaximalTermInDictionary(sub_phrase,phrase,data);
        while(next_term.getTerm()!= null){
            phrase_results.add(next_term);
            // Reduce the current term/word from the original phrase..
            sub_phrase = reduceTermFromSubPhrase(next_term, sub_phrase);
            // Update next_term
            next_term = getMaximalTermInDictionary(sub_phrase,phrase,data);
        }

        return phrase_results;
    }
}
