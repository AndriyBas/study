import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by bamboo on 29.05.14.
 */
public interface MyPredicate {

    static <T> Predicate<T> ololoNonNull(Object o) {
        return Objects::nonNull;
    }

}
