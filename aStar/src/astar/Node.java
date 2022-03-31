/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

/**
 *
 * @author Unknown (UNCC), Edited: Marcel Colón
 * @date 7/28/2021
 * @version 2.0
 */
public class Node implements Comparable<Node> {      //implements comparable so that the PriorityQueue API can be used with the F value

    private int row, col, f, g, h, type;
    private Node parent;

    /**
     * Description: Constructor for a Node object
     *
     * @param r Integer of the row index
     * @param c Integer of the column index
     * @param t Integer of the nodes type
     */
    public Node(int r, int c, int t) {
        row = r;
        col = c;
        type = t;
        parent = null;
        //type 0 is traverseable, 1 is not
    }

    //mutator methods to set values
    /**
     * Description: Method to set object F value
     */
    public void setF() {
        f = g + h;
    }

    /**
     * Description: Method to set objects g value
     *
     * @param value Integer input to set g value
     */
    public void setG(int value) {
        g = value;
    }

    /**
     * Description: Method to set h value
     *
     * @param value Integer input to set h value
     */
    public void setH(int value) {
        h = value;
    }

    /**
     * Description: Method to set parent
     *
     * @param n Object of node type to set as parent
     */
    public void setParent(Node n) {
        parent = n;
    }

    //accessor methods to get values
    /**
     * Description: Method to get g value
     *
     * @return Integer of the f value
     */
    public int getF() {
        return f;
    }

    /**
     * Description: Method to get the g value
     *
     * @return Integer of g value
     */
    public int getG() {
        return g;
    }

    /**
     * Description: Method to get h value
     *
     * @return Integer of h value
     */
    public int getH() {
        return h;
    }

    /**
     * Description: Method to get parent object
     *
     * @return Node object of parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Description: Method to get row index
     *
     * @return Integer of row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Description: Method to get column index
     *
     * @return Integer of column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Description: Method to get objects type
     *
     * @return Integer of the objects type (0 is traversable, 1 is not)
     */
    public int getType() {
        return type;
    }

    /**
     * Description: Method used to compare objects f value. Needed for
     * PriorityQueue API
     *
     * @param other Node object that current object is being compared to
     * @return Integer value used by PriorityQueue API to evaluate compare
     */
    public int compareTo(Node other) {
        if (this.equals(other)) {
            return 0;
        } else if (getF() > other.getF()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Description: Method to see if two objects are the same
     *
     * @param in The object to compare to
     * @return Boolean of the results of the object comparison
     */
    public boolean equals(Object in) {
        //typecast to Node
        Node n = (Node) in;

        return row == n.getRow() && col == n.getCol();
    }

    /**
     * Description: Method to display objects indexes
     *
     * @return String of the objects indexes
     */
    public String toString() {
        return "Node: " + row + "_" + col;
    }

}
