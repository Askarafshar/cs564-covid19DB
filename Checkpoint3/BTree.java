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
    //TODO: review the code again. -ASA
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
    
    BTree insert(Student student) {
        /**
         * TODO:
         * Implement this function to insert in the B+Tree.
         * Also, insert in student.csv after inserting in B+Tree.
         */
        return this;
    }

    /**
     * Deletes an entry from the B+ tree given a studentId. Then it attempts to remove
     * the entry from student.csv. 
     * 
     * @param studentId     id of the student to be deleted
     * @return true     if the student was successfully deleted from the B+ tree
     *                      does not guarantee it was removed from student.csv
     *         false    if student does not exist or failed delete
     * @author kwalker26
     */
    boolean delete(long studentId) {
        BTreeNode studentNode;
        int studIndex = 0;
        int numEntries = 0;

        if (root == null) {
            return false;   // empty tree
        }

        // find the node with the student
        studentNode = getNode(root, studentId);
        long[] studKeys = studentNode.getKeys();
        for (int i = 0; i < studKeys.length; i++) {
            if (studKeys[i] == -1) {
                break;
            }
            else if(studentId == studKeys[i]) {
                // we found the student entry to delete
                studIndex = i;
    		}
    		else if(studentId < studKeys[i]) {
                // student entry not found
    			System.out.println("The given studentId was not found");
                return false;
            }
            numEntries++;   // do not want this to increment on empty key
        }

        // delete cases
        // 1 - delete leaves node in valid state
        // 2 - delete leaves node requiring more values, borrow from neighbor
        // 3 - delete leaves node requiring more values, merge with neighbor

        long[] studValues = studentNode.values;
        if (numEntries > t) {
            // case 1
            for (int i = studIndex; i < studKeys.length - 1; i++) {
                studKeys[i] = studKeys[i + 1];
                studValues[i] = studValues[i + 1];
            }
            studKeys[studKeys.length - 1] = 0;
            studValues[studValues.length - 1] = 0;
            // update node
            studentNode.setKeys(studKeys);
            studentNode.values = studValues;
        }
        else if (numEntries == this.t) {
            // cases 2/3: invalid

        }


        // attempt to delete from .csv

        return true;
    }

    /**
     * Traverses the tree (starting at the root) to find the parent of the leaf 
     * node where the given studentId record would exist. 
     * 
     * @param studentId    student ID of the record you want to parent node of
     * @return      the parent node for the given ID
     * @author kwalker26
     */
    private BTreeNode findParent(long studentId) {
        BTreeNode result = root;
        
        // go until we are at parent of a leaf
        while (!result.getChild()[0].leaf) {
            result = result.getChild()[childrenSearch(studentId, result.getKeys())];
        }

        return result;
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
