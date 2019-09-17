package artificemc.artifice.api.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class CollectionUtils {
    private CollectionUtils() {}

    @SafeVarargs
    public static <T> Set<T> setOf(T... elems) {
        return Arrays.stream(elems).collect(Collectors.toSet()); }
    @SafeVarargs
    public static <T> List<T> listOf(T... elems) {
        return Arrays.stream(elems).collect(Collectors.toList()); }
}
