package cz.hradek.kremilek.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * http://www.vogella.com/tutorials/Hamcrest/article.html
 * <p>
 * It is possible to write your custom Hamcrest matcher by extending TypeSafeMatcher.
 * The following is an example for defining a matcher which allows testing if a String matches a regular expression.
 */
public class RegexMatcher extends TypeSafeMatcher<String> {

    private final String regex;

    public RegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("matches regular expression=`" + regex + "`");
    }

    @Override
    public boolean matchesSafely(final String string) {
        return string.matches(regex);
    }


    // matcher method you can call on this matcher class
    public static RegexMatcher matchesRegex(final String regex) {
        return new RegexMatcher(regex);
    }
}

