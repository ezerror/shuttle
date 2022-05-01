package me.ezerror.shuttle.util;

import java.util.*;
import java.util.regex.Pattern;

public class ShuttleRegexMap<T> implements Map<String, T> {

    private final HashMap<String, T> container = new HashMap<>();
    private final HashMap<String, T> cache = new HashMap<>();
    // 添加未查到的缓存
    private final HashSet<String> not_found_cache = new HashSet<>();

    /**
     * 先从缓存中获取，如果未获取到将遍历keySet作为正则表达式匹配当前的get_key
     *
     * @param key key
     * @return result
     */
    public T get(Object key) {
        if (key == null || not_found_cache.contains((String) key)) {
            return null;
        }
        T result;
        if ((result = cache.get(key)) != null) {
            return result;
        } else if ((result = container.get(key)) != null) {
            return result;
        } else {
            for (Entry<String, T> entry : container.entrySet()) {
                if (Pattern.matches(entry.getKey(), (String) key)) {
                    putCache((String) key, entry.getValue());
                    return entry.getValue();
                }
            }
        }
        putNotFoundCache((String) key);
        return null;
    }


    public T put(String key, T value) {
        return container.put(key, value);
    }


    public void putCache(String key, T value) {
        cache.put(key, value);
    }

    public void putNotFoundCache(String key) {
        not_found_cache.add(key);
    }

    public void putAll(Map<? extends String, ? extends T> m) {
        container.putAll(m);
    }

    public T remove(Object key) {
        return container.remove(key);
    }

    public int size() {
        return container.size() + cache.size();
    }

    public boolean isEmpty() {
        return container.isEmpty() && cache.isEmpty();
    }


    public Set<String> keySet() {
        return new HashSet<String>() {
            {
                addAll(container.keySet());
                addAll(cache.keySet());
            }
        };
    }


    public Collection<T> values() {
        return new HashSet<T>() {
            {
                addAll(container.values());
                addAll(cache.values());
            }
        };
    }

    public void clear() {
        container.clear();
        cache.clear();
    }

    public boolean containsKey(Object key) {
        if (cache.containsKey(key))
            return true;
        if (container.containsKey(key))
            return true;
        for (String s : container.keySet()) {
            if (Pattern.matches(s, (String) key))
                return true;
        }
        return false;
    }


    public boolean containsValue(Object value) {
        if (cache.containsValue(value))
            return true;
        if (container.containsValue(value))
            return true;
        return false;
    }


    public Set<Entry<String, T>> entrySet() {
        HashSet<Entry<String, T>> set = new HashSet<Entry<String, T>>();
        set.addAll(container.entrySet());
        set.addAll(cache.entrySet());
        return set;
    }
}
