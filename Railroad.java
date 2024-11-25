/* Ethan McKissic
   Dr. Steinberg
   COP3503 Fall 2024
   Programming Assignment 5
*/

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Railroad 
{
    private int edgeCount; //num of railroad tracks
    private Edge[] edges; // declare array to store tracks 
    private HashMap<String, Integer> vertices = new HashMap<>(); // hashmap to assign vertex indexes 
    private int cost; // accumaltes total cost 

    public Railroad(int edgeCount, String fileName) throws IOException
    {
        this.edgeCount = edgeCount; //store edge count 
        edges = new Edge[edgeCount]; //initialize edges array 

        BufferedReader br = new BufferedReader(new FileReader(fileName)); //read from test case file  

        String line; //store string on current line 
        int cnt = 0; //keep track of array index 
        int hashValue = 0; //keep track of each hash index 

        //read each line from input file 
        while((line = br.readLine()) != null)
        { 
            String[] words = line.split(" "); //split by source, destination, and weight 

            //store values into graph 
            edges[cnt] = new Edge(words[0], words[1], Integer.parseInt(words[2])); //store values into graph 
            
            //check if vertex in hashmap 
            for(int i = 0; i < 2; i++)
            {   
                //if vertex has not been inserted 
                if(!vertices.containsKey(words[i]))
                {
                    vertices.put(words[i], hashValue); //insert vertex into hash table 
                    hashValue++; //upddate hashvalue 
                }
            }
            cnt++; //increment count 
        }

        br.close(); //close reader when done 
    }

    //method to run kruskal's algorithm 
    public String buildRailroad()
    {
        int v = vertices.size(); //extract # of vertices 

        //create empty disjoint set 
        DisjointSetImproved disjoint = new DisjointSetImproved(v); 
        disjoint.makeSet(); 
        
        Arrays.sort(edges, Comparator.comparingInt(w -> w.weight)); //sort edges based on weight 

        for(int i = 0; i < edgeCount; i++)
        {
            //index for source and destination vertex 
            int source = vertices.get(edges[i].src);
            int dest = vertices.get(edges[i].dest);

            //if they do not share representative 
            if(disjoint.find(source) != disjoint.find(dest))
                disjoint.union(source, dest); //combine vertices into set 

            //if cycle exists 
            else 
                edges[i].weight = -1; //we will ignore this edge 
        }

        return printRailRoad(); //output costs
    }

    public String printRailRoad()
    {    
        //for each edge in the railroad 
        for(int i = 0; i < edgeCount; i++)
        {
            //if edge did not create cycle 
            if(edges[i].weight != -1)
            {
                //prints railroad tracks with desired formatting 
                if(edges[i].src.compareTo(edges[i].dest) < 1)
                    System.out.print(edges[i].src + "---" + edges[i].dest + "\t$" + edges[i].weight + "\n");

                else
                    System.out.print(edges[i].dest + "---" + edges[i].src + "\t$" + edges[i].weight + "\n");

                cost += edges[i].weight; //tally up cost 
            }
        }

        return "The cost of the railroad is $"+cost+"."; //return total cost 
    }

    //object to represent graph edges 
    public class Edge
    {
        String src, dest; //vertices
        int weight; //cost

        public Edge(String src, String dest, int weight)
        {
            this.src = src; 
            this.dest = dest; 
            this.weight = weight; 
        }
    }

    //disjoint code from dr. steinberg
    public class DisjointSetImproved
    {
        int [] rank;
        int [] parent;
        int n;
        
        public DisjointSetImproved(int n)
        {
            rank = new int[n];
            parent = new int[n];
            this.n = n;
            makeSet();
        }
        
        // Creates n sets with single item in each
        public void makeSet()
        {
            for (int i = 0; i < n; i++) 
            {
                parent[i] = i;
            }
        }
        
        //path compression
        public int find(int x)
        {
            if (parent[x] != x) 
            {
                parent[x] = find(parent[x]);
            }
    
            return parent[x];
        }
        
        //union by rank
        public void union(int x, int y)
        {
            int xRoot = find(x), yRoot = find(y);
    
            if (xRoot == yRoot)
                return;
            
            if (rank[xRoot] < rank[yRoot])
                parent[xRoot] = yRoot;
            
            else if (rank[yRoot] < rank[xRoot])
                parent[yRoot] = xRoot;
            else 
            {
                parent[yRoot] = xRoot;
                rank[xRoot] = rank[xRoot] + 1;
            }
        }
    }
}