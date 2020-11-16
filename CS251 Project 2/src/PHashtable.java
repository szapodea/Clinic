import java.util.*;

public class PHashtable {
    private ArrayList<Patient>[] table;
    private int size;
    private int capacity;

    //set the table size to the first 
    //prime number p >= capacity
    public PHashtable (int capacity) {
        if (!isPrime(capacity)) {
            this.table = new ArrayList[getNextPrime(capacity)];

            this.capacity = getNextPrime(capacity);
        } else {
            table = new ArrayList[capacity];
            this.capacity = capacity;
        }
        Arrays.fill(table, null);

        this.size = 0;
    }


    //return the Patient with the given name 
    //or null if the Patient is not in the table
    public Patient get(String name) {
	    int hash = name.hashCode() % capacity;
	    if (hash < 0) {
	        hash += capacity;
        }
	    if (table[hash] == null) {
	        return null;
        } else {
            for (int i = 0; i < table[hash].size(); i++) {
                if (table[hash].get(i).name().equals(name)) {
                    return table[hash].get(i);
                }
            }
        }
	    return null;
    }

    //put Patient p into the table
    public void put(Patient p) {
        int hash = p.name().hashCode() % capacity;
        if (hash < 0) {
            hash += capacity;
        }
        if (table[hash] != null) {
            if (table[hash].contains(p)) {
                return;
            }
            table[hash].add(p);

        } else {
            table[hash] = new ArrayList<Patient>();
            table[hash].add(p);

        }
        size++;
    }

    //remove and return the Patient with the given name
    //from the table
    //return null if Patient doesn't exist
    public Patient remove(String name) {
        Patient p = get(name);
        int hash = name.hashCode() % capacity;
        if (hash < 0) {
            hash += capacity;
        }
        if (table[hash] == null) {
            return null;
        } else {
            table[hash].remove(p);
            size--;
            return p;
        }
    }	    

    //return the number of Patients in the table
    public int size() {
	    return size;
    }

    //returns the underlying structure for testing
    public ArrayList<Patient>[] getArray() {
        return table;
    }
    
    //get the next prime number p >= num
    private int getNextPrime(int num) {
    if (num == 2 || num == 3)
        return num;
    int rem = num % 6;
    switch (rem) {
        case 0:
        case 4:
            num++;
            break;
        case 2:
            num += 3;
            break;
        case 3:
            num += 2;
            break;
    }
    while (!isPrime(num)) {
        if (num % 6 == 5) {
            num += 2;
        } else {
            num += 4;
           }
        }
        return num;
    }


    //determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if(num % 2 == 0){
            return false;
        }
        
	int x = 3;
	for(int i = x; i < num; i+=2){
	    if(num % i == 0){
		    return false;
        }
    }
	return true;
    }
}
      

