import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Ex1 {

    public static void insert_results_to_file(String path, int nodes_num, int cost, double time, boolean print_time) throws IOException {
        FileWriter writer = new FileWriter("output.txt");
        System.out.println(path);
        System.out.println("Num: " + nodes_num);
        writer.write(path + "\n");
        writer.write("Num: " + nodes_num + "\n");
        if (cost == -1){
            System.out.println("Cost: inf");
            writer.write("Cost: inf\n");
        }else {
            System.out.println("Cost: " + cost);
            writer.write("Cost: " + cost + "\n");
        }
        if (print_time){
            String final_time = String.format("%.4f seconds\n",time);
            System.out.println(final_time);
            writer.write(""+final_time);
        }
        writer.close();
    }


    public static char[][] make_array_from_input(int size, ArrayList<Object> args) {
        /*
        Creating a matrix (map) from the input data
         */
        char[][] map = new char[size][size];
        for (int i = 0; i < size; i++) {
            String line = (String) args.get(i);
            for (int j = 0; j < size; j++) {
                map[i][j] = line.charAt(j);
            }
        }return map;
    }

    public static void main(String[] args) throws IOException {
        List<Object> inputs_args = new ArrayList<Object>();

        Scanner scanner = new Scanner(new File("input.txt"));
        while (scanner.hasNextLine()) {
            inputs_args.add(scanner.nextLine());
        }
        //parse the input
        String algo = (String)inputs_args.get(0);
        String create_nodes_order = (String)inputs_args.get(1);
        boolean clockwise = Objects.equals(create_nodes_order, "clockwise");
        String Node_preference = null;
        if(Objects.equals(algo, "DFBnB") || Objects.equals(algo, "A*")){
            String[] temp = create_nodes_order.split(" ", 2);
            Node_preference = temp[1];
        }
        boolean new_first = Objects.equals(Node_preference, "new-first");
        String str_time = (String)inputs_args.get(2);
        boolean print_time = Objects.equals(str_time, "with time");
        String temp_open = (String)inputs_args.get(3);
        boolean print_open_list = Objects.equals(temp_open, "with open");
        int map_size = Integer.parseInt(inputs_args.get(4).toString());
        String start_and_goal = (String)inputs_args.get(5);
        Node start = new Node(Character.getNumericValue(start_and_goal.charAt(1)),Character.getNumericValue(start_and_goal.charAt(3)),null);
        Node goal = new Node(Character.getNumericValue(start_and_goal.charAt(7)),Character.getNumericValue(start_and_goal.charAt(9)), null);
        ArrayList<Object> map_args = new ArrayList<Object>(inputs_args.subList(6, inputs_args.size()));
        char[][] draw_map = make_array_from_input(map_size, map_args);
        Map map = new Map(draw_map);

        //Running the algorithm based on the input
//        if(Objects.equals(algo, "BFS")){
//            BFS.bfs(start,goal, map, clockwise,print_time ,print_open_list);
//        }
//        else if(Objects.equals(algo, "DFID")){
//            DFID.dfid(start,goal, map, clockwise, print_time);
//        }
//        else if(Objects.equals(algo, "A*")){
//            A_STAR.A_star(start, goal, map, clockwise, print_time ,print_open_list, new_first);
//        }
//        else if(Objects.equals(algo, "IDA*")){
//            IDA_STAR.IDA_star(start, goal, map, clockwise, print_time ,print_open_list);
//        }
//        else if(Objects.equals(algo, "DFBnB*")){
//            DFBnB.dfbnb(start, goal, map, clockwise, print_time ,print_open_list, new_first);
//        }


        Node n = new Node(3, 2);
        Node v = new Node(2, 6);
//        BFS.bfs(start,goal, map, clockwise,print_time ,print_open_list);
//            DFID.dfid(start,goal, map, clockwise, print_time, print_open_list);
//        A_STAR.A_star(start, v, map, clockwise, print_time ,print_open_list, false);
//        IDA_STAR.IDA_star(start, v, map, clockwise, print_time ,print_open_list);
        DFBnB.dfbnb(start, v, map, clockwise, print_time ,print_open_list, true);
}
}
