public class Node{   //Creating a class named 'Node' to represent a vertex
    public int row;
    public int col;
    public Node father;  //The node from which we reached the current node (used to know where not to return)
    public int cost_to_here;  //The cost of getting from a certain starting node to the current node
    public boolean out;   //Used to indicate if the vertex is part of the path (in DFBnB)
    public int g_plus_h;   //f function, for the informed algorithms


    public Node(int row, int col){  //constructor
        this.row = row;
        this.col = col;
        this.father = null;
    }

    public Node(int row, int col, Node father){  //constructor (with specific father)
        this.row = row;
        this.col = col;
        this.father = father;
    }

    public Node(Node n){
        this.row = n.row;
        this.col = n.col;
        this.father = n.father;
        this.cost_to_here = n.cost_to_here;
    }


    @Override
    public boolean equals(Object other) {
        /*
        Overriding the equal function to allow equality between nodes according to the row and column
         */
        if (other == null || getClass() != other.getClass())
            return false;
        Node node = (Node) other;
        return row == node.row && col == node.col;
    }


    @Override
    /*
    A function that  generates a unique hash code for an object based on its row and col values, which can be used to improve performance in data structures that rely on hash codes.
     */
    public int hashCode() {
        final int prime = 7;   //By using a prime number (7) we ensure uniqueness for the position of the object in the table
        int result = 1;
        result = prime * result + row;
        result = prime * result + col;
        return result;
    }


    @Override
    public String toString() {
        return "("+ row + "," + col + ") cost = "+ g_plus_h;
    }

}
