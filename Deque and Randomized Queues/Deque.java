import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Item[] items;
    private int front, end;
    /** Creates an empty deque. */
    public Deque() {
        items = (Item[]) new Object[8];
        size = 0;
        front = 0; // Set the front to be the first position in array
        end = 1; // Set the end to be the last position in array.
    }

    /** Returns the index of the position right after the input position . */
    private int nextPos(int pos) {
        int nextPos = end + 1;
        if (nextPos == items.length) {
            nextPos = 0;
        }
        return nextPos;
    }

    /** Returns the index of the position right before the input position. */
    private int prevPos(int pos) {
        int prevPos = front - 1;
        if (prevPos < 0) {
            prevPos = items.length - 1;
        }
        return prevPos;
    }

    /** Returns true if the deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the numbers of items in the deque. */
    public int size() {
        return size;
    }

    /** Resizes the deque when necessary.
     * eg: 1. Halve the deque when 3/4 of the deque is empty.
     *     2. Double the deque when the deque is full. */
    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        if (front <= end) {
            System.arraycopy(items, front, newItems, 0, size);
        }
        else {
            int lengthOfFirstPart = items.length - front;
            int lengthOfSecondPart = items.length - lengthOfFirstPart;
            System.arraycopy(items, front, newItems, 0, lengthOfFirstPart);
            System.arraycopy(items, 0, newItems, lengthOfFirstPart, lengthOfSecondPart);
        }

    }
    /** Adds an item to the front. */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null item");
        }

        if (size == items.length) {
            resize(size * 2);
        }

        items[front] = item;
        front = prevPos(front);
        size++;
    }

    /** Adds an item to the end.*/
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null item");
        }
    }

    /** Removes and returns the item from the front. */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("No item to be removed");
        }
    }

    /** Removes and returns the item from the end. */
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("No item to be removed");
        }
    }

    /** Returns an iterator over items from front to the end. */
    public Iterator<Item> iterator() {
        return new ArrayIterator<Item>();
    }

    private class ArrayIterator<Item> implements Iterator<Item> {
        private int itemsLeft = size;

        public boolean hasNext() {

        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("This operation is not supported");
        }

        public Item next() {
            if (itemsLeft == 0) {
                throw new java.util.NoSuchElementException("There's no more items");
            }
        }

    }
    /** Unit tests. */
    public static void main(String[] args) {

    }
}

