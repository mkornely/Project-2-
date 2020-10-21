// --== CS400 File Header Information ==--
// Name: Michael Kornely
// Email: mkornely@wisc.edu
// Team: BA
// TA: Bri
// Lecturer: Florian
// Notes to Grader: J unit was tough to implement on eclipse


import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing the nodes within a
 * binary search tree. You can use this class' insert method to build a binary search tree, and its
 * toString method to display the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always be maintained.
   */
  protected static class Node<T> {
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;
    public boolean isBlack;

    public Node(T data) {
      this.data = data;
      this.isBlack = false;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * 
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { // display subtree in order traversal
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += ", ";
      }
      return output + "]";
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   */
  public void insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if (root == null) {
      root = newNode;
      root.isBlack = true;
    } // add first node to an empty tree
    else
      insertHelper(newNode, root); // recursively insert into subtree
  }

  /**
   * Makes sure properties are followed after each insertion into the tree.
   * 
   * @param newNode to be added into this red black tree
   * 
   */
  private void enforceRBTreePropertiesAfterinsert(Node<T> newNode) {

    // if the node is the root of the tree
    if (newNode.parent == null) {
      newNode.isBlack = true;
      return;
    }
    // if the root of the tree or subtree is black
    if (newNode.parent.isBlack)
      return;

    // sets local variables
    Node<T> parent = newNode.parent;
    Node<T> grandparent = getGrandparent(newNode);
    Node<T> uncle = getUncle(newNode);


    // case 2a which requires a rotation and a recolor.

    if (uncle.isBlack || uncle == null) {
      rotate(parent, grandparent);
      parent.isBlack = true;
      grandparent.isBlack = false;
      newNode.isBlack = false;
      return;
    }
    // case 2b which requires a recolor
    if (!uncle.isBlack) {
      parent.isBlack = true;
      uncle.isBlack = true;
      if (grandparent == root)
        grandparent.isBlack = true;
      else
        grandparent.isBlack = false;
      return;
    }


  }

  /**
   * Gets the uncle for the tree to be fixed.
   * 
   * @param newNode to be added into this red black tree
   * 
   */

  private Node<T> getUncle(Node<T> newNode) {
    Node<T> grandparent = null;
    if (newNode.parent != null)
      grandparent = newNode.parent.parent;
    if (grandparent == null)
      return null;
    if (grandparent.leftChild == newNode.parent)
      return grandparent.rightChild;
    else
      return grandparent.leftChild;


  }

  /**
   * Gets the grandparent for the tree to be fixed.
   * 
   * @param newNode to be added into this red black tree
   * 
   */

  private Node<T> getGrandparent(Node<T> newNode) {

    if (newNode.parent == null)
      return null;

    return newNode.parent.parent;
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
   */
  private void insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.data.compareTo(subtree.data);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      throw new IllegalArgumentException("This RedBlackTree already contains that value.");

    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }
    enforceRBTreePropertiesAfterinsert(newNode);
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   */
  @Override
  public String toString() {
    return root.toString();
  }


  /**
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {


    // right rotation
    if (parent.leftChild != null) {
      if (parent.leftChild.data.equals(child.data)) {

        // assigns the subtrees
        parent.leftChild = child.rightChild;
        if (child.rightChild != null)
          child.rightChild.parent = parent;
        // swithces the parents around
        child.parent = parent.parent;
        // assigns root
        if (parent.parent == null)
          root = child;

        // pointer of the grandparent to the child
        else {
          if (parent.data.equals(parent.parent.leftChild.data))
            parent.parent.leftChild = child;
          else
            parent.parent.rightChild = child;

        }
        // child points to parent and vise versa
        child.rightChild = parent;
        parent.parent = child;
      }

    }

    // left rotation same as right implementation with some changes

    if (parent.rightChild != null) {
      if (parent.rightChild.data.equals(child.data)) {

        parent.rightChild = child.leftChild;
        if (child.leftChild != null)
          child.leftChild.parent = parent;


        child.parent = parent.parent;
        if (parent.parent == null)
          root = child;

        else {
          if (parent.data.equals(parent.parent.leftChild.data))
            parent.parent.leftChild = child;
          else
            parent.parent.rightChild = child;
        }

        child.leftChild = parent;
        parent.parent = child;

      }
    }

    else
      throw new IllegalArgumentException();

  }


}
