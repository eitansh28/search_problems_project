import java.util.ArrayList;
import java.util.List;

public class Map {
    public int size;
    public char[][] Terrain;

    public Map(char[][] data){
        this.Terrain = data;
        this.size = data[0].length;
    }


    public static int max_possible_neigh = 8;

    public Node neighbor(boolean clockwise, Node curr, Node father, int neig_num){
        /*
        function that return the neighbor (or null, if not valid) according to specific index
        */
        int n = 0;
        if(!clockwise){    //change the order of the neigh if not clockwise
            if(neig_num == 1) n = 1;  //no change
            if(neig_num == 2) n = 8;
            if(neig_num == 3) n = 7;
            if(neig_num == 4) n = 6;
            if(neig_num == 5) n = 5;  //no change
            if(neig_num == 6) n = 4;
            if(neig_num == 7) n = 3;
            if(neig_num == 8) n = 2;
        }else {
            n = neig_num;
        }
        Node curr_neigh = null;
        switch (n) {    //Before creating each node I check that it is within the boundaries of the map, not marked with an X and not the father of the current node
            case 1:
                if (curr.col + 1 <= size && this.Terrain[curr.row - 1][curr.col] != 'X') {  //right
                    if (father == null || curr.row != curr.father.row || curr.col + 1 != curr.father.col) {
                        curr_neigh = new Node(curr.row, curr.col + 1);  //create new Node
                        curr_neigh.father = curr;   //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);  //update the cost of curr_neigh
                    }
                }break;
            case 2:
                if (curr.row + 1 <= size && curr.col + 1 <= size && this.Terrain[curr.row][curr.col] != 'X') {  //rightDown
                    if (father == null || curr.row + 1 != curr.father.row || curr.col + 1 != curr.father.col) {
                        curr_neigh = new Node(curr.row + 1, curr.col + 1);  //create new Node
                        curr_neigh.father = curr;  //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);  //update the cost of curr_neigh
                    }
                }break;
            case 3:
                if (curr.row + 1 <= size && this.Terrain[curr.row][curr.col - 1] != 'X') {  //down
                    if (father == null || curr.row + 1 != curr.father.row || curr.col != curr.father.col) {
                        curr_neigh = new Node(curr.row + 1, curr.col);  //create new Node
                        curr_neigh.father = curr;   //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);   //update the cost of curr_neigh
                    }
                }break;
            case 4:
                if (curr.row + 1 <= size && curr.col - 1 >= 1 && this.Terrain[curr.row][curr.col - 2] != 'X') {  //leftDown
                    if (father == null || curr.row + 1 != curr.father.row || curr.col - 1 != curr.father.col) {
                        curr_neigh = new Node(curr.row + 1, curr.col - 1);  //create new Node
                        curr_neigh.father = curr;   //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);   //update the cost of curr_neigh
                    }
                }break;
            case 5:
                if (curr.col - 1 >= 1 && this.Terrain[curr.row - 1][curr.col - 2] != 'X') {  //left
                    if (father == null || curr.row != curr.father.row || curr.col - 1 != curr.father.col) {
                        curr_neigh = new Node(curr.row, curr.col - 1);   //create new Node
                        curr_neigh.father = curr;   //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);   //update the cost of curr_neigh
                    }
                }break;
            case 6:
                if (curr.row - 1 >= 1 && curr.col - 1 >= 1 && this.Terrain[curr.row - 2][curr.col - 2] != 'X') {  //leftUp
                    if (father == null || curr.row - 1 != curr.father.row || curr.col - 1 != curr.father.col) {
                        curr_neigh = new Node(curr.row - 1, curr.col - 1);   //create new Node
                        curr_neigh.father = curr;   //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);   //update the cost of curr_neigh
                    }
                }break;
            case 7:
                if (curr.row - 1 >= 1 && this.Terrain[curr.row - 2][curr.col - 1] != 'X') {  //Up
                    if (father == null || curr.row - 1 != curr.father.row || curr.col != curr.father.col) {
                        curr_neigh = new Node(curr.row - 1, curr.col);   //create new Node
                        curr_neigh.father = curr;   //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);   //update the cost of curr_neigh
                    }
                }break;
            case 8:
                if (curr.row - 1 >= 1 && curr.col + 1 <= size && this.Terrain[curr.row - 2][curr.col] != 'X') {  //rightUp
                    if (father == null || curr.row - 1 != curr.father.row || curr.col + 1 != curr.father.col) {
                        curr_neigh = new Node(curr.row - 1, curr.col + 1);  //create new Node
                        curr_neigh.father = curr;   //update curr_neigh to be son of curr
                        curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);    //update the cost of curr_neigh
                    }
                }break;
        }
        return curr_neigh;
    }

    public List<Node> neighbors(boolean clockwise, Node curr){
        /*
        function that return all the neighbor of the current node
        */
        ArrayList<Node> neigh = new ArrayList<>();
        Node curr_neigh;
        for(int i=1;i<=max_possible_neigh;i++){
            curr_neigh = neighbor(clockwise,curr,curr.father,i);
            if (curr_neigh != null){
                neigh.add(curr_neigh);
            }
        }
        return neigh;
    }


    public ArrayList<Node> find_path(Node goal, Node start){
        /*
        A function that returns a path between 2 nodes by tracking the father
         */
        ArrayList<Node> path = new ArrayList<>();
        Node n = goal;
        while (! n.equals(start)){   //insert the path in opposite way, starting from the goal
            path.add(0, n);
            n = n.father;
        }
        path.add(0, start);
        return path;
    }

    public int total_cost(List<Node>path){
        /*
        A function that returns the cost of a path between 2 nodes
         */
        int total_cost = 0;
        for (int i = 0; i < path.size()-1; i++) {
            total_cost+= cost(path.get(i), path.get(i+1));
        }
        return total_cost;
    }

    public int cost(Node father, Node son){
        /*
        A function that returns the cost of one step to some node
         */
        int cost = 0;
        if(this.Terrain[son.row - 1][son.col - 1] == 'D'){
            cost = 1;
        }
        if(this.Terrain[son.row - 1][son.col - 1] == 'R'){
            cost = 3;
        }
        if(this.Terrain[son.row - 1][son.col - 1] == 'H'){
            cost = 5;
            if(father != null && father.row != son.row && father.col != son.col){
                cost+= 5;
            }
        }
        if(this.Terrain[son.row - 1][son.col - 1] == 'G'){
            cost = 5;
        }
        return cost;
    }

    public String convert_path_to_directions(Node father, Node son){
        /*
        A function that convert one step to direction
         */
        String ans ="";
        if(father.row == son.row && father.col+1 == son.col){    //right
            ans+="-R";
        }
        if(father.row+1 == son.row && father.col+1 == son.col){    //rightDown
            ans+="-RD";
        }
        if(father.row+1 == son.row && father.col == son.col){    //down
            ans+="-D";
        }
        if(father.row+1 == son.row && father.col-1 == son.col){    //leftDown
            ans+="-LD";
        }
        if(father.row == son.row && father.col-1 == son.col){    //left
            ans+="-L";
        }
        if(father.row-1 == son.row && father.col-1 == son.col){    //leftUp
            ans+="-LU";
        }
        if(father.row-1 == son.row && father.col == son.col){    //up
            ans+="-U";
        }
        if(father.row-1 == son.row && father.col+1 == son.col){    //rightUp
            ans+="-RU";
        }
        return ans;
    }

    public String path_directions(List<Node> path){
        /*
        A function that convert a path to directions
         */
        String direc = "";
        for (int i = 0; i < path.size()-1; i++) {
            direc+= convert_path_to_directions(path.get(i), path.get(i+1));
        }
        return direc.substring(1);

    }


    public int g(Node curr){
        /*
        A function that calculate the cost from start to curr
         */
        int inf = Integer.MAX_VALUE;
        if (this.Terrain[curr.row-1][curr.col-1] == 'X'){
            return inf;
        }
        return curr.cost_to_here;
    }

    public int h(Node curr ,Node goal){
        /*
        A heuristic function that estimates the cost from curr to goal
        */
        // Calculate heuristic function, A full explanation is given in the 'details.docx' file
        int heuristic_eval = 0;
        if (Math.abs(goal.col - curr.col) < Math.abs(goal.row - curr.row)){
            heuristic_eval = Math.abs(goal.row - curr.row)  - cost(null,curr) + 5;
        }
        else if(Math.abs(goal.col - curr.col) >= Math.abs(goal.row - curr.row)){
            heuristic_eval = Math.abs(goal.col - curr.col)  - cost(null,curr) + 5;
        }
        curr.g_plus_h = curr.cost_to_here + heuristic_eval;
        return heuristic_eval;
    }

    public int f(Node curr ,Node goal){
        /*
        A function that use for evaluate the nodes in informed algorithms
         */
        return g(curr) + h(curr, goal);
    }

    public void init_h(List<Node>nodes,Node Goal){
        /*
        A function that display the h function on list of nodes
         */
        for (Node node : nodes) {
            this.h(node, Goal);
        }
    }

}




















//        int index = 0;
//
//        if (curr.col + 1 <= size && this.Terrain[curr.row - 1][curr.col] != 'X'){  //right
//            curr_neigh = new Node(curr.row, curr.col + 1);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                neig.add(curr_neigh);
//                index = 1;
//            }
//        }
//        if (curr.row + 1 <= size && curr.col + 1 <= size && this.Terrain[curr.row][curr.col] != 'X'){  //rightDown
//            curr_neigh = new Node(curr.row + 1, curr.col+1);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                if (clockwise) {
//                    neig.add(curr_neigh);
//                } else {
//                    neig.add(index, curr_neigh);
//                }
//            }
//        }
//        if (curr.row + 1 <= size && this.Terrain[curr.row][curr.col -1] != 'X'){  //down
//            curr_neigh = new Node(curr.row + 1, curr.col);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                if (clockwise) {
//                    neig.add(curr_neigh);
//                } else {
//                    neig.add(index, curr_neigh);
//                }
//            }
//        }
//        if (curr.row + 1 <= size && curr.col - 1 >= 1 && this.Terrain[curr.row][curr.col - 2] != 'X'){  //leftDown
//            curr_neigh = new Node(curr.row + 1, curr.col - 1);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                if (clockwise) {
//                    neig.add(curr_neigh);
//                } else {
//                    neig.add(index, curr_neigh);
//                }
//            }
//        }
//        if (curr.col - 1 >= 1 && this.Terrain[curr.row - 1][curr.col - 2] != 'X'){  //left
//            curr_neigh = new Node(curr.row, curr.col - 1);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                if (clockwise) {
//                    neig.add(curr_neigh);
//                } else {
//                    neig.add(index, curr_neigh);
//                }
//            }
//        }
//        if (curr.row - 1 >= 1 &&  curr.col - 1 >=1 && this.Terrain[curr.row - 2][curr.col - 2] != 'X'){  //leftUp
//            curr_neigh = new Node(curr.row - 1, curr.col - 1);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                if (clockwise) {
//                    neig.add(curr_neigh);
//                } else {
//                    neig.add(index, curr_neigh);
//                }
//            }
//        }
//        if (curr.row - 1 >= 1 &&  this.Terrain[curr.row - 2][curr.col - 1] != 'X'){  //Up
//            curr_neigh = new Node(curr.row - 1, curr.col);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                if (clockwise) {
//                    neig.add(curr_neigh);
//                } else {
//                    neig.add(index, curr_neigh);
//                }
//            }
//        }
//        if (curr.row - 1 >= 1 && curr.col + 1 <= size &&  this.Terrain[curr.row - 2][curr.col] != 'X'){  //rightUp
//            curr_neigh = new Node(curr.row - 1, curr.col + 1);
//            if(!curr_neigh.equals(curr.father)) {
//                curr_neigh.father = curr;
//                curr_neigh.cost_to_here = curr.cost_to_here + cost(curr,curr_neigh);
//                if (clockwise) {
//                    neig.add(curr_neigh);
//                } else {
//                    neig.add(index, curr_neigh);
//                }
//            }
//        }