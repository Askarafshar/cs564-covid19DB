
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
        while (!(node.getChild() == null)) {
            node = node.children[childrenSearch(key, node.getKeys())];
        }
        return node;
    }

    // search to consider the internal nodes too.
    public int childrenSearch(long key, long[] keys) {
        int start = 0;
        int length = keys.length - 1;
        int mid;
        int index = -1;
        if (key < keys[start]) {
            return 0;
        }
        if (key >= keys[length]) {
            return keys.length;
        }
        while (start <= length) {
            mid = (start + length) / 2;
            if (key < keys[mid] && key >= keys[mid - 1]) {
                index = mid;
                break;
            } else if (key >= keys[mid]) {
                start = mid + 1;
            } else {
                length = mid - 1;
            }
        }
        return index;
    }
    /*
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
        if (!capacity(candidate)) {
            for (int i = candidate.values.length - 2; i >= index; i--) {
                candidate.keys[i + 1] = candidate.keys[i];
                candidate.values[i + 1] = candidate.values[i];
            }
            candidate.keys[index] = studentId;
            candidate.values[index] = recordId;
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
        if (node.keys.length == node.values.length) {
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
        BTreeNode studentNode;
        int studIndex = 0;

        if (root == null) {
            return false; // empty tree
        }

        // find the node with the student
        studentNode = searchLeafNode(root, studentId);
        long[] studKeys = studentNode.getKeys();
        for (int i = 0; i < studKeys.length; i++) {
            if (studentId == studKeys[i]) {
                // we found the student entry to delete
                studIndex = i;
                break;
            } else if (studentId < studKeys[i]) {
                // student entry not found
                System.out.println("The given studentId was not found");
                return false;
            }
        }

        // Perform delete and update the node
        removeKeyValue(studentNode, studIndex);

        // delete cases
        // 1 - delete leaves node in valid state
        // 2 - delete leaves node requiring more values, borrow from neighbor
        // 3 - delete leaves node requiring more values, merge with neighbor

        if (studentNode.n < t && root != studentNode) {
            // cases 2/3: invalid
            BTreeNode parent = findParent(studentId);
            int studNodeIndex = -1;
            boolean borrow = false;
            boolean merge = false;

            // try case 2 - attempt borrowing
            studNodeIndex = childrenSearch(studentId, parent.getKeys());
            if (studNodeIndex < parent.n) {
                // sibling to right exists so try to borrow
                BTreeNode sibling = parent.getChild()[studNodeIndex + 1];
                borrow = borrowHelper(studentNode, parent, sibling, studNodeIndex, true);
            }
            if (!borrow && studNodeIndex != 0) {
                // sibling to left exists so try to borrow
                BTreeNode sibling = parent.getChild()[studNodeIndex - 1];
                borrow = borrowHelper(studentNode, parent, sibling, studNodeIndex, true);
            }

            // case 3 - must merge with sibling
            if (!borrow) {
                // TODO: implement functionality to merge upon delete
            }

            if (!borrow && !merge) {
                System.out.println("Failed to merge or borrow upon delete.");
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

    private void removeKeyValue(BTreeNode node, int index) {
        long[] keys = node.getKeys();
        long[] values = node.values;
        for (int i = index; i < keys.length - 1; i++) {
            keys[i] = keys[i + 1];
            values[i] = values[i + 1];
        }
        keys[keys.length - 1] = 0;
        values[values.length - 1] = 0;
        node.setKeys(keys);
        node.values = values;
        node.n = node.n - 1;
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
            if (right.n > t) {
                // sibling can lend a value
                long tempKey, tempVal;
                long[] currKeys = left.getKeys();
                long[] currVals = left.values;
                long[] parKeys = parent.getKeys();
                // remove from sibling
                tempKey = right.getKeys()[0];
                tempVal = right.values[0];
                removeKeyValue(right, 0);
                // add to current
                currKeys[left.n] = tempKey;
                currVals[left.n] = tempVal;
                left.setKeys(currKeys);
                left.values = currVals;
                left.n = left.n + 1;
                // update parent
                parKeys[borrowerIndex] = tempKey;
                parent.setKeys(parKeys);
                return true;
            }
        } 
        else {
            // sibling = left
            if (left.n > t) {
                // sibling can lend a value
                long tempKey, tempVal;
                long[] currKeys = right.getKeys();
                long[] currVals = right.values;
                long[] parKeys = parent.getKeys();
                // remove from sibling
                tempKey = left.getKeys()[left.n - 1];
                tempVal = left.values[left.n - 1];
                removeKeyValue(left, left.n - 1);
                // add to current
                for (int i = currKeys.length - 1; i > 0; i--) {
                    currKeys[i] = currKeys[i - 1];
                    currVals[i] = currVals[i - 1];
                }
                currKeys[0] = tempKey;
                currVals[0] = tempVal;
                right.setKeys(currKeys);
                right.values = currVals;
                right.n = right.n + 1;
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
        while (!result.getChild()[0].leaf) {
            result = result.getChild()[childrenSearch(studentId, result.getKeys())];
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
        	for(int i=0;i<leftLeafNode.n;i++) {
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
