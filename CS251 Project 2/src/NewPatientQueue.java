import java.util.Arrays;

public class NewPatientQueue {
    private Patient[] array;
    private PHashtable table;
    private int size;

    //constructor: set variables
    //capacity = initial capacity of array
    public NewPatientQueue(int capacity) {
        if (capacity == 0) {
            this.array = null;
        } else {
            this.array = new Patient[capacity];
            Arrays.fill(array, null);
            table = new PHashtable(capacity);
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
            size++;
            p.setPosInQueue(0);
            return 0;
        }
        for (int i = 0; i < size; i++) {
            if (array[i] == null) {
                array[i] = p;
                size++;
                return insertHelper(i);

            }
        }
        array[size()] = p;
        int index = size();
        size++;
        table.put(p);
        int insertHelperIndex = insertHelper(index);
        array[insertHelperIndex].setPosInQueue(insertHelperIndex);
        return insertHelperIndex;
    }

    public int insertHelper(int location) {
        if (array[location].compareTo(array[(location - 1) / 2]) > 0) {
            Patient swap = array[(int) (location - 1) / 2];
            array[(location - 1) / 2] = array[location];
            array[(location - 1) / 2].setPosInQueue((location - 1) / 2);
            array[location] = swap;
            array[location].setPosInQueue(location);
            return insertHelper((location - 1) / 2);
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
        delete.setPosInQueue(-1);
        size--;
        table.remove(delete.name());
        return delete;
    }

    public void merge(int leftInt, int rightInt) {
        boolean leftBool = false;
        boolean rightBool = false;
        if (leftInt < array.length) {
            leftBool = true;
        }
        if (rightInt < array.length) {
            rightBool = true;
        }
        if (!leftBool && !rightBool) {
            return;
        }
        Patient left = null;
        Patient right = null;
        if (leftBool && !rightBool) {
            left = array[leftInt];

        } else if (!leftBool && rightBool){
            right = array[rightInt];
        } else {
            left = array[leftInt];
            right = array[rightInt];
        }

        if (left == null && right == null) {
            return;
        }
        if (left == null || right == null) {
            if (left == null) {
                array[(rightInt - 1) / 2] = array[rightInt];
                array[(rightInt - 1) / 2].setPosInQueue((rightInt - 1) / 2);
                array[rightInt] = null;
                //if (rightInt * 2 + 1 < array.length && rightInt * 2 + 2 < array.length) {
                    merge(rightInt * 2 + 1, rightInt * 2 + 2);
                //}
                return;
            } else {
                array[(leftInt - 1) / 2] = array[leftInt];
                array[(leftInt - 1) / 2].setPosInQueue((leftInt - 1) /2);
                array[leftInt] = null;
                //if (leftInt * 2 + 1 < array.length && leftInt * 2 + 2 < array.length) {
                    merge(leftInt * 2 + 1, leftInt * 2 + 2);
                //}
                return;
            }
        }
        if (left.compareTo(right) > 0) {
            array[(leftInt - 1) / 2] = array[leftInt];
            array[(leftInt - 1) / 2].setPosInQueue((leftInt - 1) /2);
            array[leftInt] = null;
            //if (leftInt * 2 + 1 < array.length && leftInt * 2 + 2 < array.length) {
                merge(leftInt * 2 + 1, leftInt * 2 + 2);
            //}
        } else {
            array[(rightInt - 1) / 2] = array[rightInt];
            array[(rightInt - 1) / 2].setPosInQueue((rightInt - 1) /2);
            array[rightInt] = null;
            //if (rightInt * 2 + 1 < array.length && rightInt * 2 + 2 < array.length) {
                merge(rightInt * 2 + 1, rightInt * 2 + 2);
            //}
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

    /*TO BE COMPLETED IN PART 2*/

    //remove and return the Patient with
    //name s from the queue
    //return null if the Patient isn't in the queue
    public Patient remove(String s) {
        Patient p = table.get(s);
        if (p == null) {
            return null;
        }
        int index = p.posInQueue();
        removeHelper(p, index);
        return p;
    }


    public void removeHelper(Patient p, int index) {
        if (index > array.length || array[index] == null) {
            return;
        }
        if (array[index] == p) {
            array[index] = null;
            merge(index * 2 + 1, index * 2 + 2);
            p.setPosInQueue(-1);
            table.remove(p.name());
            size--;
        }


    }


    //update the emergency level of the Patient
    //with name s to urgency
    public void update(String s, int urgency) {
        Patient p = table.get(s);
        if (p == null) {
            return;
        }
        int index = p.posInQueue();
        array[index].setUrgency(urgency);
        table.get(s).setUrgency(urgency);
        int leftIndex = 2 * index + 1;
        int rightIndex = 2 * index + 2;
        boolean left = true;
        boolean right = true;
        if (!(leftIndex < array.length) || array[leftIndex] == null ||
                !(array[index].compareTo(array[leftIndex]) < 0)) {
            left = false;
        }
        if (!(rightIndex < array.length) || (array[rightIndex] == null) ||
                !(array[index].compareTo(array[rightIndex]) < 0)) {
            right = false;
        }
        if (array[(index - 1) / 2].compareTo(array[index]) < 0) {
            swim(index);
        } else if (left && right) {
            if (array[leftIndex].compareTo(array[rightIndex]) > 0) {
                sink(index, leftIndex);
            } else {
                sink(index,rightIndex);
            }
        } else if(left) {
            sink(index, leftIndex);
        } else if(right) {
            sink(index, rightIndex);
        }
    }

    public void swim(int index) {
        if (index == 0 || index >= array.length) {
            return;
        }
        int newIndex = (index - 1) / 2;
        if (array[newIndex].compareTo(array[index]) < 0) {
            Patient swap = array[newIndex];
            array[newIndex] = array[index];
            array[newIndex].setPosInQueue(newIndex);
            array[index] = swap;
            array[index].setPosInQueue(index);
            swim(newIndex);
            return;
        }
    }

    public void sink(int index, int newIndex) {
        if (index >= array.length || newIndex >= array.length) {
            return;
        }
        Patient swap = array[newIndex];
        array[newIndex] = array[index];
        array[newIndex].setPosInQueue(newIndex);
        array[index] = swap;
        array[index].setPosInQueue(index);
        int leftIndex = 2 * newIndex + 1;
        int rightIndex = 2 * newIndex + 2;
        boolean left = true;
        boolean right = true;
        if (!(leftIndex < array.length) || !(array[newIndex].compareTo(array[leftIndex]) < 0)) {
            left = false;
        }
        if (!(rightIndex < array.length) || !(array[newIndex].compareTo(array[rightIndex]) < 0)) {
            right = false;
        }
        if (left && right) {
            if (array[leftIndex].compareTo(array[rightIndex]) > 0) {
                sink(newIndex, leftIndex);
            } else {
                sink(newIndex,rightIndex);
            }
        } else if(left) {
            sink(newIndex, leftIndex);
        } else if(right) {
            sink(newIndex, rightIndex);
        } else {
            return;
        }
    }

    public Patient get(String name) {
        return table.get(name);
    }
}
