import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BFS {
    public static int max_possible_neigh = 8;

    public static void bfs(Node start, Node Goal, Map map, boolean clockwise, boolean print_time,boolean print_open_list) throws IOException {
        long start_Running = System.currentTimeMillis();
        long end_Running;
        double Running_time = 0;
        Node curr_neigh;
        int nodes_num = 1;
        List<Node> path;
        Hashtable<Node, Boolean> closed_list = new Hashtable<>();
        Hashtable<Node, Boolean> open_list = new Hashtable<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        open_list.put(start, true);
        while (queue.size() != 0) {
            if(print_open_list) System.out.println(open_list);
            Node curr = Objects.requireNonNull(queue.poll());  //Remove a node from the queue to expand it
            open_list.remove(curr);  //Remove the curr from the open-list
            for (int i = 1; i <= max_possible_neigh; i++) {
                curr_neigh = map.neighbor(clockwise, curr, curr.father, i);
                if (curr_neigh == null) {
                    continue;
                }
                nodes_num++;  //add 1 to the total count of nodes that created
                if (open_list.containsKey(curr_neigh) || closed_list.containsKey(curr_neigh) || queue.contains(curr_neigh)) {  //loop avoidance
                    continue;
                } else if (curr_neigh.equals(Goal)) {  //If we have reached the goal, the calculations of the directions and the cost are done, and sent to be written to the output file
                    Goal.father = curr;  //update goal's father
                    path = map.find_path(Goal, start);
                    String path_direc = map.path_directions(path);
                    int total_cost = map.total_cost(path);
                    if(print_time){
                        end_Running = System.currentTimeMillis();
                        Running_time = (end_Running - start_Running) / 1000.0;
                    }
                    Ex1.insert_results_to_file(path_direc,nodes_num, total_cost,Running_time,print_time);   //write the results in the output file
                    return;
                }
                queue.add(curr_neigh);
                open_list.put(curr_neigh, true);
            }
            closed_list.put(curr, true);   //After expanding we add to closed-list
        }
        //if we got here so there is no solution
        if(print_time){
            end_Running = System.currentTimeMillis();
            Running_time = (end_Running - start_Running) / 1000.0;
        }
        Ex1.insert_results_to_file("no path",nodes_num, -1,Running_time,print_time);  //write the results in the output file
    }

}
