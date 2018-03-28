package at.renehollander.ibex.util.test;

import at.hollander.ibex.util.CollectionUtil;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class CollectionUtilTest {

    @Test
    public void testSplitBy() {
        Set<Integer> s1 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        Set<Integer> s2 = new HashSet<>();
        s2.add(2);
        s2.add(3);
        s2.add(4);

        Set<Integer> r1 = new HashSet<>();
        Set<Tuple2<Integer, Integer>> r2 = new HashSet<>();
        Set<Integer> r3 = new HashSet<>();
        CollectionUtil.splitBy(s1, s2, Integer::compare, r1, r2, r3);

        assertThat(r1, contains(1));
        assertThat(r2, contains(Tuple.tuple(2, 2), Tuple.tuple(3, 3)));
        assertThat(r3, contains(4));
    }

    @Test
    public void testRemoveAllBy() {
        Set<Integer> s1 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        Set<Integer> s2 = new HashSet<>();
        s2.add(1);
        s2.add(3);

        CollectionUtil.removeAllBy(s1, s2, Integer::compare);

        assertThat(s1, contains(2));
    }

    @Test
    public void containsBy() {
        Set<Integer> s1 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        assertTrue(CollectionUtil.containsBy(s1, 1, Integer::compare));
        assertFalse(CollectionUtil.containsBy(s1, 4, Integer::compare));
    }

}
