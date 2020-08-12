package memberSystem;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

public class GHashTable<K, V> implements Iterable<NameInfoPair>{
	// fields
	public NameInfoPair pair; // entry
	// declare generic type
	public ArrayList<LinkedList<NameInfoPair>> GHashTable;
	public int numEntries;
	public int numBuckets;
	public static final double loadFactor = 0.75;
	// Calculate load factor: numEntries / numBuckets
	
	/*
	 * Note that the order of members within each LinkedList does NOT matter.
	 */
	
	// constructor
	/*
	 * Here in the constructor we create an empty hash table.
	 * In essense the hash table is actually an ArrayList which contains a specific number of LinkedLists and inside
	 * each LinkedList are some NameInfoPairs.
	 */
	
	public GHashTable(int buckets) {
		this.numEntries = 0;
		this.numBuckets = buckets;
		this.GHashTable = new ArrayList<LinkedList<NameInfoPair>>(0);
		for (int i = 0; i < this.numBuckets; i++) {
			this.GHashTable.add(new LinkedList<NameInfoPair>());
		}
	}
	
	// Here are some helper methods:
	
	public ArrayList<LinkedList<NameInfoPair>> getHashTable() {
		return this.GHashTable;
	}
	
	public int getNumMem() {
		return this.numEntries;
	}
	
	public int getNumBuckets() {
		return this.numBuckets;
	}
	
	public boolean isEmpty() {
		return this.numEntries == 0;
	}
	
	// Below is the main part of the whole class:
	
	public int hashFunction(String name) {
		int hashValue = Math.abs(name.hashCode()) % this.numBuckets;
		return hashValue;
	}
	
	
	/*
	 * addMember method:
	 * If the member to be added is not in our Hash Table, then we add it in and do rehash if necessary.
	 * 
	 * If the member to be added is originally in our Hash Table, then we check whether the information is same or not.
	 * -- If the information is the same then we do not add anything to it and numEntries will remain the same; if the
	 * -- information is different then we return the previous Member and update the new information in our Hash Table.
	 */
	
	public Member addMember(Member m) {
		NameInfoPair toAdd = new NameInfoPair(m);
		// First add then rehash bc we need to check whether this member are in the Hash Table or not.
		int index = this.hashFunction(m.getName());
		// If the Hash Table is originally empty:
		if (this.numBuckets == 0) {
			this.numBuckets = 1;
			this.numEntries += 1;
			this.getHashTable().get(index).add(new NameInfoPair(m));
			System.out.println(m.toString() + "is registered successfully!");
		}
		// else: not empty. Check the existence of this member.
		LinkedList<NameInfoPair> target = this.getHashTable().get(index);
		if (target.size() != 0) {  // This LinkedList contains at least one member
			for (NameInfoPair pair: target) {
				if (pair.equals(toAdd)) {  // This member exists in the Hash Table
					throw new IllegalArgumentException("This customer is already a registered member.");
				}
			}
		}
		else {
			// else: this member is not in the Hash Table
			target.add(new NameInfoPair(m));
			this.numEntries += 1;
			System.out.println(m.toString() + "is registered successfully!");
		}
		// rehash if necessary
		if (this.numEntries / this.numBuckets >= this.loadFactor) {
			this.rehash();
		}
		return m;
	}
	
	
	/*
	 * This method is used to remove a specific member out of the Hash Table
	 * 
	 * If this member is in the Hash Table, then remove it and return this member.
	 * if this member is not in the Hash Table, raise an exception about this and return null.
	 * 
	 * Remember to reduce numEntries by 1.
	 */
	
	public Member remove(String name) {
		int index = this.hashFunction(name);
		boolean exist = false;
		Member output = null;
		LinkedList<NameInfoPair> targetLL = this.getHashTable().get(index);
		for (int i = 0; i < targetLL.size(); i++) {
			if (targetLL.get(i).getValue().getName().equals(name)) {
				output = targetLL.remove(i).getValue();
				this.numEntries -= 1;
				System.out.println("Customer" + name + "has been removed successfully!");
				exist = true;
			}
		}
		if (exist == false) {
			throw new IllegalArgumentException("This member does not exist in the system.");
		}
		return output;
	}
	
	public void empty() {
		this.GHashTable = new ArrayList<LinkedList<NameInfoPair>>(0);
		this.numBuckets = 0;
		this.numEntries = 0;
	}
	
	
	/*
	 * Rehash method:
	 * When doing rehash, numBuckets should double.
	 * That is, make a new ArrayList and then re-put all the member inside the new 'Hash Table' (ArrayList).
	 */
	
	public void rehash() {
		// get a copy of the original Hash Table
		int prevBuckets = this.numBuckets;
		GHashTable<String, NameInfoPair> newTable = new GHashTable<String, NameInfoPair>(prevBuckets * 2);
		for (NameInfoPair pair: this) {
			newTable.addMember(pair.getValue());
		}
		this.GHashTable = newTable.getHashTable();
		this.numBuckets = newTable.getNumBuckets();
		this.numEntries = newTable.getNumMem();
	}
	
	
	/*
	 * checkMember:
	 * Input: the name of the customer that is to be checked.
	 * Output: If the customer is a member then return their member information; if the customer is not a member then 
	 * throw an exception.
	 */
	
	public Member checkMember(String name) {
		int index = this.hashFunction(name);
		LinkedList<NameInfoPair> target = this.GHashTable.get(index);
		for (NameInfoPair pair: target) {
			if (pair.getKey() == name) {
				return pair.getValue();
			}
		}
		throw new IllegalArgumentException(name + "is not a registered member.");
		//return null;
	}
	
	
	// a helper method
	public double credits(NameInfoPair pair) {
		double result = pair.getValue().getPoints() * 0.8 + pair.getValue().getConsTimes() * 0.2;
		return result;
	}
	
	
	/*
	 * We sort all the member in the Hash Table based on their points AND consTimes.
	 * We measure their priority based on this formula: Credits = points * 0.8 + consTimes * 0.2 
	 * Member who have the highest credits will be the first one in the output ArrayList.
	 */
	
	public static ArrayList<NameInfoPair> sort(GHashTable<String, NameInfoPair> table) {
		// first get an ArrayList of all the members (each node is a NameInfoPair)
		ArrayList<NameInfoPair> tmpAL = new ArrayList<NameInfoPair>(0);
		for (NameInfoPair pair: table) {
			tmpAL.add(pair);
		}
		
		return null;
	}
	
	@Override
	public MemHashIterator iterator() {
		return new MemHashIterator();
	}
	
	private class MemHashIterator implements Iterator<NameInfoPair> {
		// fields
		private ArrayList<NameInfoPair> iterList;
		private int curIndex;
		
		/*
		 * The iterator in essense is an ArrayList that contains all the NameInfoPair in
		 * the parent class.
		 * 
		 * We can get access to the fields inside our parent class directly.
		 */
		
		// constructor
		private MemHashIterator() {
			ArrayList<NameInfoPair> toIterate = new ArrayList<NameInfoPair>(0);
			if (numBuckets != 0) {
				for (int i = 0; i < numBuckets; i++) {
					LinkedList<NameInfoPair> curLL = GHashTable.get(i);
					for (int j = 0; j < curLL.size(); j++) {
						toIterate.add(curLL.get(j));
					}
				}
			}
			this.iterList = toIterate;
			this.curIndex = 0;
		}
		
		@Override
		/*
		 * Here we return the current member and move the pointer to the next member.
		 * 
		 * If we use remove() then the time complexity is O(n) which is not very effective.
		 * Instead we use another method.
		 */
		public NameInfoPair next() {
			NameInfoPair output = this.iterList.get(this.curIndex);
			this.curIndex += 1;
			return output;
		}
		
		@Override
		/*
		 * Check this.curIndex and the size of this.iterList.
		 */
		public boolean hasNext() {
			if (this.curIndex >= this.iterList.size()) {
				return false;
			}
			return true;
		}
	}

	
}
