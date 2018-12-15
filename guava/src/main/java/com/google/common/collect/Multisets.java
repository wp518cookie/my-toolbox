package com.google.common.collect;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author wuping
 * @date 2018/12/15
 */

public final class Multisets {
    private Multisets() {

    }

    public static <T, E, M extends Multiset<E>> Collector<T, ?, M> toMultiset(
            Function<? super T, E> elementFunction,
            ToIntFunction<? super T> countFunction,
            Supplier<M> multisetSupplier) {
        checkNotNull(elementFunction);
        checkNotNull(countFunction);
        checkNotNull(multisetSupplier);
        return Collector.of(
                multisetSupplier,
                (ms, t) -> ms.add(elementFunction.apply(t), countFunction.applyAsInt(t)),
                (ms1, ms2) -> {
                    ms1.addAll(ms2);
                    return ms1;
                }
        );
    }
}
