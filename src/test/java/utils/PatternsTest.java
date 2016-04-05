package utils;

import org.junit.Test;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kadir on 3/22/2016.
 */
public class PatternsTest {
    @Test
    public void patternTest() {
        String property2 = "Deneme 1 2 3 Microsoft , ve , Oracle ve MySQL";
        String property = property2.toLowerCase();
        Pattern compile = Pattern.compile("(microsoft|oracle|mysql)");
        Matcher matcher = compile.matcher(property);
//        MatchResult matchResult = matcher.toMatchResult();
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
//        String group = matcher.group(0);
//        String group2 = matcher.group(1);
//        System.out.println(group);
//        System.out.println(group2);
    }
}
