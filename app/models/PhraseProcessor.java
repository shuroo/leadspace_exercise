package models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by shirirave on 20/12/2017.
 *
 * Class to process the phrase content, to return pairs: (offset + ) per step.
 */
public class PhraseProcessor {

    /**
     * Return the maximum word length found in the dictionary
     * @param phrase
     * @param data
     * @return
     */
    public  PhraseResult getMaximalWordInDictionary(String sub_phrase,String phrase,HashSet<String> data){
        Iterator<String> word_iterator = data.iterator();
        Integer max_word_length = 0;
        // Max word present in the data-store -
        String max_word = null;
        //todo: there must be a more elegant way to write this..
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
