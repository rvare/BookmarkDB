package org.bookmarkdb.model;

import org.bookmarkdb.model.Bookmark;

import java.util.LinkedList;

public class AVL_Tree {
	private AVL_Node root;
	
	public LinkedList<Bookmark> queue; // Used to for inorder displaying for the JList class

	public AVL_Tree() {
		queue = new LinkedList<Bookmark>();
		root = null;
	}

	// Getters
	public AVL_Node getRoot() {
		return this.root;
	}

	// Setters
	public void setRoot(AVL_Node root) {
		this.root = root;
	}

	// AVL operations
	public AVL_Node insert(AVL_Node leaf, String key, Bookmark bookmark) {
		if (leaf == null) {
			leaf = new AVL_Node(key, bookmark);
			return leaf;
		}
		else if (key.compareTo(leaf.getKey()) < 0) { // Traverse left
			leaf.setLeftNode(insert(leaf.getLeftNode(), key, bookmark));
		}
		else {
			leaf.setRightNode(insert(leaf.getRightNode(), key, bookmark));
		}

		leaf = rebalance(leaf);

		return leaf;
	}

	public AVL_Node deleteNode(AVL_Node leaf, String key) {
		if (leaf == null) return null;

		if (key.compareTo(leaf.getKey()) < 0) { // Traverse left
			assert !leaf.getKey().equals(key) : "Correct node found but still traversing left.";
			leaf.setLeftNode(deleteNode(leaf.getLeftNode(), key));
		}
		else if (key.compareTo(leaf.getKey()) > 0) { // Traverse right
			assert !leaf.getKey().equals(key) : "Correct node found but still traversing right.";
			leaf.setRightNode(deleteNode(leaf.getRightNode(), key));
		}
		else { // Node found
			if (leaf.getLeftNode() == null && leaf.getRightNode() == null) { // Signle node case
				assert leaf.getKey().equals(key) : "Deleting wrong node.";
				return null;
			}
			else if (leaf.getLeftNode() != null && leaf.getRightNode() == null) { // Left child case
				return leaf.getLeftNode();
			}
			else if (leaf.getLeftNode() == null && leaf.getRightNode() != null) { // Right child case
				return leaf.getRightNode();
			}
			else if (leaf.getLeftNode() != null && leaf.getRightNode() != null) { // Internal node case
				AVL_Node tempNode = leaf.getRightNode();
				while (tempNode.getLeftNode() != null) tempNode = tempNode.getLeftNode();
				leaf.setKey(tempNode.getKey());
				leaf.setRightNode(deleteNode(leaf.getRightNode(), tempNode.getKey()));
			}
			else {
				if (leaf.getLeftNode() != null) {
					return leaf.getLeftNode();
				}
				else {
					return leaf.getRightNode();
				}
			} // End inner if-else
		} // End outer if-else

		return leaf;
	} // End of deleteNode

	public AVL_Node searchBookmark(AVL_Node leaf, String key) {
		AVL_Node foundBookmark;
		
		if (leaf == null) return null;

		if (key.equals(leaf.getKey())) {
			return leaf;
		}
		else if (key.compareTo(leaf.getKey()) < 0) {
			foundBookmark = searchBookmark(leaf.getLeftNode(), key);
		}
		else {
			foundBookmark = searchBookmark(leaf.getRightNode(), key);
		}

		return foundBookmark;
	}

	public AVL_Node rebalance(AVL_Node leaf) {
		leaf.setHeight(Math.max(height(leaf.getLeftNode()), height(leaf.getRightNode())) + 1);
		leaf.setBalance(height(leaf.getLeftNode()) - height(leaf.getRightNode()));

		if (leaf.getLeftNode() != null && leaf.getBalance() >= 2 && leaf.getLeftNode().getBalance() > 0) { // Left heavy, outside
			return rotateRight(leaf);
		}

		if (leaf.getRightNode() != null && leaf.getBalance() <= -2 && leaf.getRightNode().getBalance() < 0) { // Right heavy, outside
			return rotateLeft(leaf);
		}

		if (leaf.getLeftNode() != null && leaf.getBalance() >= 2 && leaf.getLeftNode().getBalance() < 0) { // Left heavy, inside
			leaf.setLeftNode(rotateLeft(leaf.getLeftNode()));
			return rotateRight(leaf);
		}

		if (leaf.getRightNode() != null && leaf.getBalance() <= -2 && leaf.getRightNode().getBalance() > 0) { // Right heavy, inside
			leaf.setRightNode(rotateRight(leaf.getRightNode()));
			return rotateLeft(leaf);
		}

		return leaf;
	}

	private int height(AVL_Node leaf) {
		if (leaf != null) {
			int leftHeight = height(leaf.getLeftNode()); // Get height of left node
			int rightHeight = height(leaf.getRightNode()); // Get height of right node

			// TODO: DONE Make this match the other if statements for consistency
			if (leftHeight > rightHeight) {
				return leftHeight + 1;
			}
			else {
				return rightHeight + 1;
			}
		}

		return -1;
	}

	// TODO: Change these to not be one character variables names
	// Rotation methods
	public AVL_Node rotateLeft(AVL_Node x) {
		AVL_Node y = x.getRightNode();
		AVL_Node z = y.getLeftNode();

		y.setLeftNode(x);
		x.setRightNode(z);

		// Recalculate heights of x and y
		x = rebalance(x);
		y = rebalance(y);

		return y;
	}

	public AVL_Node rotateRight(AVL_Node x) {
		AVL_Node y = x.getLeftNode();
		AVL_Node z = y.getRightNode();

		y.setRightNode(x);
		x.setLeftNode(z);

		// Recalculate heights of x and y
		x = rebalance(x);
		y = rebalance(y);

		return y;
	}

	// Traversal methods
	public void inOrderTraversal(AVL_Node leaf) {
		if (leaf != null) {
			inOrderTraversal(leaf.getLeftNode());
			queue.add(leaf.getBookmark());
			inOrderTraversal(leaf.getRightNode());
		}
	}

	public LinkedList<Bookmark> getQueue() {
		return queue;
	}
} // End AVL_Tree

class AVL_Node {
	private String key;
	private int height; // Height of the node in the tree
	private int balanceFactor; // Balance factor for rotations

	private AVL_Node leftNode;
	private AVL_Node rightNode;

	private Bookmark bookmark;

	public AVL_Node() { } // Default constructor; does nothing

	public AVL_Node(final String data, final Bookmark bookmark) {
		this.key = data;
		this.bookmark = bookmark;
	}

	// Getters
	public String getKey() {
		return this.key;
	}

	public AVL_Node getLeftNode() {
		return this.leftNode;
	}

	public AVL_Node getRightNode() {
		return this.rightNode;
	}

	public int getHeight() {
		return this.height;
	}

	public int getBalance() {
		return this.balanceFactor;
	}
	
	public Bookmark getBookmark() {
		return this.bookmark;
	}

	// Setters
	public void setKey(final String key) {
		this.key = key;
	}

	public void setLeftNode(final AVL_Node newLeft) {
		this.leftNode = newLeft;
	}

	public void setRightNode(final AVL_Node newRight) {
		this.rightNode = newRight;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public void setBalance(final int balance) {
		this.balanceFactor = balance;
	}

	public void setBookmark(final Bookmark bookmark) {
		this.bookmark = bookmark;
	}
} // End AVL_Node
