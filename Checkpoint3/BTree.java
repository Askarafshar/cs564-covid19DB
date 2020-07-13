/**
 * Do NOT modify.
 * This is the class with the main function
 */

import java.util.ArrayList;
import java.util.List;

/**
 * B+Tree Structure
 * Key - StudentId
 * Leaf Node should contain [ key,recordId ]
 */
class BTree {

    /**
     * Pointer to the root node.
     */
    private BTreeNode root;
    /**
     * Number of key-value pairs allowed in the tree/the minimum degree of B+Tree
     **/
    private int t;

    BTree(int t) {
        this.root = null;
        this.t = t;
    }
    /////TODO: review the code again. -ASA
     /**
     * Search function in the B+Tree.
     * Return recordID for the given StudentID.
     * Otherwise, print out a message that the given studentId has not been found in the table and return -1.
     */
    long search(long studentId) {
        long result = -1;
    	BTreeNode temp;
    	temp = getNode(this.root, studentId);
    	long[] keys = temp.getKeys();
    	for(int i=0;i<keys.length;i++) {
    		if(studentId == keys[i]) {
    			result = temp.values[i];
    		}
    		if(studentId < keys[i]) {
    			System.out.println("the given studentId has not been found");
    			break;
    		}
    	}
    	return result;
    }
    
    //to traverse and get node
    public BTreeNode getNode(BTreeNode node, long key) {
    	while(!(node.children==null)) {
    		node = node.getChild()[childrenSearch(key, node.getKeys())];
    	}
    	return node;
    }
    //search to consider the internal nodes too.
    public int childrenSearch(long key, long[] keys) {
    	int start = 0;
    	int length = keys.length -1;
    	int mid;
    	int index = -1;
    	if(key < keys[start]) {
    		return 0;
    	}
    	if(key >= keys[length]) {
    		return keys.length;
    	}
    	while(start <= length) {
    		mid = (start + length) / 2;
    		if(key < keys[mid] && key >= keys[mid -1]) {
    			index = mid;
    			break;
    		}
    		else if(key >= keys[mid]) {
    			start = mid + 1;
    		}else {
    			length = mid - 1;
    		}
    	}
    	return index;
    }
    ///////////////ASA//////////////
    BTree insert(Student student) {
        /**
         * TODO:
         * Implement this function to insert in the B+Tree.
         * Also, insert in student.csv after inserting in B+Tree.
         */
        return this;
    }

    boolean delete(long studentId) {
        /**
         * TODO:
         * Implement this function to delete in the B+Tree.
         * Also, delete in student.csv after deleting in B+Tree, if it exists.
         * Return true if the student is deleted successfully otherwise, return false.
         */
        return true;
    }

    List<Long> print() {

        List<Long> listOfRecordID = new ArrayList<>();

        /**
         * TODO:
         * Implement this function to print the B+Tree.
         * Return a list of recordIDs from left to right of leaf nodes.
         *
         */
        return listOfRecordID;
    }
}
