
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

    /* 
     * to traverse and get node
     * @param a node and a key
     * @return a node contains the key
     * @author safipourafsh
     */
    public BTreeNode getNode(BTreeNode node, long key) {
        while (!(node.children == null)) {
            node = node.children[childrenSearch(key, node.keys)];
        }
        return node;
    }

   
     /* 
     * search to consider the internal nodes index.
     * @param an array and a key
     * @return a index 
     * @author safipourafsh
     */
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
            int mid = (candidate.keys.length)/2;
            newNode.keys[0] = candidate.keys[mid];
            if(index >= mid) {index = childrenSearch(studentId, newNode.keys);}
            else {index = childrenSearch(studentId, candidate.keys);}
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
        
        if (root != null) {
        	System.out.println("Tree is empty");
            return false; // empty tree
        }
        else {
        	if (!deleteHelper(null, root, studentId, deletedNode)) {
            	System.out.println("Failed to delete");
            	return false;
            }
        }
        
        

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

    // if recursively passing deletedChild doesn't work, try making it a return value and use that at root to determine reaction
    private boolean deleteHelper(BTreeNode parent, BTreeNode curr, long key, BTreeNode deletedChild) {
    	boolean result = false;
    	
    	if (curr.leaf) {
    		if (nodeSize(curr.keys) > t) {
    			// entries to spare so remove
    			result = removeKeyValue(curr, key);
    			deletedChild = null;
    			return result;
    		}
    		// TODO KW: currently only tries the right sibling
    		// idea to implement both, try to force code to execute twice, once with sib index as 1 less than curr, other as 1 more than
    		else {
    			BTreeNode sibling = curr.next;
    			if (sibling == null) {
    				// consider case w/ null sibling and at root as child[0]; becomes important if we cannot find left sibling
    				System.out.println("TODO KW: use of right sibling for merge/redistribute not implemented");
    				return false;
    			}
    			// get index for navigating parent
    			int sibNodeIndex = -1;
    			for (int i = 0; i < nodeSize(parent.keys); i++) {
    				if (parent.keys[i] > sibling.keys[0]) {
    					// parent children array index is always equal to the index of the first key greater than smallest child key
    					// i(child) = i_min(parent.key > child.key[0])
    					sibNodeIndex = i;
    					break;
    				}
    			}
    			if (sibNodeIndex == -1) {
    				// sibling is last child of this parent
    				sibNodeIndex = nodeSize(parent.keys);
    			}
    			if (nodeSize(sibling.keys) > t) {
    				// has extra entries so remove and redistribute
    				if (!removeKeyValue(curr, key)) {
    					return false;
    				}
    				redistribute(curr, sibling);
    				// update parent with new min key in right
    				parent.keys[sibNodeIndex - 1] = sibling.keys[0];
    				deletedChild = null;
        			return true;
    			}
    			else {
    				// no extra entries, merge
    				// set deleted to rhs node TODO: currently can only be sibling
    				deletedChild = sibling;
    				merge(curr, sibling);	// empty right node into left node
    				// update pointer for leaf and child array for parent
    				curr.next = null;
    				removeChild(parent, sibNodeIndex);
    				return true;
    			}
    		}
    	}
    	// not a leaf
    	else {
    		// find subtree
    		int childIndex = childrenSearch(key, curr.keys);
    		deleteHelper(curr, curr.children[childIndex], key, deletedChild);	// recurse
    		if (deletedChild == null) {
    			return true;
    		}
    		// TODO KW: currently only tries the right sibling
    		else {
    			// remove deleted child, handled when the child is set as deletedChild
    			// test not underflow (enough entries to be a valid node)
    			if (nodeSize(curr.keys) >= t) {
    				deletedChild = null;
    				return true;
    			}
    			// underflow
    			else {
    				BTreeNode sibling = curr.next;
        			if (sibling == null) {
        				// consider case w/ null sibling and at root as child[0]; becomes important if we cannot find left sibling
        				System.out.println("TODO KW: use of right sibling for merge/redistribute not implemented");
        				return false;
        			}
        			// get index for navigating parent
        			int sibNodeIndex = -1;
        			for (int i = 0; i < nodeSize(parent.keys); i++) {
        				if (parent.keys[i] > sibling.keys[0]) {
        					// parent children array index is always equal to the index of the first key greater than smallest child key
        					// i(child) = i_min(parent.key > child.key[0])
        					sibNodeIndex = i;
        					break;
        				}
        			}
        			if (sibNodeIndex == -1) {
        				// sibling is last child of this parent
        				sibNodeIndex = nodeSize(parent.keys);
        			}
        			if (nodeSize(sibling.keys) > t) {
        				// has extra entries so redistribute through parent (need to preserve children)
        				redistributeInternal(curr, sibling);
        				// update parent with new min key in right
        				parent.keys[sibNodeIndex - 1] = sibling.keys[0];
        				deletedChild = null;
            			return true;
        			}
    				// merge
        			else {
        				// set deleted to rhs node TODO: currently can only be sibling
        				deletedChild = sibling;
        				// in parent pull key between children down into top of left node
        				// left node is currently assumed to be curr
        				curr.keys[nodeSize(curr.keys)] = parent.keys[sibNodeIndex - 1];	// -1 assumes left is always curr
        				mergeInternal(curr, sibling); // empty right node into left node
    					// update pointer for leaf and child array for parent
        				curr.next = null;
        				removeChild(parent, sibNodeIndex);
        				return true;
        			}
    			}
    				
    		}
    	}
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
     * Removes the child at the given index from the internal node.
     * 
     * @param internalNode
     * @param index
     */
    private void removeChild(BTreeNode internalNode, int index) {
    	if (internalNode.leaf) {
    		System.out.print("Cannot remove child from leaf");
    		return;
    	}
    	
    	int numKeys = nodeSize(internalNode.keys);
    	if (index > numKeys - 1 || index < -1) {
    		System.out.println("Invalid index to remove child.");
    		return;
    	}
    	// shift all values down to fill where it was
    	for (int i = index; i < numKeys - 1; i ++) {
    		internalNode.keys[i] = internalNode.keys[i + 1];
    		internalNode.children[i] = internalNode.children[i + 1];
    	}
    	if (numKeys == (2 * t - 1)) {
    		// node was full of keys
    		if (index == numKeys - 1) {
    			// deleting 2nd to last child
    			internalNode.keys[numKeys - 1] = 0;
    			internalNode.children[numKeys] = internalNode.children[internalNode.children.length - 1];
    		}
    		// must always null last child upon deleting one of last 2 entries
    		internalNode.children[internalNode.children.length - 1] = null;
    	}
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
    	long[] rghtVals = right.values;
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
    	// update nodes
    	left.keys = lftKeys;
    	left.values = lftVals;
    	right.keys = rghtKeys;
    	right.values = rghtVals;
    }
    
    /** 
     * Redistributes children between 2 leaf nodes. Does not update the parent node. 
     * @param left
     * @param right
     */
    private void redistributeInternal(BTreeNode left, BTreeNode right) {
    	if (left.leaf || right.leaf) {
    		System.out.println("Cannot redistribute children between leaf nodes.");
    	}
    	long[] lftKeys = left.keys;
    	BTreeNode[] lftChildren = left.children;
    	long[] rghtKeys = right.keys;
    	BTreeNode[] rghtChildren = right.children;
    	int lftSize = nodeSize(lftKeys);
    	int rghtSize = nodeSize(rghtKeys);
    	int totEntries = lftSize + rghtSize;
    	
    	if (lftSize < totEntries / 2) {
    		// add entries to left node
    		while (lftSize < totEntries / 2) {
    			lftKeys[lftSize] = rghtKeys[0]; 
    			lftChildren[lftSize + 1] = rghtChildren[0];
    			// shift right values down
    			for (int i = 0; i < rghtSize - 1; i++) {
    				rghtKeys[i] = rghtKeys[i + 1];
    				rghtChildren[i] = rghtChildren[i + 1];
    			}
    			rghtKeys[rghtSize - 1] = 0;
    			rghtChildren[rghtSize - 1] = null;
    			rghtChildren[rghtSize] = null;	// clear last pointer
    			// update sizes
    			lftSize = nodeSize(lftKeys);
    	    	rghtSize = nodeSize(rghtKeys);
    		}
    	}
    	else {
    		// add entries to right node
    		while (rghtSize < totEntries / 2) {
    			// shift right up
    			rghtChildren[rghtSize + 1] = rghtChildren[rghtSize];
    			for (int i = rghtSize; i > 0; i--) {
    				rghtKeys[i] = rghtKeys[i - 1];
    				rghtChildren[i] = rghtChildren[i - 1];
    			}
    			// add highest from left
    			rghtKeys[0] = lftKeys[lftSize - 1];
    			rghtChildren[0] = lftChildren[lftSize];
    			// remove highest child from left
    			lftKeys[lftSize - 1] = 0;
    			lftChildren[lftSize] = null;
    			// update sizes
    			lftSize = nodeSize(lftKeys);
    	    	rghtSize = nodeSize(rghtKeys);
    		}
    	}
    	// update nodes
    	left.keys = lftKeys;
    	left.children = lftChildren;
    	right.keys = rghtKeys;
    	right.children = rghtChildren;
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
     * Merges two internal nodes. 
     * ASSUMES that we always mergeInto the leftmost node (ie. smaller values)
     * @param mergeInto
     * @param emptyMe
     */
    private void mergeInternal(BTreeNode mergeInto, BTreeNode emptyMe) {
    	if (mergeInto.keys[0] < emptyMe.keys[0]) {
    		// merging into node with smaller keys
    		int baseIndex = nodeSize(mergeInto.keys);
    		for (int i = 0; i < nodeSize(emptyMe.keys); i++) {
    			mergeInto.keys[i + baseIndex] = emptyMe.keys[i];
    			mergeInto.children[i + baseIndex + 1] = emptyMe.children[i];
    		}
    	}
    	else {
    		System.out.println("Violating merge internal assumption of always merge into LHS");
    		// merging into node with larger keys
//    		int baseIndex = nodeSize(emptyMe.keys);
//    		for (int i = baseIndex - 1; i >= 0; i--) {
//    			// push larger keys up
//    			for (int j = nodeSize(mergeInto.keys); j > 0; j--) {
//    				mergeInto.keys[j] = mergeInto.keys[j - 1];
//    				mergeInto.children[j + 1] = mergeInto.children[j];
//    			}
//    			// add largest key from emptying node
//    			mergeInto.keys[0] = emptyMe.keys[i];
//    			mergeInto.children[1] = emptyMe.children[i + 1];
//    		}
//    		// get the final child pointer from the smaller node
//    		mergeInto.children[0] = emptyMe.children[0];
    	}
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
