import java.util.*;

/**
 * @param <Key> - key type
 * @param <Value> - value type
 * @see CacheMap
 */
public class CacheMapImpl<Key, Value> implements CacheMap<Key, Value> {
    private static final long DEFAULT_TTL = 5000;

    private volatile Map<Key, Value> mStorage;
    private volatile Map<Key, Long> mCache;
    private Long mTimeToLive;

    public CacheMapImpl() {
        mStorage = new HashMap<Key, Value>();
        mCache = new HashMap<Key, Long>();
        mTimeToLive = DEFAULT_TTL;
    }

    @Override
    public void setTimeToLive(Long ttl) {
        validateCache();
        this.mTimeToLive = ttl;
    }

    @Override
    public void put(Key key, Value value) {
        validateCache();
        mStorage.put(key, value);
        mCache.put(key, System.currentTimeMillis());
    }

    @Override
    public Value get(Key key) {
        validateCache();
        return mStorage.get(key);
    }

    @Override
    public boolean isEmpty() {
        validateCache();
        return mStorage.isEmpty();
    }

    @Override
    public int size() {
        validateCache();
        return mStorage.size();
    }

    @Override
    public Value remove(Key key) {
        validateCache();
        mCache.remove(key);
        return mStorage.remove(key);
    }

    @Override
    public boolean containsValue(Value value) {
        validateCache();
        return mStorage.containsValue(value);
    }

    @Override
    public boolean containsKey(Key key) {
        validateCache();
        return mStorage.containsKey(key);
    }

    @Override
    public void clear() {
        mCache.clear();
        mStorage.clear();
    }

    public void validateCache()
    {
        Long time = System.currentTimeMillis();
        for(Iterator<Map.Entry<Key, Long>> it = mCache.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Key, Long> entry = it.next();
            if(time - entry.getValue() >= mTimeToLive) {
                it.remove();
                mStorage.remove(entry.getKey());
            }
        }
    }

    @Override
    public Set<Map.Entry<Key, Value>> entrySet() {
        validateCache();
        return mStorage.entrySet();
    }

    @Override
    public Set<Key> keySet() {
        validateCache();
        return mStorage.keySet();
    }

    @Override
    public int hashcode() {
        validateCache();
        return mStorage.hashCode();
    }

    @Override
    public Collection<Value> values() {
        validateCache();
        return mStorage.values();
    }

    @Override
    public void putAll(Map<? extends Key, ? extends Value> m) {
        validateCache();
        mStorage.putAll(m);
    }
}
