import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashUSet<E extends Comparable<E>> implements SimpleUSet<E> {
    public static final int DEFAULT_LOG_CAPACITY = 4;
    
    protected int logCapacity = DEFAULT_LOG_CAPACITY; // value d from lecture
    protected int capacity = 1 << logCapacity;        // n = 2^d
    protected int size = 0;
    protected Object[] table;    // array of heads of linked lists

    // final = can't be changed after initial assignment!
    protected final int z;

    public HashUSet() {
	// set capacity to 2^logCapacity
	int capacity = 1 << logCapacity;
	
	table = new Object[capacity];

	// add a sentinel node to each list in the table
	for (int i = 0; i < capacity; ++i) {
	    table[i] = new Node<E>(null);
        System.out.println(table[i]);
	}

	// fix a random odd integer
	Random r = new Random();	
	z = (r.nextInt() << 1) + 1;
    }
    
    @Override
    public int size() {
	return this.size;
    }

    @Override
    public boolean isEmpty() {
	return this.size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E x) {

        //if array is full, increase array capacity
        if (size==capacity){
            increaseCapacity();
        }

        //get index of value
        int index = getIndex(x);

        //check whether sentinel node is null, add if true
        if(table[index]==null){
            Node toAdd=new Node(x);
                table[index]=toAdd;
                size++;
                return true;
        }
        
        else{

            //casts the head of the lsit to a node
            Node nd = (Node) table[index];  

            //if the next node exists, checks whether it is equal to x
            if(nd.next!=null){
                E top=(E) nd.next.value;

                //if nd.next doesn't have value x, update nd
                if(!(top.compareTo(x)==0)){
                    nd=nd.next;
                }

                //if nd.next has value nd, return false
                else if(top.compareTo(x)==0){
                    return false;
                }

                //iterates through the list till the end is reached
                while(nd.next!=null){

                    //if a node with value x is found, return false
                    if(nd.next.value.compareTo(x)==0){
                        return false;
                    }

                    //if nd.next does not have value x, moves nd forwards
                    else{
                        nd=nd.next;
                    }
                }

                //if the list end is reached and x is not found, creates a node with value x and return true
                Node toAdd=new Node(x);
                nd.next=toAdd;
                size++;
                return true;
            }

            //if the next node after the head is null, create a new node with value x and return true
            else{
                Node toAdd=new Node(x);
                nd.next=toAdd;
                size++;

                return true;
                }
            }
        }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(E x) {

        int index = getIndex(x);

        Node nd = (Node) table[index];
        Node prev = null;

         //if the next two node exists, checks whether it is equal to x
         if(nd.next!=null && nd.next.next!=null){
            E nextval=(E) nd.next.value;

            //if nd.next doesn't have value x, update nd
            if(nextval != null && !(nextval.compareTo(x)==0)){
                nd=nd.next;
            }

            //if nd.next has value nd, return false
            else if(nextval != null  && nextval.compareTo(x)==0){
                nd.next=nd.next.next;
                size--;
                return nextval;
            }
        }

        //if only one other node exists
        else if(nd.next!=null && nd.next.next==null){
            E nextval=(E) nd.next.value;

            //if the node has the desired value, remove it
            if(nextval != null && nextval.compareTo(x)==0){
                nd.next=null;
                size--;
                return nextval;
            }

            //if nd.next doesn't have value x, update nd
            else{
                return null;
            }
        }
        
        //if the next node is null, return  null
        else if(nd.next==null){
                return null;
            }


        //iterate through the rest of the list until the end is reached
        while(nd.next!=null){

            //if the next two nodes exist, run this if stetement
            if(nd.next.next!=null){

            if(nd.next.value.compareTo(x)==0){
                E temp = (E) nd.next.value;
                nd.next=nd.next.next;
                size--;
                return temp;
            }

            //if nd.next does not have the required value, move nd forwards
            else{
                prev=nd;
                nd=nd.next;
            }
        }

        //if only one more node exists, run this if statement
        if(nd.next.next==null){
            if(nd.next.value.compareTo(x)==0){
                E temp = (E) nd.next.value;
                nd.next=null;
                size--;
                return temp;
            }          
        }

        }

    //if the desired node is not found, return null
	return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E find(E x) {

        //finds hash value of x, which corresponds to its index in table
        int index = getIndex(x);

        Node nd = (Node)table[index];

        //if the next node after the head exists, checks whether it has equal value to x
        if(nd.next!=null){
            E top=(E) nd.next.value;

            //if x is not found, update the position of nd (moving forwards)
            if(!(top.compareTo(x)==0)){
                nd=nd.next;
            }

            //if x is found, return it
            else if(top != null && top.compareTo(x)==0){
                return top;
            }

            //once the first post-head node has been tested, iterates through the list until the end is reached
            while(nd.next!=null){

                //if the next node has value x, return it
                if(nd.next.value!=null && nd.next.value.compareTo(x)==0){
                    return (E) nd.next.value;
                }

                //else, update the position of nd
                else{
                    nd=nd.next;
                }
            }
        }   
        //if the desired value x is not found, return null
        return null;
        }
        

    protected int getIndex(E x) {
	// get the first logCapacity bits of z * x.hashCode()
	return ((z * x.hashCode()) >>> 32 - logCapacity);
    }

    // @SuppressWarnings("unchecked")
    // protected void increaseCapacity() {
	
    // 	logCapacity += 1;
    // 	capacity = capacity << 1;

    // 	// store the old hash table
    // 	Object[] oldTable = table;

    // 	// make a new new has table and initialize it
    // 	table = new Object[capacity];

    // 	// add old lists to new table
    // 	for (int i = 0; i < oldTable.length; ++i) {
    // 	    table[i] = oldTable[i];	    
    // 	}

    // 	// add sentinel nodes to new table
    // 	for (int i = oldTable.length; i < table.length; ++i) {
    // 	    table[i] = new Node<E>(null);
    // 	}
    // }

    @SuppressWarnings("unchecked")
    protected void increaseCapacity() {	
    	logCapacity += 1;
    	capacity = capacity << 1;

    	// store the old hash table
    	Object[] oldTable = table;

    	// make a new new has table and initialize it
    	table = new Object[capacity];
	    
    	for (int i = 0; i < table.length; ++i) {
    	    table[i] = new Node<E>(null);
    	}

    	// reset the size to 0 since it will get incremented when we
    	// add elements to the new table
    	size = 0;

    	// iterate over lists in oldTable and add elements to new table
    	for (int i = 0; i < oldTable.length; ++i) {
    	    Node<E> nd = ((Node<E>)oldTable[i]).next;
    	    while (nd != null) {
    		this.add(nd.value);
    		nd = nd.next;
    	    }
    	}
    }
    
    protected class Node<E extends Comparable<E>> {
	protected Node<E> next = null;
	protected E value;

	public Node(E value) {
	    this.value = value;
	}
    }
}
