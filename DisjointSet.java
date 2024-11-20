/* Dr. Steinberg
   COP3503 Computer Science 2
   Disjoint Set Data Structure
   DisjointSet.java
   Typical Disjoint Set with no improvements...
*/


public class DisjointSet
{
	int [] rank;
	int [] parent;
    int n;
	
    public DisjointSet(int n)
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
	
	public int find(int i)
	{
		if (parent[i] == i) 
		{
			return i;
		}
		else 
		{
			return find(parent[i]);
		}
	}
	
	public void union(int i, int j) 
	{
		int irep = find(i);

		int jrep = find(j);

		parent[irep] = jrep; //merge
	}
	
	
	public static void printSets(int[] universe, DisjointSet ds)
    {
        for (int i: universe) 
		{
            System.out.print(ds.find(i) + " ");
        }
 
        System.out.println();
    }
	
	
	public static void main(String [] args)
	{
		DisjointSet dus = new DisjointSet(5);
		
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