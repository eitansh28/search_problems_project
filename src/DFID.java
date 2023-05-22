import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class DFID {

    public static int max_possible_neigh = 8;
    public static int nodes_num_all , cost;
    public static String path1, num;

    public static String dfid(Node start, Node Goal, Map map, boolean clockwise, boolean print_time, boolean print_open_list) throws IOException {
        long start_Running = System.currentTimeMillis();
        long end_Running;
        double Running_time = 0;
        nodes_num_all++;  //begin with the start node
        List<Node>path = new ArrayList<>();
        String result;
        int depth = 1;   //begin with depth 1 (because start is not equal to goal..)
        while (true){
            Hashtable<Node,Boolean> nodesHash = new Hashtable<>();
//            System.out.println("threshold = "+depth);
            result = LimitedDFS(start,start,Goal,depth,nodesHash,map,clockwise, print_open_list,null,path,nodes_num_all);  //'result' will receive the result of the recursive function (path, cutoff or no path)
            if(!Objects.equals(result, "cutoff")){
                if(print_time){
                    end_Running = System.currentTimeMillis();
                    Running_time = (end_Running - start_Running) / 1000.0;
                }
                if (Objects.equals(result, "no path")){
                    Ex1.insert_results_to_file("no path",nodes_num_all, -1,Running_time,print_time);  //write the results in the output file
                }else{
                    Ex1.insert_results_to_file(path1,nodes_num_all, cost,Running_time,print_time);   //write the results in the output file
                }
                return result;
            }
            depth++;   //if we pass the previous threshold and there is no solution, increase the threshold by 1
//            System.out.println(depth);
            path.clear();
        }
    }


    public static String LimitedDFS(Node origin_start, Node curr_start, Node goal, int threshold, Hashtable<Node,Boolean> nodeHash, Map map, boolean clockwise, boolean print_open, Node father, List<Node>path, int nodes_num) throws IOException {
        nodes_num_all = nodes_num;
        curr_start.father = father;
        path.add(curr_start);  //Add (at least temporarily) the current node to the path
        String result;
        boolean isCufoff = true;
        Node curr_neigh;
        if(curr_start.equals(goal)){  //If we have reached the goal, the calculations of the directions and the cost are done
            goal.father = father;
            path1 = map.path_directions(map.find_path(goal, origin_start));
            cost = map.total_cost(map.find_path(goal, origin_start));
            return (map.find_path(goal, origin_start)).toString();
        }else if(threshold == 0){  //If we have reached the threshold, the current iteration can be stopped
            return "cutoff";
        }else{   //If the goal has not been reached and we have not yet passed the threshold, then the current node is expanded
            nodeHash.put(curr_start, true);
            isCufoff = false;
            for (int i = 1; i <=max_possible_neigh ; i++) {
                curr_neigh = map.neighbor(clockwise,curr_start,curr_start.father,i);
                if (curr_neigh == null){
                    continue;
                }
                nodes_num++;
                nodes_num_all++;
                if(nodeHash.containsKey(curr_neigh)){
                    continue;
                }
                nodes_num=nodes_num_all;  //update nodes_num before sending it to recursion
                result = LimitedDFS(origin_start,curr_neigh, goal,threshold-1,nodeHash,map,clockwise, print_open, curr_neigh.father,path,nodes_num);  //Enter recursion with the current neighbor, with a decrease threshold by 1
                if (Objects.equals(result, "cutoff")){
                    isCufoff = true;
                }else if(!Objects.equals(result, "no path")){
                    return result;
                }
            }
        }
        //If we didn't find any path, we can remove the current node from nodeHash and path
        nodeHash.remove(curr_start);
        if (print_open) System.out.println(nodeHash);
        path.remove(curr_start);
        if(isCufoff){
            return "cutoff";
        }
        return "no path";
    }
}
