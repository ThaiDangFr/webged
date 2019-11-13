package openplatform.index;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.fr.*;
import org.apache.lucene.analysis.br.*;
import org.apache.lucene.analysis.cz.*;
import org.apache.lucene.analysis.de.*;
import org.apache.lucene.analysis.nl.*;

/**
 * OPAnalyzer.java
 *
 *
 * Created: Sat Jul 16 19:32:59 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class OPAnalyzer extends StandardAnalyzer
{
    public static String[] STOP_WORDS = new String[0];

    static
    {
        addStopWords(FrenchAnalyzer.FRENCH_STOP_WORDS);
        addStopWords(StopAnalyzer.ENGLISH_STOP_WORDS);
        addStopWords(BrazilianAnalyzer.BRAZILIAN_STOP_WORDS);
        addStopWords(CzechAnalyzer.CZECH_STOP_WORDS);
        addStopWords(new String[] {"einer", "eine", "eines", "einem", "einen",
                                   "der", "die", "das", "dass", "daß",
                                   "du", "er", "sie", "es",
                                   "was", "wer", "wie", "wir",
                                   "und", "oder", "ohne", "mit",
                                   "am", "im", "in", "aus", "auf",
                                   "ist", "sein", "war", "wird",
                                   "ihr", "ihre", "ihres",
                                   "als", "für", "von", "mit",
                                   "dich", "dir", "mich", "mir",
                                   "mein", "sein", "kein",
                                   "durch", "wegen", "wird"}); // GERMAN
        addStopWords(DutchAnalyzer.DUTCH_STOP_WORDS);
    }


    public OPAnalyzer()
    {
        super(STOP_WORDS);
    }
    

    public static void main(String[] args)
    {
        for(int i=0; i<STOP_WORDS.length;i++)
            System.out.println(STOP_WORDS[i]);
    }


    private static void addStopWords(String[] sw)
    {
        if(sw == null) return;
        String[] newSW = new String[STOP_WORDS.length+sw.length];
        System.arraycopy(STOP_WORDS,0,newSW,0,STOP_WORDS.length);
        System.arraycopy(sw,0,newSW,STOP_WORDS.length,sw.length);
        STOP_WORDS = newSW;
    }
}
