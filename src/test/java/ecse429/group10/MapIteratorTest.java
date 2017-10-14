package ecse429.group10;

import com.rits.cloning.Cloner;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.apache.commons.collections4.iterators.ObjectArrayIterator;
import org.apache.commons.collections4.iterators.ObjectArrayListIterator;
import org.apache.commons.collections4.list.CursorableLinkedList;
import org.apache.commons.collections4.list.NodeCachingLinkedList;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.hamcrest.internal.ArrayIterator;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Comparator.*;


import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for the MapIterator interface inside of the Apache commons collection
 */

@RunWith(JukitoRunner.class)
public class MapIteratorTest {

    Cloner cloner = new Cloner();

    public static class Module extends JukitoModule {

         @Override
        protected void configureTest() {

             // generic binds
             bindMany(IterableMap.class , ListOrderedMap.class , LinkedMap.class, BidiMap.class);

             // initialize empty instances of maps
             bindManyNamedInstances(IterableMap.class, "empty" , new ListOrderedMap() , new LinkedMap(), new TreeBidiMap());

             // initialize non-empty instances of maps
             bindManyNamedInstances(IterableMap.class, "filled" , new ListOrderedMap<String , Integer>()
                     {{
                        put("ONE" , 1);
                        put("TWO" , 2);
                     }},
                     new LinkedMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                         put("TWO" , 2);
                     }},
                     new TreeBidiMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                         put("TWO" , 2);
                     }});

             // initialize non-empty one element instances of maps
             bindManyNamedInstances(IterableMap.class, "one element" , new ListOrderedMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                     }},
                     new LinkedMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                     }},
                     new TreeBidiMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                     }});
         }


    }

    /**
     *
     * Test that there is no next element in an empty map
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failAfterLastElementEmptyMap(@All("empty") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        iterator.next();
    }

    /**
     *
     * Test that there is no second element in map of size 1 by calling next() twice
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failAfterLastElementOnedMap(@All("one element") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        iterator.next();
    }

    /**
     *
     * Test that there is no fourth element in map of size 2 by calling next() 3 times
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failAfterLastElementFilledMap(@All("filled") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        iterator.next();
        iterator.next();
    }

    /**
     *
     * Test that Test that getKey() returns an exception on an empty list
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failGetKeyEmptyMap(@All("empty") IterableMap map)
    {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        assertEquals("ONE" , iterator.getKey());
    }

    /**
     *
     * Test that Test that getKey() returns the correct key in a map of size 1
     * @param map
     *
     * */
    @Test
    public void failGetKeyOneMap(@All("one element") IterableMap map)
    {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        assertEquals("ONE" , iterator.getKey());
    }

    /**
     *
     * Test that Test that getKey() returns the correct key in a map of size 2
     * @param map
     *
     * */
    @Test
    public void failGetKeyFilledMap(@All("filled") IterableMap map)
    {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        assertEquals("ONE" , iterator.getKey());
    }

    /**
     *
     * Test that Test that getValue() returns an exception on an empty list
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failGetValueEmptyMap(@All("empty") IterableMap map)
    {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        assertEquals(1 , iterator.getValue());
    }

    /**
     *
     * Test that Test that getValue() returns the correct key in a map of size 1
     * @param map
     *
     * */
    @Test
    public void failGetValueOneMap(@All("one element") IterableMap map)
    {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        assertEquals(1 , iterator.getValue());
    }

    /**
     *
     * Test that Test that getValue() returns the correct key in a map of size 2
     * @param map
     *
     * */
    @Test
    public void failGetValueFilledMap(@All("filled") IterableMap map)
    {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        assertEquals(1 , iterator.getValue());
    }

    /**
     *
     * Test that the next() method returns the next element in map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldReturnNextElementOneElementMap(@All("one element") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        Object key = iterator.next();
        assertEquals("ONE" , key);
        assertEquals(1 , iterator.getValue());
    }

    /**
     *
     * Test that the next() method returns the next element in map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldReturnNextElementFilledMap(@All("filled") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        Object key = iterator.next();
        assertEquals("ONE" , key);
        assertEquals(1 , iterator.getValue());
    }

    /**
     *
     * Test that hasNext() returns false when called on an empty map
     * @param map
     *
     * */
    @Test
    public void shouldReturnFalseHasNextEmpty(@All("empty") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        assertFalse(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns false when called on last element of a map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldReturnFalseHasNextOneElement(@All("one element") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns false when called on last element of a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldReturnFalseHasNextFilledElement(@All("filled") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns true when called on map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldReturnTrueHasNextOneElement(@All("one element") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        assertTrue(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns true when called on a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldReturnTrueHasNextFilled(@All("filled") IterableMap map) {
        MapIterator iterator = map.mapIterator();
        assertTrue(iterator.hasNext());
    }

    /**
     *
     * Test that calling remove() on an empty map throws an exception
     * @param map
     *
     * */
    @Test(expected = IllegalStateException.class)
    public void failRemoveElementFromEmptyMap(@All("empty") IterableMap map) {
        IterableMap copiedMap = cloner.deepClone(map);
        MapIterator iterator = copiedMap.mapIterator();
        iterator.remove();
    }

    /**
     *
     * Test that remove() removes an element from a map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldRemoveOneElement(@All("one element") IterableMap map) {
        IterableMap copiedMap = cloner.deepClone(map);
        MapIterator iterator = copiedMap.mapIterator();
        iterator.next();
        iterator.remove();
        assertFalse(iterator.hasNext());
    }

    /**
     *
     * Test that Test that remove() removes an element from a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldRemoveFilledElement(@All("filled") IterableMap map) {
        IterableMap copiedMap = cloner.deepClone(map);
        MapIterator iterator = copiedMap.mapIterator();
        iterator.next();
        iterator.remove();
        iterator.next();
        assertEquals("TWO" , iterator.getKey());
    }
}