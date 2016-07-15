package cz.hradek.kremilek.hamcrest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * http://www.vogella.com/tutorials/Hamcrest/article.html
 */
public class VogellaTestCase {

    @Test
    public void introduction() {
        boolean a = true;
        boolean b = true;

        // all statements test the same
        assertThat(a, equalTo(b));
        assertThat(a, is(equalTo(b)));
        assertThat(a, is(b));

        // JUnit 4 for equals check
        String expected = "foo";
        String actual = expected;

        assertEquals(expected, actual);
        // Hamcrest for equals check
        assertThat(actual, is(equalTo(expected)));

        actual = "bar";
        // JUnit 4 for not equals check
        assertFalse(expected.equals(actual));
        // Hamcrest for not equals check
        assertThat(actual, is(not(equalTo(expected))));

        // It is also possible to chain matchers, via the anyOf of allOf method.
        assertThat("test", anyOf(is("testing"), containsString("est")));
    }

    /*
The following are the most important Hamcrest matchers:
allOf - matches if all matchers match (short circuits)
anyOf - matches if any matchers match (short circuits)
not - matches if the wrapped matcher doesn’t match and vice
equalTo - test object equality using the equals method
is - decorator for equalTo to improve readability
hasToString - test Object.toString
instanceOf, isCompatibleType - test type
notNullValue, nullValue - test for null
sameInstance - test object identity
hasEntry, hasKey, hasValue - test a map contains an entry, key or value
hasItem, hasItems - test a collection contains elements
hasItemInArray - test an array contains an element
closeTo - test floating point values are close to a given value
greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo
equalToIgnoringCase - test string equality ignoring case
equalToIgnoringWhiteSpace - test string equality ignoring differences in runs of whitespace
containsString, endsWith, startsWith - test string matching
     */

    @Test
    public void example_3_1() {
        assertThat(Long.valueOf(1), instanceOf(Integer.class));
    }

    @Test
    public void listShouldInitiallyBeEmpty() {
        List<Integer> list = Arrays.asList(5, 2, 4);

        assertThat(list, hasSize(3));
        // ensure the order is correct
        assertThat(list, contains(5, 2, 4));
        assertThat(list, containsInAnyOrder(2, 4, 5));
        assertThat(list, everyItem(greaterThan(1)));
    }

    @Test
    public void testRegularExpressionMatcher() throws Exception {
        String s = "aaabbbaaaa";
        assertThat(s, RegexMatcher.matchesRegex("a*b*a*"));
    }

    // 5. Exercise - Using Hamcrest matchers
    @Test
    public void exercise() {
        List<Integer> list = Arrays.asList(5, 2, 4);

        // has a size of 3
        assertThat(list, hasSize(3));
        // contains the elements 2, 4, 5 in any order
        assertThat(list, containsInAnyOrder(2, 4, 5));
        // every item is greater than 1
        assertThat(list, everyItem(is(greaterThan(1))));
    }

    @Test
    public void exercise2() {
        Integer[] ints = new Integer[]{7, 5, 12, 16};

        // has a size of 4
        assertThat(ints, arrayWithSize(4));
        // contains 7, 5 , 12 , 16 in the given order
        assertThat(ints, arrayContainingInAnyOrder(7, 5, 12, 16));
    }

    @Test
    public void beanTest() {
        Todo todo = new Todo(1, "Learn Hamcrest", "Important");
        Todo todo2 = new Todo(1, "Learn Hamcrest", "Important");
        // Todo has a property called "summary"
        assertThat(todo, hasProperty("summary"));
        // If Todo is constructed with the summary "Learn Hamcrest" that the summary property has the correct value
        assertThat(todo, hasProperty("summary", equalTo("Learn Hamcrest")));
        // Two objects created with the same values, have the same property values
        assertThat(todo, samePropertyValuesAs(todo2));
    }

    @Test
    public void emptyString() {
        String test = "";
        assertThat(test, isEmptyString());
        assertThat(test, isEmptyOrNullString());
    }
}
