
/**
 * Do NOT modify.
 * This is the class with the main function
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * B+Tree Structure Key - StudentId Leaf Node should contain [ key,recordId ]
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

    // TODO: review the code again. -ASA
    /**
     * Search function in the B+Tree. Return recordID for the given StudentID.
     * Otherwise, print out a message that the given studentId has not been found in
     * the table and return -1.
     *
     * @param studentId
     * @return recordID
     *
     * @author safipourafsh
     */
    long search(long studentId) {
        // return -1 if root is empty
        if (this.root == null) {
            return -1;
        }

        // Search for the leaf node which contains the key
        BTreeNode leaf = searchLeafNode(this.root, studentId);
        long result = -1;
        // find the index and return
        // Look for value in the leaf
        for (int i = 0; i < leaf.keys.length; i++) {
            if (studentId == leaf.keys[i]) {
                result = leaf.values[i];
            }
        }
        return result;
    }

    /**
     * returns the leaf node which might contain key
     *
     * @param key
     * @return node
     *
     * @author safipourafsh
     */
    private BTreeNode searchLeafNode(BTreeNode node, long studentId) {
        // If the node is a leaf, return
        if (node.leaf) {
            return node;
        }
        // If the key is smaller than the first key in the node, follow its first child
        // to continue
        if (studentId < node.keys[0]) {
            return searchLeafNode(node.children[0], studentId);
            // If the key is bigger than the last key in the node, follow its last child to
            // continue
        } else if (studentId > node.keys[node.keys.length - 1]) {
            return searchLeafNode(node.children[node.keys.length - 1], studentId);
        } else {
            // Use binary search to find the child to follow
            int left = 0, right = node.keys.length - 1, mid;
            while (left <= right) {
                mid = (left + right) / 2;
                if (node.keys[mid] == studentId) {
                    return searchLeafNode(node.children[mid + 1], studentId);
                } else if (node.keys[mid] < studentId) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return searchLeafNode(node.children[left], studentId);
        }
    }

    // to traverse and get node
    public BTreeNode getNode(BTreeNode node, long key) {
        while (!(node.children == null)) {
            node = node.children[childrenSearch(key, node.keys)];
        }
        return node;
    }

    // search to consider the internal nodes too.
    public int childrenSearch(long key, long[] keys) {
        int left = 0;
        int right = keys.length - 1;
        int mid;
        int index = -1;
        if (key < keys[left]) {
            return 0;
        }
        if (key >= keys[right]) {
            return keys.length;
        }
        while (left <= right) {
            mid = (left + right) / 2;
            if (key == keys[mid]) {
                index = mid + 1;
                break;
            } else if (key > keys[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }
    
    /** 
    *return the size of array not considering zeros
    *@params array of keys or values
    *@return size of array (# of non-zero elements)
    *@author safipourafsh
    */        
    int nodeSize(long[] keys) {
        int size = 0;
        for (int i=0; i<keys.length; i++) {
          if (keys[i] != 0)
            size++;
        }
        return size;
      }

    BTree insert(Student student) {
        /**
         * TODO: Implement this function to insert in the B+Tree. Also, insert in
         * student.csv after inserting in B+Tree.
         */
        if (root == null) {
        	root = new BTreeNode(t, true);
            root.keys[0] = student.studentId;
            root.values[0] = student.recordId;
        }
        return insertHelper(root, student);
    }

    BTree insertHelper(BTreeNode node, Student student) {
        long studentId = student.studentId;
        long recordId = student.recordId;
        BTreeNode candidate = searchLeafNode(node, studentId);
        int index = childrenSearch(studentId, candidate.keys);
        // if there is room for the key
        if(nodeSize(candidate.keys) != candidate.keys.length) {
        	long[] canKeys = candidate.keys;
        	for (int i = nodeSize(canKeys) - 1; i >= 0; i--) {
        		if (canKeys[i] > studentId) {
        			// move this key index up one
        			canKeys[i + 1] = canKeys[i];
        			candidate.values[i + 1] = candidate.values[i];
        		}
        		else if (canKeys[i] < studentId) {
        			// insert our entry at one index higher
        			canKeys[i + 1] = studentId;
        			candidate.values[i + 1] = recordId;
        			break;
        		}
        	}
        	candidate.setKeys(canKeys); // update node
        }
        // if the node if full we need split the node
        else {
            if (candidate == this.root) {
                BTreeNode newRoot = new BTreeNode(t, true);
                newRoot.children[0] = candidate;
            }
            BTreeNode newNode = new BTreeNode(t, true);
            newNode.keys[0] = studentId;
            for (int i = index; i < candidate.keys.length; i++) {
                newNode.keys[i - index + 1] = candidate.keys[i];
                candidate.keys[i] = 0;
            }
            newNode.next = candidate.next;
            candidate.next = newNode;

            BTreeNode parent = findParent(studentId);
            insertHelper(parent, student);
        }

        try {
            FileWriter writer = new FileWriter("./student.csv");
            // Writing data to the csv file
            writer.append(String.join(",", student.toString()));// it needs to get fixed
            writer.append("\n");
            // Flushing data from writer to file
            writer.flush();
            writer.close();
            System.out.println("Data entered");
        }
        catch (IOException e) {
            System.out.println("Failed to write to student.csv.");
            e.printStackTrace();
        }

        return this;
    }

    /**
     * returns true if the node is full and false otherwise
     */
    public boolean capacity(BTreeNode node) {
        if (nodeSize(node.keys) == node.values.length) {
            return true;
        }
        return false;
    }

    /**
     * Deletes an entry from the B+ tree given a studentId. Then it attempts to
     * remove the entry from student.csv.
     * 
     * @param studentId id of the student to be deleted
     * @return true if the student was successfully deleted from the B+ tree does
     *         not guarantee it was removed from student.csv false if student does
     *         not exist or failed delete
     * @author kwalker26
     */
    boolean delete(long studentId) {
        BTreeNode deletedNode = null;

        if (root == null) {
            return false; // empty tree
        }
        else {
        	 deleteHelper(null, root, studentId, deletedNode);
        }

        
        // rewrite this to follow book pseudocode
        /*
        // find the node with the student
//        studentNode = searchLeafNode(root, studentId);
//        long[] studKeys = studentNode.getKeys();
//        int numStudKeys = nodeSize(studKeys);
//        for (int i = 0; i < numStudKeys; i++) {
//            if (studentId == studKeys[i]) {
//                // we found the student entry to delete
//                studIndex = i;
//                break;
//            } else if (studentId < studKeys[i]) {
//                // student entry not found
//                System.out.println("The given studentId was not found");
//                return false;
//            }
//        }
//
//        // Perform delete and update the node
//        removeKeyValue(studentNode, studIndex);
//
//        // delete cases
//        // 1 - delete leaves node in valid state
//        // 2 - delete leaves node requiring more values, borrow from neighbor
//        // 3 - delete leaves node requiring more values, merge with neighbor
//
//        if (numStudKeys < t && root != studentNode) {
//            // cases 2/3: invalid
//            BTreeNode parent = findParent(studentId);
//            int numParentKeys = nodeSize(parent.getKeys());
//            int studNodeIndex = -1;
//            boolean borrow = false;
//            boolean merge = false;
//
//            // try case 2 - attempt borrowing
//            studNodeIndex = childrenSearch(studentId, parent.getKeys());
//            if (studNodeIndex < numParentKeys) {
//                // sibling to right exists so try to borrow
//                BTreeNode sibling = parent.getChild()[studNodeIndex + 1];
//                borrow = borrowHelper(studentNode, parent, sibling, studNodeIndex, true);
//            }
//            if (!borrow && studNodeIndex != 0) {
//                // sibling to left exists so try to borrow
//                BTreeNode sibling = parent.getChild()[studNodeIndex - 1];
//                borrow = borrowHelper(studentNode, parent, sibling, studNodeIndex, true);
//            }
//
//            // case 3 - must merge with sibling
//            if (!borrow) {
//                // TODO: implement functionality to merge upon delete
//            }
//
//            if (!borrow && !merge) {
//                System.out.println("Failed to merge or borrow upon delete.");
//                return false;
//            }
//        }
*/
        // attempt to delete from .csv
        try {
            BufferedReader buffer = new BufferedReader(new FileReader("./student.csv"));
            List<String> validEntries = new ArrayList<String>();
            String currLine, currID;
            while ((currLine = buffer.readLine()) != null) {
                if (currLine.length() != 0) {
                    currID = currLine.split(",")[0];
                    if (studentId != Long.parseLong(currID)) {
                        // only add if not the student that was deleted
                        validEntries.add(currLine);
                    }
                }
            }
            buffer.close(); // close once we are done reading
            // rewrite all entries that were added to the list
            FileWriter writer = new FileWriter("./student.csv", false);
            Iterator<String> iter = validEntries.iterator();
            while (iter.hasNext()) {
                writer.write(iter.next() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Failed to find student.csv for update upon delete.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error while updating student.csv upon delete.");
            e.printStackTrace();
        }

        return true;
    }

    private boolean deleteHelper(BTreeNode parent, BTreeNode curr, long key, BTreeNode deletedChild) {
    	boolean result = false;
    	
    	if (curr.leaf) {
    		if (nodeSize(curr.keys) > t) {
    			// entries to spare so remove
    			result = removeKeyValue(curr, key);
    			deletedChild = null;
    			return result;
    		}
    		// leaf is underflowing
    		// TODO KW: currently only tries the right sibling
    		else {
    			BTreeNode sibling = curr.next;
    			if (sibling == null) {
    				System.out.println("use of right sibling for merge/redistribute not implemented");
    				return false;
    			}
    			int sibIndex = 0;
    			for (int i = 0; i < nodeSize(parent.keys); i++) {
    				if (parent.keys[i] > sibling.keys[0]) {
    					// this key
    					sibIndex = i;
    				}
    			}
    			if (sibIndex == 0) {
    				// sibling is last child of this parent
    				sibIndex = nodeSize(parent.keys);
    			}
    			if (nodeSize(sibling.keys) > t) {
    				// has extra entries so remove and redistribute
    				if (!removeKeyValue(curr, key)) {
    					return false;
    				}
    				redistribute(curr, sibling);
    				// update parent with new min key in right
    				parent.keys[sibIndex - 1] = sibling.keys[0];
    				deletedChild = null;
        			return true;
    			}
    			else {
    				// no extra entries, merge
    				// set deleted to rhs node TODO: currently can only be sibling
    				deletedChild = sibling;
    				merge(curr, sibling);
    				// update pointer for leaf
    				curr.next = null;
    			}
    		}
    	}
    	else {
    		// find subtree
    		int childIndex = childrenSearch(key, curr.keys);
    		deleteHelper(curr, curr.children[childIndex], key, deletedChild);
    		if (deletedChild == null) {
    			return true;
    		}
    		else {
    			// remove deleted child
    			// if not underflow
    				// set deleted to null and return
    			// else
    				// get sibling
    				// if sibling can share
    					// redistribute through parent
    					// set deleted to null and return
    				// else
    					// merge
    					// set deleted to rhs node (call this M)
    					// pull splitting key from parent down into left node
    					// move all from M to the lhs node
    					// discard M and return
    		}
    	}
    	return false;
    }

    /**
     * Deletes 	key value pair from a node. 
     * @param node
     * @param key
     * @return	returns false on failure to find key value
     */
    private boolean removeKeyValue(BTreeNode node, long key) {
        long[] keys = node.keys;
        long[] values = node.values;
        int keyIndex = -1;
        int numEntries = nodeSize(keys);
        for (int i = 0; i < numEntries; i++) {
        	if (keys[i] == key) {
        		keyIndex = i;
        		break;
        	}
        }
        
        if (keyIndex == -1) {
        	// key not found
        	System.out.println("Could not find key in leaf node.");
        	return false;
        }
        for (int i = keyIndex; i < numEntries - 1; i++) {
        	keys[i] = keys[i + 1];
            values[i] = values[i + 1];
        }
        keys[numEntries - 1] = 0;
        values[numEntries - 1] = 0;
        node.keys = keys;
        node.values = values;
        
        return true;
    }

    /**
     * Redistributes key-value pairs between 2 leaf nodes
     * 
     * @param left
     * @param right
     */
    private void redistribute(BTreeNode left, BTreeNode right) {
    	long[] lftKeys = left.keys;
    	long[] lftVals = left.values;
    	long[] rghtKeys = right.keys;
    	long[] rghtVals = right.keys;
    	int lftSize = nodeSize(lftKeys);
    	int rghtSize = nodeSize(rghtKeys);
    	int totEntries = lftSize + rghtSize;
    	
    	if (lftSize < totEntries / 2) {
    		// add entries to left node
    		while (lftSize < totEntries / 2) {
    			lftKeys[lftSize] = rghtKeys[0]; 
    			lftVals[lftSize] = rghtVals[0];
    			// shift right values down
    			for (int i = 0; i < rghtSize - 1; i++) {
    				rghtKeys[i] = rghtKeys[i + 1];
    				rghtVals[i] = rghtVals[i + 1];
    			}
    			rghtKeys[rghtSize - 1] = 0;
    			rghtVals[rghtSize - 1] = 0;
    			// update sizes
    			lftSize = nodeSize(lftKeys);
    	    	rghtSize = nodeSize(rghtKeys);
    		}
    	}
    	else {
    		// add entries to right node
    		while (rghtSize < totEntries / 2) {
    			// shift right up
    			for (int i = rghtSize; i > 0; i--) {
    				rghtKeys[i] = rghtKeys[i - 1];
    				rghtVals[i] = rghtVals[i - 1];
    			}
    			// add highest from left
    			rghtKeys[0] = lftKeys[lftSize - 1];
    			rghtVals[0] = lftVals[lftSize - 1];
    			// remove highest val from left
    			lftKeys[lftSize - 1] = 0;
    			lftVals[lftSize - 1] = 0;
    			// update sizes
    			lftSize = nodeSize(lftKeys);
    	    	rghtSize = nodeSize(rghtKeys);
    		}
    	}
    	
    }
    
    /**
     * Takes all values from emptyMe and moves them into mergeInto. 
     * @param mergeInto
     * @param emptyMe
     */
    private void merge(BTreeNode mergeInto, BTreeNode emptyMe) {
    	if (mergeInto.keys[0] < emptyMe.keys[0]) {
    		// merging into node with smaller keys
    		int baseIndex = nodeSize(mergeInto.keys);
    		for (int i = 0; i < nodeSize(emptyMe.keys); i++) {
    			mergeInto.keys[i + baseIndex] = emptyMe.keys[i];
    			mergeInto.values[i + baseIndex] = emptyMe.values[i];
    		}
    	}
    	else {
    		// merging into node with larger keys
    		int baseIndex = nodeSize(emptyMe.keys);
    		for (int i = baseIndex - 1; i >= 0; i--) {
    			// push larger keys up
    			for (int j = nodeSize(mergeInto.keys); j > 0; j--) {
    				mergeInto.keys[j] = mergeInto.keys[j - 1];
    				mergeInto.values[j] = mergeInto.values[j - 1];
    			}
    			// add largest key from emptying node
    			mergeInto.keys[0] = emptyMe.keys[i];
    			mergeInto.values[0] = emptyMe.values[i];
    		}
    	}
    }
    
    /**
     * 
     * @param left      node to 'left'
     * @param parent    parent of nodes trying to borrow
     * @param right     node to 'right'
     * @param LfromR    true if left node is borrowing from right node
     * @return          true if the borrow was successful, null otherwise
     */
    private boolean borrowHelper(BTreeNode left, BTreeNode parent, BTreeNode right, int borrowerIndex, boolean LfromR) {
        if (LfromR) {
            // sibling = right
            if (nodeSize(right.keys) > t) {
                // sibling can lend a value
                long tempKey, tempVal;
                long[] currKeys = left.keys;
                long[] currVals = left.values;
                int lftEntries = nodeSize(currKeys);
                long[] parKeys = parent.keys;
                // remove from sibling
                tempKey = right.keys[0];
                tempVal = right.values[0];
                removeKeyValue(right, 0);
                // add to current
                currKeys[lftEntries] = tempKey;
                currVals[lftEntries] = tempVal;
                left.setKeys(currKeys);
                left.values = currVals;
                // update parent
                parKeys[borrowerIndex] = tempKey;
                parent.setKeys(parKeys);
                return true;
            }
        } 
        else {
            // sibling = left
            int lftEntries = nodeSize(left.keys);
            if (lftEntries > t) {
                // sibling can lend a value
                long tempKey, tempVal;
                long[] currKeys = right.keys;
                long[] currVals = right.values;
                long[] parKeys = parent.keys;
                // remove from sibling
                tempKey = left.keys[lftEntries - 1];
                tempVal = left.values[lftEntries - 1];
                removeKeyValue(left, lftEntries - 1);
                // add to current
                for (int i = currKeys.length - 1; i > 0; i--) {
                    currKeys[i] = currKeys[i - 1];
                    currVals[i] = currVals[i - 1];
                }
                currKeys[0] = tempKey;
                currVals[0] = tempVal;
                right.setKeys(currKeys);
                right.values = currVals;
                // update parent
                parKeys[borrowerIndex - 1] = tempKey;
                parent.setKeys(parKeys);
                return true;
            }
        }
        return false;
    }

    /**
     * Traverses the tree (starting at the root) to find the parent of the leaf 
     * node where the given studentId record would exist. 
     * 
     * @param studentId    student ID of the record you want to parent node of
     * @return      the parent node for the given ID, null if root is leaf node
     * @author kwalker26
     */
    private BTreeNode findParent(long studentId) {
        BTreeNode result = root;
        
        if (root.leaf) {
        	return null;
        }
        // go until we are at parent of a leaf
        while (!result.children[0].leaf) {
            result = result.children[childrenSearch(studentId, result.getKeys())];
        }

        return result;
    }

/**
     * 
     * Prints the B+Tree's leaf node values (recordIDs).
     * Return a list of recordIDs from left to right of leaf nodes.
     *@param
     *@return list of recordIDs
     *@author safipourafsh
     */
    List<Long> print() {
        List<Long> listOfRecordID = new ArrayList<>();
        BTreeNode temp = this.root;
        BTreeNode leftLeafNode = getLeftLeafNode(temp);
         
        while(!(leftLeafNode==null)) {
        	for(int i = 0; i < nodeSize(leftLeafNode.keys); i++) {
        		listOfRecordID.add(leftLeafNode.values[i]);
        	}
        	leftLeafNode = leftLeafNode.next;
        }
       return listOfRecordID;
    }
    /*
     * gets the left leaf node, used in print function.
     * @param BTreeNode node
     * @return left leaf node
     * @author safipourafsh
     */
    private BTreeNode getLeftLeafNode(BTreeNode node) {
        //If the node is a leaf, return
        if (node.leaf) {
            return node;
        }
        return getLeftLeafNode(node.children[0]);
    }
}
