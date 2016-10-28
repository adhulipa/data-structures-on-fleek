import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by adhulipa on 10/28/16.
 */
public class MedianHeap<E extends Number & Comparable> implements Queue<Number> {

    private enum QueueSize { BIGGER, SMALLER };


    private PriorityQueue<E> lowers;
    private PriorityQueue<E> uppers;

    public MedianHeap() {
        super();
        lowers = new PriorityQueue<E>(Collections.reverseOrder());
        uppers = new PriorityQueue<E>();
    }

    @Override
    public boolean offer(Number number) {
        boolean offered;
        if ( !lowers.isEmpty() && isLessThan(number, lowers.peek())) {
            // Cast number to actual type E
            offered = lowers.offer( (E) number);
        } else if ( !uppers.isEmpty() && isGreaterThan(number, uppers.peek())) {
            offered = uppers.offer( (E) number);
        } else {
            // Otherwise, add new number to smaller of the two queues
            Queue smallerQueue = selectQueue(QueueSize.SMALLER);
            offered = smallerQueue.offer(number);
        }

        rebalance();
        return offered;
    }

    @Override
    public Double peek() {
        return this.median(false);
    }

    @Override
    public int size() {
        return lowers.size() + uppers.size();
    }

    @Override
    public boolean isEmpty() {
        return lowers.isEmpty() && uppers.isEmpty();
    }

    @Override
    public Double poll() {
        Double median = this.median(true);
        rebalance();
        return median;
    }

    private void rebalance() {
        while ( Math.abs(lowers.size() - uppers.size()) > 1 ) {
            Queue bigger  = selectQueue(QueueSize.BIGGER);
            Queue smaller = selectQueue(QueueSize.SMALLER);
            smaller.offer(bigger.poll());
        }
    }

    private Double median(boolean isPoll) {
        Double median = null;

        if ( queueSizesEqual() ) {
            median = (lowers.peek().doubleValue() + uppers.peek().doubleValue()) / 2;

            if (isPoll) {
                lowers.poll();
                uppers.poll();
            }

        } else { // peek() bigger queue
            Queue bigger = selectQueue(QueueSize.BIGGER);
            Number medianNumber = (Number) bigger.peek();
            median = medianNumber.doubleValue();

            if (isPoll) {
                bigger.poll();
            }
        }

        return median;

    }

    private boolean queueSizesEqual() {
        return lowers.size() == uppers.size();
    }

    private Queue selectQueue(QueueSize which) {
        Queue smaller = lowers;
        Queue bigger  = uppers;
        Queue answer;

        if (uppers.size() < lowers.size()) {
            smaller = uppers;
            bigger  = lowers;
        } else if (uppers.size() > lowers.size() ){
            bigger  = uppers;
            smaller = lowers;
        }

        switch (which ){

            case BIGGER:
                answer = bigger;
                break;

            case SMALLER:
                answer = smaller;
                break;

            default:
                answer =  smaller;
        }

        return answer;
    }

    private boolean isLessThan(Number newItem, E currentMin) {
        E item = (E) newItem;
        return item.compareTo(currentMin) < 0;
    }

    private boolean isGreaterThan(Number newItem, E currentMax) {
        E item = (E) newItem;
        return item.compareTo(currentMax) > 0;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        Not Implemented methods below
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public boolean remove(Object o) throws NotImplementedException { throw new NotImplementedException(); }

    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<Number> iterator() {
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
    public boolean add(Number i) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public Number remove()  {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c)  {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends Number> c) {
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
    public Number element()  {
        throw new NotImplementedException();
    }

}
