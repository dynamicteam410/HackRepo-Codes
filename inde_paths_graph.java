// Java Program to Find Independent Sets in a Graph
// by Graph Coloring

// Importing input output classes
import java.io.*;
// Importing utility classes from java.util package
import java.util.*;

// Class 1
// Helper class
class GFGUTIL {

	// Method 1
	// Utility function to label maximum vertices with
	// 0,that can be included in the set
	public static void
	Util(Vector<Vector<Integer> > adjacency_list,
		Vector<Integer> color)
	{
		int a = 0;

		// Condition check
		while (a != -1) {
			a = remove_all(adjacency_list, color);
			if (a != -1)
				color.set(a, 0);
		}
	}

	// Method 2
	// This method that tries whether it is possible to
	// remove any adjacent vertex of any removed vertex
	public static void
	Util2(Vector<Vector<Integer> > adjacency_list,
		Vector<Integer> color, int j)
	{

		int cnt = 0;

		Vector<Integer> tmp_color = new Vector<Integer>();
		for (int g = 0; g < color.size(); ++g)
			tmp_color.add(color.get(g));

		for (int i = 0; i < color.size(); ++i) {
			if (tmp_color.get(i) == 1) {
				int sum = 0;
				int idx = -1;
				for (int g = 0; g < adjacency_list.get(i).size(); ++g)
					if (tmp_color.get(adjacency_list.get(i).get(g)) == 0) {
						idx = g;
						sum++;
					}

				if (sum == 1 && color.get(adjacency_list.get(i).get(idx))== 0) {
					tmp_color.set(adjacency_list.get(i).get(idx), 1);
					tmp_color.set(i, 0);
					Util(adjacency_list, tmp_color);
					++cnt;
				}

				if (cnt > j)
					break;
			}
		}
		for (int g = 0; g < color.size(); ++g)
			color.set(g, tmp_color.get(g));
	}

	// Method 3
	// Returning the number of vertices
	// that can't be included in the set
	public static int Util3(Vector<Integer> color)
	{
		int cnt = 0;

		for (int i = 0; i < color.size(); i++)
			if (color.get(i) == 1)
				++cnt;
		return cnt;
	}

	// Method 4
	// Returning the index of the vertex
	public static int
	remove_all(Vector<Vector<Integer> > adjacency_list, Vector<Integer> color)
	{
		int a = -1, max = -1;

		for (int i = 0; i < color.size(); ++i) {
			if (color.get(i) == 1 && can_remove(adjacency_list.get(i), color) == 1) {

				Vector<Integer> tmp_color = new Vector<Integer>();

				for (int j = 0; j < color.size(); ++j)
					tmp_color.add(color.get(j));
				tmp_color.set(i, 0);

				int sum = 0;

				for (int j = 0; j < tmp_color.size(); ++j)
					if (tmp_color.get(j) == 1 && can_remove(adjacency_list.get(j), tmp_color) == 1)
						++sum;

				if (sum > max) {
					max = sum;
					a = i;
				}
			}
		}

		// Index of the vertex
		return a;
	}

	// Method 5
	// To check whether a vertex can be removed or not
	public static int can_remove(Vector<Integer> adj_list, Vector<Integer> color)
	{
		int check = 1;

		for (int i = 0; i < adj_list.size(); ++i)
			if (color.get(adj_list.get(i)) == 0)
				check = 0;

		return check;
	}
}

// Class 2
// Main class
public class GFG {

	// Main driver method
	public static void main(String[] args) throws Exception
	{
		// inputting the graph and forming it's adjacency
		// list.

		// Display message for better readability
		System.out.println("The number of vertices in the graph is taken as 4");

		// Custom input is taken here
		int n = 4;

		// Creating a vector object for adjacency matrix.
		Vector<Vector<Integer> > adjacency_matrix = new Vector<Vector<Integer> >(n, (n));

		// Input matrix is
		// 0111
		// 1011
		// 1101
		// 1110

		// Nested for loops for iterations
		for (int i = 0; i < n; ++i) {
			Vector<Integer> adj = new Vector<Integer>(n);
			for (int j = 0; j < n; ++j)
				if (i == j)
					adj.add(0);
				else
					adj.add(1);
			adjacency_matrix.add(adj);
		}

		// Creating a vector object for adjacency list
		Vector<Vector<Integer> > adjacency_list
			= new Vector<Vector<Integer> >();

		// Nested for loops for iterations
		for (int i = 0; i < n; ++i) {
			Vector<Integer> adj_list = new Vector<Integer>();
			for (int j = 0; j < n; ++j) {
				if (adjacency_matrix.get(i).get(j) == 1)
					adj_list.add(j);
			}
			adjacency_list.add(adj_list);
		}

		// Display message only for
		// taking the minimum size of the set required.
		System.out.println("The minimum size of the set required is taken as 2");

		// Declaring and initializing variable with
		// least size of the set required
		int x = 2;

		// Complement of the size
		int y = n - x;
		int found = 0;
		int size = 0;
		int min = n + 1;

		// Creating a set found vector to
		// store all the possible set
		Vector<Vector<Integer> > set_found = new Vector<Vector<Integer> >();

		// Display message
		System.out.println("Searching for the set");

		for (int i = 0; i < n; ++i) {

			// If set is found
			if (found == 1)

				// Hault the further execution of Program
				break;

			// Cover vector to have the state of all the
			// vertices initially
			Vector<Integer> color = new Vector<Integer>(n);
			for (int j = 0; j < n; ++j)
				color.add(1);

			// Starting by putting the ith node in set
			color.set(i, 0);

			// then finding all the nodes to be pushed
			GFGUTIL.Util(adjacency_list, color);

			// Finding the number of those which cannot be
			// pushed in set
			size = GFGUTIL.Util3(color);
			if (size < min)
				min = size;

			// If the number of elements in set are more or
			// equal
			if (size <= y) {

				// Print and display the size
				System.out.println("Independent set of size " + (n - size) + "found");

				for (int j = 0; j < n; ++j)

					if (color.get(j) == 0)
						System.out.print(j + 1 + " ");
				System.out.println();
				set_found.add(color);

				// Set flaf to 1
				found = 1;

				// Hault the further execution of Program
				break;
			}

			// If sufficient nodes are not found then
			// we call util2 function

			for (int j = 0; j < x; ++j)
				GFGUTIL.Util2(adjacency_list, color, j);

			size = GFGUTIL.Util3(color);
			if (size < min)
				min = size;
			System.out.println("Independent set of size " + (n - size) + "found");
			for (int j = 0; j < n; ++j)
				if (color.get(j) == 0)
					System.out.print(j + 1 + " ");
			System.out.println();
			set_found.add(color);
			if (size <= y) {
				found = 1;
				break;
			}
		}

		int r = set_found.size();

		// Now searching pairwise and
		// repeating same procedure as above discussed
		for (int a = 0; a < r; ++a) {
			if (found == 1)
				break;

			for (int b = a + 1; b < r; ++b) {
				if (found == 1)
					break;

				Vector<Integer> color = new Vector<Integer>(n);

				for (int j = 0; j < n; ++j)
					color.add(1);
				for (int c = 0; c < n; ++c)
					if (set_found.get(a).get(c) == 0
						&& set_found.get(b).get(c) == 0)
						color.set(c, 0);

				GFGUTIL.Util(adjacency_list, color);
				size = GFGUTIL.Util3(color);

				if (size < min)
					min = size;
				if (size <= y) {
					System.out.println("Independent set of size" + (n - size));

					for (int j = 0; j < n; ++j)
						if (color.get(j) == 0)

							System.out.print(j + 1 + " ");

					System.out.println();
					found = 1;
					break;
				}

				for (int j = 0; j < y; ++j)
					GFGUTIL.Util2(adjacency_list, color, j);
				size = GFGUTIL.Util3(color);
				if (size < min)
					min = size;

				System.out.println("Independent set of size " + (n - size) + "found");

				for (int j = 0; j < n; ++j)
					if (color.get(j) == 0)
						System.out.print(j + 1 + " ");

				System.out.println();
				if (size <= y) {
					found = 1;
					break;
				}
			}
		}

		// If found
		if (found == 1)

			// Display command
			System.out.println("Found the set of given least possible size");
		else

			// Display command
			System.out.println("Couldn't find the set of least size given");
	}
}
