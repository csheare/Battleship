package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/21/2016.
 */
public class BoundedSet {
    Coordinate[] contents;
    int count;

    public BoundedSet(int max) {
        count = 0;
        contents = new Coordinate[max];
    }

    /**
     * @param element Element to be inserted into the set.
     * @requires element != null and [element is not in the set.] and
     * [The set is not full.]
     * @ensures <pre>
     * [element is unchanged.] and [element is inserted into the set.]
     * </pre>
     */
    public void insert(Coordinate element) {
        contents[count++] = element;//count incremented post function
    }

    /**
     * @param element Element to find in the set.
     * @return Indicator of element presence in the set.
     * @requires element != null
     * @ensures <pre>
     * [element is unchanged.] and
     * [The return is true if element was found in the set; otherwise
     * the return is false.]
     * </pre>
     */
    public boolean contains(Coordinate element) {
        for (Coordinate i : contents) {
            if((i != null)) {
                if (i.equals(element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param element Element to remove from the set.
     * @requires element != null and [element is in the set.]
     * @ensures <pre>
     * [element is unchanged.] and [element is removed from the set.]
     * </pre>
     */
    public void remove(Integer element) {
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (contents[i].equals(element)) {
                index = i;
            }
        }

        for (int i = index; i < count - 1; i++) {
            contents[i] = contents[i + 1];
        }
        count--;
    }


    /**
     * @return The element removed from the set.
     * @requires [The set is not empty.]
     * @ensures <pre>
     * [An arbitrarily chosen element is removed from the set; this element
     * is returned to the caller.]
     * </pre>
     */
    public Coordinate removeAny() {
        return contents[--count];
    }

    /**
     * @return The stringified contents of the set.
     * @requires true
     * @ensures <pre>
     * [The contents of the set are unchanged.] and
     * [The contents of the set are returned as a string.]
     * </pre>
     */
    public String toString() {
        String str = "{";
        boolean first = true;
        for (int i = 0; i < count; i++) {
            if (first) {
                str += contents[i];
                first = false;
            }
            else {
                str += "," + contents[i];
            }
        }
        return str + "}";
    }

    public boolean equals(Object o) {
        // Check for type equality.
        if (!(o instanceof BoundedSet)) return false;
        // The object is of the correct type! Cast it!
        BoundedSet rhs = (BoundedSet) o;
        // Check for "count" equality.
        if (this.count != rhs.count) return false;
        // Check for "contents" equality. Remember, a set is unordered.
        for (int i = 0; i < this.count; ++i) {
            boolean shared = false;
            for (int j = 0; j < this.count; ++j)
                if(this.contents[i].equals(rhs.contents[j])) {
                    shared = true;
                    break;
                }
            if(!shared) return false;
        }
        return true;
    }
    public BoundedSet(int size, Coordinate[] initial) {
        this.contents = new Coordinate[size];
        this.count = initial.length;
        for(int i = 0; i < this.count; ++i)
            this.contents[i] = initial[i];
    }



}
