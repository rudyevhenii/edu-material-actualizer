package dev.rudyevhenii.edu_material_actualizer.core.util;

import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public static <T> Stream<T> toStream(Iterable<T> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection<T>) iterable).stream();
        }
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
