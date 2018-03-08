package at.hollander.ibex.util;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

public class CollectionUtil {

    public static <T> void splitBy(Collection<T> c1, Collection<T> c2, Comparator<T> comparator, Collection<T> C1ButNotC2, Collection<Tuple2<T, T>> C1AndC2, Collection<T> C2ButNotC1) {
        for (T t1 : c1) {
            boolean found = false;
            for (T t2 : c2) {
                if (comparator.compare(t1, t2) == 0) {
                    found = true;
                    C1AndC2.add(Tuple.tuple(t1, t2));
                    break;
                }
            }
            if (!found) {
                C1ButNotC2.add(t1);
            }
        }

        for (T t2 : c2) {
            if (!containsBy(c1, t2, comparator)) {
                C2ButNotC1.add(t2);
            }
        }
    }

    public static <T> void removeAllBy(Collection<T> c, Collection<T> toRemove, Comparator<T> comparator) {
        c.removeIf(t -> containsBy(toRemove, t, comparator));
    }

    public static <T> boolean containsBy(Collection<T> c, T n, Comparator<T> comparator) {
        boolean found = false;
        for (T t : c) {
            if (comparator.compare(t, n) == 0) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static Stream<LocalDate> streamDateRange(LocalDate start, LocalDate end) {
        return Stream
                .iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end) + 1);
    }

}
