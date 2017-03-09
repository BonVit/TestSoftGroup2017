import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bonar on 3/4/2017.
 */
public class CacheMapImplTest {
    final static long TTL = 200;
    CacheMapImpl<Integer, String> cacheMap;

    @Before
    public void setUp() throws Exception
    {
        cacheMap = new CacheMapImpl<>();
    }

    @Test
    public void size() throws Exception {
        cacheMap.put(1, "item1");
        assertEquals(1, cacheMap.size());

        cacheMap.put(2, "item2");
        cacheMap.put(1, "item3");
        assertEquals(2, cacheMap.size());

        cacheMap.setTimeToLive(TTL);
        Thread.sleep(TTL);
        assertEquals(0, cacheMap.size());
    }

    @Test
    public void get() throws Exception {
        cacheMap.put(1, "item1");
        cacheMap.put(2, "item2");
        cacheMap.put(2, "item3");

        assertEquals("item1", cacheMap.get(1));
        assertEquals("item3", cacheMap.get(2));
    }

    @Test
    public void contains() throws Exception
    {
        cacheMap.setTimeToLive(TTL);

        assertEquals(true, cacheMap.isEmpty());

        cacheMap.put(1, "item1");
        cacheMap.put(2, "item2");
        cacheMap.put(2, "item3");

        assertEquals(true, cacheMap.containsValue("item1"));
        assertEquals(false, cacheMap.containsValue("item2"));
        assertEquals(true, cacheMap.containsKey(1));
        assertEquals(false, cacheMap.containsKey(3));

        Thread.sleep(TTL);

        assertEquals(false, cacheMap.containsValue("item1"));
        assertEquals(false, cacheMap.containsValue("item2"));
        assertEquals(false, cacheMap.containsKey(1));
        assertEquals(false, cacheMap.containsKey(3));
    }

    @Test
    public void isEmpty() throws Exception
    {
        cacheMap.setTimeToLive(TTL);
        assertEquals(true, cacheMap.isEmpty());

        cacheMap.put(1, "item1");
        cacheMap.put(2, "item2");
        cacheMap.put(2, "item3");
        assertEquals(false, cacheMap.isEmpty());

        Thread.sleep(TTL);

        assertEquals(true, cacheMap.isEmpty());
    }

    @Test
    public void remove() throws Exception
    {
        cacheMap.setTimeToLive(TTL);
        assertNull(cacheMap.remove(1));

        cacheMap.put(1, "item1");
        cacheMap.put(2, "item2");
        cacheMap.put(2, "item3");
        assertEquals("item1", cacheMap.remove(1));
        assertEquals("item3", cacheMap.remove(2));

        cacheMap.put(1, "item1");
        cacheMap.put(2, "item2");
        cacheMap.put(2, "item3");

        Thread.sleep(TTL);

        assertNull(cacheMap.remove(1));
    }

}