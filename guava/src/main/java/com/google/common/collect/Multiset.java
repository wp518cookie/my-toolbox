package com.google.common.collect;


import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * @author wuping
 * @date 2018/12/15
 */

public interface Multiset<E> extends Collection<E> {
    @Override
    int size();

    int count(@Nullable Object element);

    int add(@Nullable E element, int occurrences);

    @Override
    boolean add(E element);

    int remove(@Nullable Object element, int occurrence);

    @Override
    boolean remove(@Nullable Object element);

    int setCount(E element, int count);

    boolean setCount(E element, int oldCount, int newCount);

    Set<E> elementSet();

    Set<Entry<E>> entrySet();

    interface Entry<E> {
        E getElement();

        int getCount();

        @Override
        boolean equals(Object o);

        @Override
        int hashCode();

        @Override
        String toString();
    }

    default void forEachEntry(ObjIntConsumer<? super E> action) {
        checkNotNull(action);
        entrySet().forEach(entry -> action.accept(entry.getElement(), entry.getCount()));
    }

    @Override
    boolean equals(@Nullable Object object);

    @Override
    int hashCode();

    @Override
    String toString();

    @Override
    Iterator<E> iterator();

    @Override
    boolean contains(@Nullable Object element);

    @Override
    boolean containsAll(Collection<?> elements);

    @Override
    boolean removeAll(Collection<?> c);

    @Override
    boolean retainAll(Collection<?> c);

    @Override
    default void forEach(Consumer<? super E> action) {
        checkNotNull(action);
        entrySet()
                .forEach(
                        entry -> {
                            E elem = entry.getElement();
                            int count = entry.getCount();
                            for (int i = 0; i < count; i++) {
                                action.accept(elem);
                            }
                        }
                );
    }

    @Override
    default Spliterator<E> spliterator() {
        return Multisets.spliteratorImpl(this);
    }
}
