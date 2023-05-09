import java.io.IOException;
import java.util.*;

public class DFBnB {

    public static boolean first_new;   //A variable that tells us whether to act according to new-first or old-first

    public static Comparator<Node> f_Comparator = new Comparator<>() {   //Creating a comparator for sorting the nodes in the neighbors list (according to the f value of each node, increasing order)
        @Override
        public int compare(Node n1, Node n2) {
            if (first_new) {    //If there is equality in the f value, the choice depends on new-first/old-first
                if (n1.g_plus_h == n2.g_plus_h) return -1;
            }
            return n1.g_plus_h - n2.g_plus_h;
        }
    };


    public static List<Node> path(Stack<Node>stack, Node goal){
        /*
        A function that receives a stack with nodes and produces a path from it in reverse order to the nodes in the stack
         * The difference between this function and the function in IDA_STAR is that we do not remove all the nodes of the path from the stack, because we want to continue the algorithm
         */
        List<Node> path = new ArrayList<>();
        Node curr;
        stack.add(goal);
        for (Node node : stack) {
            curr = node;
            if (curr.out) {   //If the node is marked with 'out' then it is part of the current path
                path.add(curr);
            }
        }
        stack.pop();
        return path;
    }

    public static void dfbnb(Node start, Node Goal, Map map, boolean clockwise, boolean print_time, boolean print_open_list, boolean new_first) throws IOException {
        long start_Running = System.currentTimeMillis();
        long end_Running;
        double Running_time = 0;
        Stack<Node>stack = new Stack<>();
        Hashtable<Node, Character> open_list_hash = new Hashtable<>();
        String result = null;
        int threshold = 10* (map.size);
        first_new = new_first;
        Node curr, curr_neigh;
        List<Node> neighbors;
        List<Node> final_path = null;
        int total_cost;
        int nodes_num = 1;
        start.g_plus_h = map.h(start,Goal);   //init the start's f function to be the value of his heuristic function
        stack.add(start);
        while(!stack.isEmpty()){
            if (print_open_list) System.out.println(open_list_hash);
            curr = (Objects.requireNonNull(stack.pop()));  //Remove a node from the stack to expand it
            if (open_list_hash.get(curr) != null && open_list_hash.get(curr) == 't') {   //used for loop avoidance
                open_list_hash.remove(curr);
            }else{    //If the node is not yet marked as part of the path then it should be marked as part of the path
                open_list_hash.remove(curr);
                open_list_hash.put(curr, 't');
                stack.remove(curr);
                curr.out = true;
                stack.add(curr);
                neighbors = map.neighbors(clockwise,curr);  //Creating all the neighbors of the current node, for the purpose of sorting according to the f func values
                nodes_num+= neighbors.size();   //Adding the number of neighbors to the number of nodes created
                map.init_h(neighbors, Goal);  //init the h func value foe each neighbor
                neighbors.sort(f_Comparator);   //Sorting the neighbors according to the f func values
                for (int i=0;i<neighbors.size();i++){
                    curr_neigh = neighbors.get(i);
                    if(map.f(curr_neigh,Goal) >= threshold){  //If the f value of the current neighbor is greater than the threshold, Remove the current node and the following nodes from the list of neighbors
                        for(int j=i;j<neighbors.size();j++){
                            neighbors.remove(j);
                            j--;
                        }
                    }else if(open_list_hash.containsKey(curr_neigh) && open_list_hash.get(curr_neigh) == 't'){  //If it is already in the open-list and marked as 'out', then it is removed from the list of neighbors
                        neighbors.remove(curr_neigh);
                        i--;
                    }else if(open_list_hash.containsKey(curr_neigh) && open_list_hash.get(curr_neigh) != 't'){   //If the current neighbor is in the stack but not marked as part of the path
                        int index = stack.indexOf(curr_neigh);
                        if (map.f(curr_neigh, Goal) >= map.f(stack.get(index),Goal)){  //If the f value of the old node is smaller ro equal than the f value of the current neighbor, then it is removed from the list of neighbors
                            neighbors.remove(curr_neigh);
                            i--;
                        }else{   //If the current value is smaller, then remove the old node from the stack and open-list
                            stack.remove(curr_neigh);
                            open_list_hash.remove(curr_neigh);
                        }
                    }else if(curr_neigh.equals(Goal)){   //If we have reached the goal, the calculations of the directions and the cost are done
                        threshold = map.f(curr_neigh, Goal);   //Update the threshold to be the f value of the current neighbor
                        curr_neigh.out = true;  //Marking the current neighbor as part of the path
                        final_path = path(stack,curr_neigh);
                        result = map.path_directions(final_path);   //Inserting the path we found into the result
                        for(int j=i;j<neighbors.size();j++){  //Remove the current node and the following nodes from the list of neighbors
                            neighbors.remove(j);
                            j--;
                        }
                    }
                }
                for(int i=neighbors.size()-1; i>=0 ;i--){  //Inserting the remaining neighbors into the stack in reverse order
                    stack.add(neighbors.get(i));
                    open_list_hash.put(neighbors.get(i), 'f');
                }
            }
        }

        if(print_time){
            end_Running = System.currentTimeMillis();
            Running_time = (end_Running - start_Running) / 1000.0;
        }
        if(final_path != null){
            total_cost = map.total_cost(final_path);
            Ex1.insert_results_to_file(result,nodes_num,total_cost,Running_time,print_time);  //write the results in the output file
        }else{
            //if we got here so there is no solution
            Ex1.insert_results_to_file("no path",nodes_num,-1,Running_time,print_time);  //write the results in the output file
        }


    }
}
