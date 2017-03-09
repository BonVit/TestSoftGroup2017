import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A map, that deletes its items when they expire\n
 * Items will be checked and deleted if they should be on each method call
 * @param <Key> - key type
 * @param <Value> - value type
 */
public interface CacheMap<Key, Value> {

    /**
     * Set item existence time
     * @param ttl - time
     */
    public void setTimeToLive(Long ttl);

    /**
     * Add new item to the map
     * @param key - key
     * @param value - value
     */
    public void put(Key key, Value value);

    /**
     * Get value for key
     * @param key - key
     * @return value
     */
    public Value get(Key key);

    /**
     * Returns true if map is empty
     */
    public boolean isEmpty();

    /**
     * Returns map's size
     */
    public int size();

    /**
     * Removes item from map with specified key
     * @param key - key
     * @return Value of removed item
     */
    public Value remove(Key key);

    /**
     * Returns true if map contains one or more items with specified value
     * @param value - value
     */
    public boolean containsValue(Value value);

    /**
     * Return true if map contains item with specified key
     * @param key - key
     */
    public boolean containsKey(Key key);

    /**
     * Clears map
     */
    public void clear();

    /**
     * Returns a Set view of the mappings contained in this map.
     */
    public Set<Map.Entry<Key, Value>> entrySet();

    /**
     * Returns a Set view of the keys contained in this map.
     */
    public Set<Key> keySet();

    /**
     * Returns the hash code value for this map.
     */
    public int hashcode();

    /**
     * Returns a Collection view of the values contained in this map.
     */
    public Collection<Value> values();

    /**
     * Compares the specified object with this map for equality.
     * @param o
     */
    public boolean equals(Object o);

    /**
     * Copies all of the mappings from the specified map to this map (optional operation).
     * @param m
     */
    public void putAll(Map<? extends Key, ? extends Value> m);


}
