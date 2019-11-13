package openplatform.regexp;

import openplatform.tools.*;
//import org.apache.oro.text.regex.*;
import java.util.regex.*;

/**
 * Class Regexp
 *
<pre>
Reference : http://www.enstimac.fr/Perl/DocFr/perlretut.html

Expression régulière
--------------------

* "Hello World" =~ /o W/ renverra true (le o de Hello et le W de World)

* Les métas caractères sont {} [] ^$.|*+?\

* Pour ne pas prendre en compte un méta caractère, le préfixer de \

* ^ et $ servent à spécifier le début et la fin :
"Hello World" =~ /World$/ => true
"Hello World" =~ /^Hello/ => true

* Les classes de caractères avec [] :
/[bcr]at/;   # reconnait 'bat, 'cat', ou 'rat'
/item[0123456789]/;  # reconnait 'item0' ou ... ou 'item9'
"abc" =~ /[cab]/;    # reconnait le 'a'

* Pour ne pas etre sensible a la casse, rajouter i après l'expression :
/oui/i équivalent à /[oO][uU][iI]/

* - sert à spécifier un intervalle :
/item[0-9]/;  # reconnait 'item0' ou ... ou 'item9'
/[0-9bx-z]aa/;  # reconnait '0aa', ..., '9aa', 'baa', 'xaa', 'yaa', or 'zaa'

* les abbréviations : 
\d est un chiffre et represente [0-9]
\s est un caractere d'espacement et represente [\ \t\r\n\f]
\w est un caractere de mot (alphanumerique ou _) et represente [0-9a-zA-Z_]
\D est la negation de \d; il represente n'importe quel caractere sauf un chiffre [^0-9]
\S est la negation de \s; il represente n'importe quel caractere qui ne soit pas d'espacement [^\s]
\W est la negation de \w; il represente n'importe quel caractere qui ne soit un pas un caractere de mot [^\w]
Le point '.' reconnai^t n'importe quel caracte`re sauf ``\n''

* exemple :
/\d\d:\d\d:\d\d/; # reconnait une heure au format hh:mm:ss
/[\d\s]/;         # reconnait n'importe quel chiffre ou espacement
/\w\W\w/;         # reconnait un caractere mot, suivi d'un caractere non-mot, suivi d'un caractere mot
/..rt/;           # reconnait deux caractere, suivis de 'rt'
/end\./;          # reconnait 'end.'
/end[.]/;         # la meme chose, reconnait 'end.'

/(a|b)b/;    # reconnait 'ab' ou 'bb'
a? = reconnait 'a' 1 ou 0 fois
a* = reconnait 'a' 0 ou plusieurs fois
a+ = reconnait 'a' 1 ou plusieurs fois
a{n,m} = reconnait au moins n 'a', mais pas plus que m 'a'.
a{n,} = reconnait au moins n 'a' ou plus
a{n} = reconnait exactement n 'a'
</pre>
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Regexp
{
	/*
    private static PatternCompiler compiler = new Perl5Compiler();
    private static PatternMatcher matcher = new Perl5Matcher();

    private Pattern pattern;
    private StringArrayList result = new StringArrayList();
    private PatternMatcherInput input;
    private String toAnalyse;
*/

	
	private Pattern pattern;
	private Matcher matcher;
	
	private String containsString;
	
	
	
    public Regexp(String spattern)
    {
        try
        {
        	//pattern = compiler.compile(spattern);
        	pattern = Pattern.compile(spattern);
        	matcher = pattern.matcher("");
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    public boolean hasMoreContains(String string)
    {
    	/*
        if(pattern == null || string == null) return false;

        if(toAnalyse == null || !string.equals(toAnalyse))
        {
            toAnalyse = string;
            input = new PatternMatcherInput(string);
        }
        

        return matcher.contains(input, pattern);
        */
    	
    	
    	if(pattern == null || string == null) return false;
    	
    	if(containsString == null || !string.equals(containsString))
    	{
    		containsString = string;
    		//matcher = pattern.matcher(string);
    		matcher.reset(containsString);
    	}
    	
    	return matcher.find();
    	
    }
    

    public boolean matches(String string)
    {
    	/*
        if(pattern == null) return false;

        return matcher.matches(string, pattern);
        */
    	
        
        matcher.reset(string);
        return matcher.matches();
    }

    
    /**
     * Begin with index=1 to retrieve matching results (index=0 is the expression)
     */
    
    public StringArrayList getMatchingResults()
    {
    	StringArrayList result = new StringArrayList();
    	
    	
    	
    	//if(matcher.matches())
    	//{
    		try
    		{
    			int len = matcher.groupCount();
    			for(int i=0; i<=len; i++)
    			{
    				result.add(matcher.group(i));
    			}
    		}
    		catch(Exception e) {}
    		
    		
    	//}
    	
    	return result;
    }
    
    
    /*
    public StringArrayList getMatchingResults()
    {
        result.clear();

        if(pattern != null)
        {
            MatchResult mresult = matcher.getMatch();
            if(mresult != null)
            {
                int len = mresult.groups();
                for(int i=0; i<len; i++)
                {
                    result.add(mresult.group(i));
                }
            }
        }

        return result;
    }
    */

    /**
     * Transform a string to a ready to use regexp string for pattern matching
     */
    public static String stringToMatchregexp(String string)
    {
        string = StringUtils.replaceAll(string,"\\d","\\\\d");
        string = StringUtils.replaceAll(string,"\\s","\\\\s");
        string = StringUtils.replaceAll(string,"\\w","\\\\w");
        string = StringUtils.replaceAll(string,"\\D","\\\\D");
        string = StringUtils.replaceAll(string,"\\S","\\\\S");
        string = StringUtils.replaceAll(string,"\\W","\\\\W");
        string = StringUtils.replaceAll(string,".","\\.");

        return string;
    }


    public static void main(String[] args)
    {
    	Regexp reg = new Regexp("(.*)Gecko(.*)");
    	System.out.println(reg.matches("sdfds"));
    	
    	
    	/*
    	Regexp reg = new Regexp("([\\w\\W])*");
    	if(reg.matches("dsd,\nds\ndd,[=")) System.out.println("ok");
    	else System.out.println("nok");
    	*/
    	
    	
    	 //matches results for pattern a1z_12:[a1z_12, a1z, 12]
    	 //matches results for pattern j1j__a:[]
    	 //contains results for pattern ezae_12dqddsq:[ezae_12, ezae, 12]
    	 //contains results for pattern dqs_1212 abs_1:[dqs_1212, dqs, 1212]
    	 //contains results for pattern dqs_1212 abs_1:[abs_1, abs, 1]
    	 //stringToMatchregexp OK 
    	 
    	/*
        Regexp reg = new Regexp("(\\w+)_(\\d+)");

        if(reg.matches("a1z_12"))
            System.out.println("matches results for pattern a1z_12:"+reg.getMatchingResults());

        if(!reg.matches("j1j__a"))
            System.out.println("matches results for pattern j1j__a:"+reg.getMatchingResults());

        
        
        while(reg.hasMoreContains("ezae_12dqddsq"))
        {
            System.out.println("contains results for pattern ezae_12dqddsq:"+reg.getMatchingResults());
        }

        while(reg.hasMoreContains("dqs_a12"))
        {
            System.out.println("contains results for pattern dqs_a12:"+reg.getMatchingResults());
        }

        
        while(reg.hasMoreContains("dqs_1212 abs_1"))
        {
            System.out.println("contains results for pattern dqs_1212 abs_1:"+reg.getMatchingResults());
        }

        String st = "\\d";
        Regexp reg1 = new Regexp(stringToMatchregexp(st));
        if(reg1.matches(st)) System.out.println("stringToMatchregexp OK");
        else System.out.println("stringToMatchregexp NOK");
        */
        
    }
}
