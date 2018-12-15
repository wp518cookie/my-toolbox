package com.google.common.collect;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wuping
 * @date 2018/12/15
 */

public abstract class AbstractMultiset<E> extends AbstractCollection<E> implements Multiset<E> {
    private transient @MonotonicNonNull Set<E> elementSet;
    private transient Set<Entry<E>> entrySet;

    @Override
    public boolean isEmpty() {
        return entrySet().isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object element) {
        return count(element) > 0;
    }

    @CanIgnoreReturnValue
    @Override
    public final boolean add(@Nullable E element) {
        add(element, 1);
        return true;
    }

    @Override
    public int add(@Nullable E element, int occurences) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Override
    public final boolean remove(@Nullable Object element) {
        return remove(element, 1) > 0;
    }

    @CanIgnoreReturnValue
    @Override
    public int remove(@Nullable Object element, int occurences) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Override
    public int setCount(@Nullable E element, int count) {
        return setCountImpl(this, element, count);
    }

    @CanIgnoreReturnValue
    @Override
    public boolean setCount(@Nullable E element, int oldCount, int newCount) {
        return setCountImpl(this, element, oldCount, newCount);
    }

    @CanIgnoreReturnValue
    @Override
    public final boolean addAll(Collection<? extends E> elementsToAdd) {
        return Multisets.addAllImpl(this, elementsToAdd);
    }

    @CanIgnoreReturnValue
    @Override
    public final boolean removeAll(Collection<?> elementsToRemove) {
        return Multisets.removeAllImpl(this, elementsToRemove);
    }

    @CanIgnoreReturnValue
    @Override
    public final boolean retainAll(Collection<?> elementsToRetain) {
        return Multisets.retainsAllimpl(this, elementsToRetain);
    }

    @Override
    public abstract void clear();

    @Override
    public Set<E> elementSet() {
        Set<E> result = elementSet;
        if (result == null) {
            elementSet = result = createElementSet();
        }
        return result;
    }

    Set<E> createElementSet() {
        return new ElementSet();
    }

    class ElementSet extends Multisets.ElementSet<E> {
        @Override
        Multiset<E> multiset() {
            return AbstractMultiset.this;
        }

        @Override
        public Iterator<E> iterator() {
            return elementIterator();
        }
    }

    abstract Iterator<E> elementIterator();

    @Override
    public Set<Entry<E>> entrySet() {
        Set<Entry<E>> result = entrySet;
        if (result == null) {
            entrySet = result = createEntrySet();
        }
        return result;
    }

    class EntrySet extends Multisets.EntrySet<E> {
        @Override
        Multiset<E> multiset() {
            return AbstractMultiset.this;
        }

        @Override
        public Iterator<Entry<E>> iterator() {
            return entryIterator();
        }

        @Override
        public int size() {
            return distinctElements();
        }
    }

    Set<Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    abstract Iterator<Entry<E>> entryIterator();

    abstract int distinctElements();

    @Override
    public final boolean equals(@Nullable Object object) {
        return Multisets.equalsImpl(this, object);
    }

    @Override
    public final int hashCode() {
        return entrySet().hashCode();
    }

    @Override
    public final String toString() {
        return entrySet().toString();
    }
}
