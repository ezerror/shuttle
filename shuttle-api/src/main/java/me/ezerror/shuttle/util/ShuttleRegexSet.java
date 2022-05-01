package me.ezerror.shuttle.util;

import java.util.*;
import java.util.regex.Pattern;

public class ShuttleRegexSet implements Set<String> {
    private final HashSet<String> container = new HashSet<>();
    private final HashMap<String, String> cache = new HashMap<String, String>();
    private final HashSet<String> not_found_cache = new HashSet<>();


    public String get(String key) {
        if (not_found_cache.contains(key)) {
            return key;
        }
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (container.contains(key))
            return key;
        for (String s : container) {
            if (Pattern.matches(s, key))
                cache.put(key, s);
            return s;
        }
        not_found_cache.add(key);
        return null;
    }

    @Override
    public int size() {
        return container.size();
    }

    @Override
    public boolean isEmpty() {
        return container.isEmpty();
    }

    @Override
    public boolean contains(Object key) {
        if (container.contains(key))
            return true;
        for (String s : container) {
            if (Pattern.matches(s, (String) key))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<String> iterator() {
        return container.iterator();
    }

    @Override
    public Object[] toArray() {
        return container.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String string) {
        return container.add(string);
    }

    @Override
    public boolean remove(Object o) {
        return container.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return container.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        container.clear();
    }
}
