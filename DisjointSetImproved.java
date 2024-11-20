/* Dr. Steinberg
   COP3503 Computer Science 2
   Disjoint Set Data Structure
   DisjointSetImproved.java
   Disjoint Set with improvement using Union by Rank and Path Compression
*/


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
	
	
	public static void printSets(int[] universe, DisjointSetImproved ds)
    {
        for (int i: universe) 
		{
            System.out.print(ds.find(i) + " ");
        }
 
        System.out.println();
    }
	
	
	public static void main(String [] args)
	{
		DisjointSetImproved dus = new DisjointSetImproved(5);
		
		int[] universe = {0, 1, 2, 3, 4};
		printSets(universe, dus);
 
        // 0 is a friend of 2
        dus.union(0, 2);
		
		printSets(universe, dus);
 
        // 4 is a friend of 2
        dus.union(4, 2);
 
        // 3 is a friend of 1
        dus.union(3, 1);
		
		printSets(universe, dus);
 
        // Check if 4 is a friend of 0
        if (dus.find(4) == dus.find(0))
            System.out.println("Yes");
        else
            System.out.println("No");
 
        // Check if 1 is a friend of 0
        if (dus.find(1) == dus.find(0))
            System.out.println("Yes");
        else
            System.out.println("No");
	}
	
}