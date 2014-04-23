import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class FF {

	public static int SIZE = 6;
	int[][] edge = new int[SIZE][SIZE];
	int[][] flow = new int[SIZE][SIZE];
	boolean isVisited[] = new boolean[SIZE];

	int[] augPath = new int[SIZE];
	int ctr;
	int src = 0;
	int sink = 5;
	int netFlow = 0;

	public static void main(String[] args) {
		File fin;
		fin = new File(args[0]);
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
			Arrays.fill(d1.isVisited, false);
		}
		d1.printMatrix();

	}

	/**
	 * populate the adjacency matrix with edges from file whose path is supplied
	 * to Scanner
	 */
	void populateEdges(Scanner sc) {
		int from, to, cap;
		src = 0;
		SIZE = sc.nextInt();
		sink = SIZE - 1;
		sc.nextInt();
		try {
			while (true) {
				from = sc.nextInt();
				to = sc.nextInt();
				cap = sc.nextInt();
				edge[from-1][to-1] = cap;
			}
		} catch (Exception e) {
			return;
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

	/**
	 * Prints the flow matrix, residual graph matrix and original matrix
	 */
	void printMatrix() {
		for (int i = 0; i < SIZE; i++) {
			System.out.println(augPath[i]);
		}
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

		System.out.println("\nNet Flow = " + netFlow);

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

}