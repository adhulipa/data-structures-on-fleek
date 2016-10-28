import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by adhulipa on 10/28/16.
 */
class EvictingPriorityQueue<I extends Comparable<? super I>> implements Queue<I> {

    private PriorityQueue<Entry> itemQueue;
    private PriorityQueue<Entry> timestampQueue;
    private double capacity;


    class Entry {
        I item;
        long timestamp;

        Entry(I item) {
            this.item      = item;
            this.timestamp = new Date().getTime();
        }
    }

    class EntryItemComparator implements Comparator<Entry> {
        @Override
        public int compare(Entry e1, Entry e2) {
            return e1.item.compareTo(e2.item);
        }
    }

    class EntryTimestampComparator implements Comparator<Entry> {
        @Override
        public int compare(Entry e1, Entry e2) {
            return Long.compare(e1.timestamp, e2.timestamp);
        }
    }

    public EvictingPriorityQueue() {
        this(Double.POSITIVE_INFINITY);
    }

    public EvictingPriorityQueue(double capacity) {
        this.capacity  = capacity;
        itemQueue      = new PriorityQueue<Entry>(new EntryItemComparator());
        timestampQueue = new PriorityQueue<Entry>(new EntryTimestampComparator());
    }


    @Override
    public int size() {
        return itemQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return itemQueue.isEmpty();
    }

    @Override
    public boolean offer(I item)  {
        // If we're reached current capacity, then evict the oldest entry before adding new item
        if (itemQueue.size() >= capacity) {
            evictEldest();
        }

        // Create new entry for item
        Entry entry = new Entry(item);

        // Add entry to our internal queues
        boolean offeredToTimestampQueue = timestampQueue.offer(entry);
        boolean offeredToItemQueue = itemQueue.offer(entry);

        return offeredToTimestampQueue && offeredToItemQueue;

    }

    @Override
    public I poll()  {
        Entry entry = itemQueue.poll();
        timestampQueue.remove(entry);

        return entry.item;
    }

    @Override
    public I peek()  {
        return itemQueue.peek().item;
    }

    private void evictEldest() {
        Entry eldest = timestampQueue.poll();
        itemQueue.remove(eldest);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        Not Implemented methods below
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public boolean remove(Object o) { throw new NotImplementedException(); }

    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<I> iterator() {
        throw new NotImplementedException();
    }

    @Override
    public Object[] toArray()  {
        throw new NotImplementedException();
    }

    @Override
    public <T> T[] toArray(T[] a)  {
        throw new NotImplementedException();
    }

    @Override
    public boolean add(I i) {
        throw new NotImplementedException();
    }

    @Override
    public I remove()  {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c)  {
        throw new NotImplementedException();
    }


    @Override
    public boolean addAll(Collection<? extends I> c) {
        throw new NotImplementedException();
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }


    @Override
    public boolean retainAll(Collection<?> c)  {
        throw new NotImplementedException();
    }


    @Override
    public void clear()  {
        throw new NotImplementedException();
    }

    @Override
    public I element()  {
        throw new NotImplementedException();
    }



}
