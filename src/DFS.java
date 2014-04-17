import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DFS {

	public static int SIZE = 10;
	int[][] edge = new int[SIZE][SIZE];
	boolean isVisited[] = new boolean[SIZE];

	int dfsArray[] = new int[SIZE];
	int ctr;

	public static void main(String[] args) {
		File fin;
		fin = new File(args[0]);
		DFS d1 = new DFS();
		Scanner sc;
		try {
			sc = new Scanner(fin);
			d1.populateEdges(sc);
		} catch (FileNotFoundException e) {
			System.out
					.println("Unable to open file to read adjacency matrix. Does it exist?");
			e.printStackTrace();
		}

		d1.printEdges();
		d1.doDFS(0);
		d1.printDFS();

	}

	/**
	 * populate the adjacency matrix with edges from file whose path is supplied
	 * as args0
	 */
	void populateEdges(Scanner sc) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				edge[i][j] = sc.nextInt();
			}
		}
	}

	/**
	 * Display adjacency matrix
	 */
	void printEdges() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(edge[i][j] + " ");
			}
			System.out.println();
		}
	}

	void doDFS(int currNode) {
		dfsArray[ctr] = currNode;
		isVisited[currNode] = true;
		ctr++;
		for (int i = 0; i < SIZE; i++) {
			if (edge[currNode][i] != 0) {
				doDFS(i);
			}
		}
	}

	void printDFS() {
		for (int i = 0; i < SIZE; i++) {
			System.out.println(dfsArray[i]);
		}
	}
}