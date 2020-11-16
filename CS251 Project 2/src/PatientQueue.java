import java.util.Arrays;

public class PatientQueue {
    private Patient[] array;
    private int size;

    //constructor: set variables
    //capacity = initial capacity of array 
    public PatientQueue(int capacity) {
        if (capacity == 0) {
            this.array = null;
        } else {
            this.array = new Patient[capacity];
            Arrays.fill(array, null);
        }
        size = 0;
    }

    //insert Patient p into queue
    //return the final index at which the patient is stored
    //return -1 if the patient could not be inserted
    public int insert(Patient p) {
        if (array.length == this.size()) {
            return -1;
        }
        if (this.size() == 0) {
            array[0] = p;
            size ++;
            return 0;
        }
        for (int i = 0; i < size; i++) {
            if (array[i] == null) {
                array[i] = p;
                size ++;
                insertHelper(i);
                return -1;
            }
        }
        array[size()] = p;
        int index = size();
        size ++;
        return insertHelper(index);
    }

    public int insertHelper(int location) {
        if (array[location].compareTo(array[(int) (location - 1) / 2]) > 0) {
            Patient swap = array[(int) (location - 1) / 2];
            array[(int) (location - 1) / 2] = array[location];
            array[location] = swap;
            return insertHelper((int) (location - 1) / 2);
        } else {
            return location;
        }
    }

    //remove and return the patient with the highest urgency level
    //if there are multiple patients with the same urgency level,
    //return the one who arrived first
    public Patient delMax() {
        if (this.size == 0) {
            return null;
        }
        Patient delete = getMax();
        array[0] = null;
        merge(1, 2);
        size--;
        return delete;
    }

    public void merge(int leftInt, int rightInt) {
        if (leftInt > array.length || rightInt > array.length) {
            return;
        }
        Patient left = array[leftInt];
        Patient right = array[rightInt];
        if (left == null || right == null) {
            if (left == null) {
                array[(rightInt - 1) / 2] = array[rightInt];
                array[rightInt] = null;
                if (rightInt * 2 + 1 < array.length && rightInt * 2 + 2 < array.length) {
                    merge(rightInt * 2 + 1, rightInt * 2 + 2);
                }
                return;
            } else {
                array[(leftInt - 1) / 2] = array[leftInt];
                array[leftInt] = null;
                if (leftInt * 2 + 1 < array.length && leftInt * 2 + 2 < array.length) {
                    merge(leftInt * 2 + 1, leftInt * 2 + 2);
                }
                return;
            }
        }
        if (left.compareTo(right) > 0) {
            array[(leftInt - 1) / 2] = array[leftInt];
            array[leftInt] = null;
            if (leftInt * 2 + 1 < array.length && leftInt * 2 + 2 < array.length) {
                merge(leftInt * 2 + 1, leftInt * 2 + 2);
            }
        } else {
            array[(rightInt - 1) / 2] = array[rightInt];
            array[rightInt] = null;
            if (rightInt * 2 + 1 < array.length && rightInt * 2 + 2 < array.length) {
                merge(rightInt * 2 + 1, rightInt * 2 + 2);
            }
        }
    }

    //return but do not remove the first patient in the queue
    public Patient getMax() {
        return array[0];
    }

    //return the number of patients currently in the queue
    public int size() {
        return size;
    }

    //return true if the queue is empty; false else
    public boolean isEmpty() {
        return array[0] == null;
    }

    //used for testing underlying data structure
    public Patient[] getArray() {
        return array;
    }
}
    