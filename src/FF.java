import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class FF {
	
	private static long startTime = System.currentTimeMillis();

	private int SIZE;
	int[][] edge;
	int[][] flow;
	boolean isVisited[];
	int[][] minCutEdge;

	int[] augPath;
	int ctr;
	int src = 0;
	int sink = 5;
	int netFlow = 0;

	public static void main(String[] args) {
		File fin;
		fin = new File("res/edges.txt");
		FF d1 = new FF();
		Scanner sc;
		try {
			sc = new Scanner(fin);
			d1.populateEdges(sc);
		} catch (FileNotFoundException e) {
			System.out
					.println("Unable to open file to read adjacency matrix. Does it exist?");
			e.printStackTrace();
		}

		// d1.printEdges();
		// d1.augmentPath();
		int sent;
		while ((sent = d1.send(d1.src, d1.sink, Integer.MAX_VALUE)) > 0) {
			d1.netFlow += sent;
		//	d1.printMatrix();
			System.out.println("Working...");
			Arrays.fill(d1.isVisited, false);
		}
	//	d1.printMatrix();
		System.out.println("\nNet Flow = " + d1.netFlow);
		d1.minCut();
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}

	/**
	 * populate the adjacency matrix with edges from file which resides in /res
	 * folder of the project and whose path is supplied to Scanner
	 */
	void populateEdges(Scanner sc) {
		int from, to, cap;
		src = 0;
		SIZE = sc.nextInt();
		sink = SIZE - 1;
		isVisited = new boolean[SIZE];
		flow = new int[SIZE][SIZE];
		edge = new int[SIZE][SIZE];
		augPath = new int[SIZE];
		minCutEdge = new int[SIZE][2];
		sc.nextInt();
		try {
			while (true) {
				from = sc.nextInt();
				to = sc.nextInt();
				cap = sc.nextInt();
				edge[from - 1][to - 1] = cap;
			}
		} catch (Exception e) { // exception is used to handle EOF.
			return;
		}
	}

	/**
	 * Prints the flow matrix, residual graph matrix and original matrix
	 */
	void printMatrix() {
		System.out.println("Printing flow array");

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(flow[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("Printing Residual Graph");
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(edge[i][j] - flow[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("Printing original Graph");
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(edge[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Sends a flow of capacity 'minn' from node 'currNode' to 'toNode'
	 * 
	 * @param currNode
	 * @param toNode
	 * @param minn
	 * @return
	 */
	int send(int currNode, int toNode, int minn) {
		isVisited[currNode] = true;

		if (currNode == toNode)
			return minn;

		for (int i = 1; i < SIZE; i++) {
			int capacity = edge[currNode][i] - flow[currNode][i];
			if (!isVisited[i] && edge[currNode][i] - flow[currNode][i] > 0) {
				int sent = send(i, toNode, min(minn, capacity));

				if (sent > 0) {
					flow[currNode][i] += sent;
					flow[i][currNode] -= sent;
					return sent;
				}
			}
		}
		return 0;
	}

	int min(int a, int b) {
		return a < b ? a : b;
	}

	void minCut() {
		ctr = 0;
		Arrays.fill(isVisited, false);
		for (int i = 0; i < SIZE; i++) {
			if (edge[src][i] > 0) {
				doDFS(src);
			}
		}
		for (int i = 0; i < ctr; i++) {
			System.out
					.println("\n" + minCutEdge[i][0] + " " + minCutEdge[i][1]);
		}
	}

	void doDFS(int currNode) {
		isVisited[currNode] = true;
		for (int i = 1; i < SIZE; i++) {
			if (edge[currNode][i] - flow[currNode][i] > 0
					&& isVisited[i] == false) {
				isVisited[i] = true;
				doDFS(i);
			} else if (edge[currNode][i] - flow[currNode][i] == 0
					&& edge[currNode][i] != 0 && isVisited[i] == false) {
				minCutEdge[ctr][0] = currNode;
				minCutEdge[ctr][1] = i;
				isVisited[i] = true;
				ctr++;
				return;
			}
		}
	}

}