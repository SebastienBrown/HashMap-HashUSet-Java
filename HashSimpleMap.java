
public class HashSimpleMap<K extends Comparable<K>,V> implements SimpleMap<K,V>  {

    SimpleUSet<Pair<K,V>> set = new HashUSet<Pair<K,V>>();

    //returns the size of the map
    public int size(){
        return set.size();
    }

    //if the size of the map is 0, returns true
    public boolean isEmpty(){
        return set.size()==0;
    }

    //returns the value associated with a key, or null if the key is not found
    public V get(K key){

        Pair<K,V> p = new Pair<K,V>(key, null);

        //if the k is not found in the set, returns null
        if(set.find(p)==null){
            return null;
        }
        //if the key is found, returns its value
        else{
            return set.find(p).value;
        }     
    }

    //changes the value associated with a certain key and returns the previous value
    //if the key is not found, adds a pair with that key and returns null
    public V put(K key, V value){

        Pair<K,V> p = new Pair<K,V>(key, value);

        //if the pair p is not found in the set, adds it and returns null
        if(set.find(p)==null){
            set.add(p);
            return null;
        }

        //if p is found, updates its value and returns the previous value
        else{
            Pair<K,V> temp = set.find(p);
            set.remove(p);
            set.add(p);
            return temp.value;
        }

    }

    //removes the given key if present in the set and returns it, otherwisde returns null
    public V remove(K key){

        Pair<K,V> p = new Pair<K,V>(key, null);

        //if a pair with that key is found, returns the associated value
        if(set.find(p)!=null){
            V temp = set.remove(p).value;
            return temp;
        }

        //if a pair with taht key is not found, returns null
        else{
            return null;
        }
    }

    //returns true if the given key is in the set, or false otherwise
    public boolean contains(K key){
        if(get(key)!=null){
            return true;
        }
        return false;
    }

    //initializes a key/value pair class
    private class Pair<K extends Comparable<K>,V> implements Comparable<Pair<K,V> >{
         K key;
         V value;

        public Pair(K key, V value){
            this.key=key;
            this.value=value;
        }

        public String toString(){
            return value+" "+key;
        }

        //impelments comparable interface for the pair class
        @Override
        public int compareTo(Pair<K,V> p) {
            return key.compareTo(p.key);
        }
        
        //returns the java hashcode of key
        public int hashCode(){
            return key.hashCode();
        }
        
    }
    
}

