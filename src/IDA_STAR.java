import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IDA_STAR {

    public static List<Node> final_path = null;
    public static int max_possible_neigh = 8;

    public static List<Node> path(Stack<Node>stack){
        /*
        A function that receives a stack with nodes and produces a path from it in reverse order to the nodes in the stack
         */
        List<Node> path = new ArrayList<>();
        Node curr;
        while (! stack.isEmpty()){
            curr = stack.pop();
            if(curr.out){  //If the node is marked with 'out' then it is part of the current path
                path.add(0,curr);
            }
        }return path;
    }

    public static void IDA_star(Node start, Node Goal, Map map, boolean clockwise, boolean print_time, boolean print_open_list) throws IOException {
        long start_Running = System.currentTimeMillis();
        long end_Running;
        double Running_time = 0;
        Stack<Node> stack = new Stack<>();
        Hashtable<Node, Character> open_list_hash = new Hashtable<>();
        int infinity = Integer.MAX_VALUE;
        int threshold = map.h(start, Goal);  //init the threshold to be the value of the start's heuristic function
        int minF;  //A variable that will keep the minimum f value we reached in each iteration
        int nodes_num = 1;
        Node curr, curr_neigh;
        while (threshold != infinity) {
            minF = Integer.MAX_VALUE;
            stack.add(start);
            open_list_hash.put(start, 'f');
            while (!stack.isEmpty()) {
                curr = (Objects.requireNonNull(stack.pop()));  //Remove a node from the stack to expand it
                if (print_open_list){
                    System.out.println(open_list_hash);
                }
                if (open_list_hash.get(curr) != null && open_list_hash.get(curr) == 't') {   //used for loop avoidance
                    open_list_hash.remove(curr);
                } else {    //If the node is not yet marked as part of the path then it should be marked as part of the path
                    open_list_hash.remove(curr);
                    open_list_hash.put(curr, 't');
                    curr.out = true;
                    stack.add(curr);
                    for (int i = 1; i <= max_possible_neigh; i++) {
                        curr_neigh = map.neighbor(clockwise, curr, curr.father, i);
                        if (curr_neigh == null) {
                            continue;
                        }
                        nodes_num++;  //add 1 to the total count of nodes that created
//                        curr_neigh.g_plus_h = curr_neigh.cost_to_here + map.h(curr_neigh,Goal);
                        if (map.f(curr_neigh, Goal) > threshold) {   //If the f value of the current neighbor is greater than the threshold, then minF is updated to be the f value of the current neighbor, and we move to the next neighbor
                            minF = Math.min(minF, map.f(curr_neigh, Goal));
                            continue;
                        }
                        if (open_list_hash.containsKey(curr_neigh) && open_list_hash.get(curr_neigh) == 't') {   //used for loop avoidance
                            continue;
                        }
                        if (open_list_hash.containsKey(curr_neigh) && open_list_hash.get(curr_neigh) != 't') {  //If the current neighbor is in the stack but not marked as part of the path
                            int index = stack.indexOf(curr_neigh);
                            if (map.f(curr_neigh, Goal) < map.f(stack.get(index),Goal)) {  //If the f value of the old node is greater than the f value of the current neighbor, then the old node is removed from the stack and the open-list
                                stack.remove(curr_neigh);
                                open_list_hash.remove(curr_neigh);
                            } else {
                                continue;
                            }
                        }
                         if (curr_neigh.equals(Goal)) {   //If we have reached the goal, the calculations of the directions and the cost are done, and sent to be written to the output file
                             curr_neigh.out = true;   //Marked the goal as 'out' to be part of path
                             stack.add(curr_neigh);
                             final_path = path(stack);
                             String path_direc = map.path_directions(final_path);
                             int total_cost = map.total_cost(final_path);
                             if(print_time){
                                 end_Running = System.currentTimeMillis();
                                 Running_time = (end_Running - start_Running) / 1000.0;
                             }
                             Ex1.insert_results_to_file(path_direc,nodes_num,total_cost,Running_time, print_time);   //write the results in the output file
                            return;
                        }
                        stack.add(curr_neigh);   //If the current neighbor is not the goal and is not already in the stack or the open-list, then put it in the stack and the open-list
                        open_list_hash.put(curr_neigh, 'f');
                    }
                }
            }
            threshold = minF;   //update the threshold
        }
        //if we got here so there is no solution
        if(print_time){
            end_Running = System.currentTimeMillis();
            Running_time = (end_Running - start_Running) / 1000.0;
        }
        Ex1.insert_results_to_file("no path",nodes_num, -1,Running_time,print_time);  //write the results in the output file
    }
}