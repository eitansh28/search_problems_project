//import java.awt.*;
//import java.awt.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class A_STAR {

    public static boolean first_new;   //A variable that tells us whether to act according to new-first or old-first
    public static int max_possible_neigh = 8;

    public static Comparator<Node> f_Comparator = new Comparator<>() {  //Creating a comparator for sorting the nodes in the priority queue (according to the f value of each node, increasing order)
        @Override
        public int compare(Node n1, Node n2) {
            if (first_new) {   //If there is equality in the f value, the choice depends on new-first/old-first
                if (n1.g_plus_h == n2.g_plus_h) return -1;
            }
            return n1.g_plus_h - n2.g_plus_h;
        }
    };

    public static void A_star(Node start, Node Goal, Map map, boolean clockwise, boolean print_time,boolean print_open_list, boolean new_first) throws IOException {
        long start_Running = System.currentTimeMillis();
        long end_Running;
        double Running_time = 0;
        first_new = new_first;
        PriorityQueue<Node> pQueue = new PriorityQueue<>(f_Comparator);  //Creating a priority queue that sorts according to the comparator we defined above
        Node curr_neigh;
        int nodes_num = 1;
        List<Node> path;
        Hashtable<Node, Integer> closed_list = new Hashtable<>();
        Hashtable<Node, Integer> open_list = new Hashtable<>();
        start.g_plus_h = map.h(start,Goal);  //init the start's f function to be the value of his heuristic function
        pQueue.add(start);
        open_list.put(start,0);
        while (pQueue.size() != 0){
            Node curr = Objects.requireNonNull(pQueue.poll());  //Remove a node from the queue to expand it
            open_list.remove(curr);   //Remove the curr from the open-list
            if (print_open_list) System.out.println(open_list);
            if(curr.equals(Goal)){   //If we have reached the goal, the calculations of the directions and the cost are done, and sent to be written to the output file
                Goal.father = curr.father;   //update goal's father
                path = map.find_path(Goal, start);
                String path_direc = map.path_directions(path);
                int total_cost = map.total_cost(path);
                if(print_time){
                    end_Running = System.currentTimeMillis();
                    Running_time = (end_Running - start_Running) / 1000.0;
                }
                Ex1.insert_results_to_file(path_direc,nodes_num,total_cost,Running_time, print_time);  //write the results in the output file
                return;
            }
            closed_list.put(curr,curr.cost_to_here);   //Because we are going to expand the current node, so add it to closed_list
            for (int i = 1; i <= max_possible_neigh; i++) {
                curr_neigh = map.neighbor(clockwise,curr,curr.father,i);
                if (curr_neigh == null){
                    continue;
                }
                nodes_num++;   //add 1 to the total count of nodes that created
                curr_neigh.g_plus_h = curr_neigh.cost_to_here + map.h(curr_neigh,Goal);  //update the f function for every neighbor
                if(!open_list.containsKey(curr_neigh) && !closed_list.containsKey(curr_neigh) && !pQueue.contains(curr_neigh)){  //If we haven't reached this node before then add to the queue and open-list
                    pQueue.add(curr_neigh);
                    open_list.put(curr_neigh, curr_neigh.cost_to_here);
                }
                else if(open_list.containsKey(curr_neigh)) {   //If we reached this node in the past and its current cost is cheaper than before, then the old one is replaced with the current one
                    if (open_list.get(curr_neigh) != null && open_list.get(curr_neigh) > curr_neigh.cost_to_here) {
                        pQueue.remove(curr_neigh);
                        pQueue.add(curr_neigh);
                        open_list.remove(curr_neigh);
                        open_list.put(curr_neigh, curr_neigh.cost_to_here);
                    }
                }
            }
        }
        //if we got here so there is no solution
        if(print_time){
            end_Running = System.currentTimeMillis();
            Running_time = (end_Running - start_Running) / 1000.0;
        }
        Ex1.insert_results_to_file("no path",nodes_num, -1,Running_time,print_time);   //write the results in the output file
    }
}
