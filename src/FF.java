import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FF {

	public static int SIZE = 6;
	int[][] edge = new int[SIZE][SIZE];
	int[][] flow = new int[SIZE][SIZE];
	boolean isVisited[] = new boolean[SIZE];

	int[] augPath = new int[SIZE];
	int ctr;
	int src;
	int sink;

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

		d1.printEdges();
		d1.augmentPath();
		d1.printDFS();

	}

	/**
	 * populate the adjacency matrix with edges from file whose path is supplied
	 * as args0
	 */
	void populateEdges(Scanner sc) {
		// TODO read src and sink values from input file
		src = 0;
		sink = 5;
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
		augPath[ctr] = currNode;
		isVisited[currNode] = true;
		ctr++;
		for (int i = 0; i < SIZE; i++) {
			if (edge[currNode][i] != 0 && !isVisited[i]) {
				doDFS(i);
			}
		}
	}

	/**
	 * The entry method used to find out an augmenting path from source to sink
	 */
	void augmentPath() {
		int nextNode = -1;
		int bottleneck = 0;
		augPath[ctr] = src;
		ctr++;
		while (true) {
			for (int i = 1; i < SIZE; i++) {
				if (edge[0][i] != 0) {
					if (edge[0][i] > bottleneck) {
						ctr = 1;
						bottleneck = edge[0][i];
						nextNode = i;
					}
				}
			}
			if (nextNode != -1) {
				bottleneck = augmentPath(nextNode, bottleneck);
				updateFlow(bottleneck);
				nextNode = -1;
			}
			if (bottleneck == -1) { // no paths left
				break;
			}
		}
	}

	int augmentPath(int currNode, int bottleneck) {
		augPath[ctr] = currNode;
		ctr++;
		int flow = 0;
		int nextNode = -1;
		for (int i = 0; i < SIZE; i++) {
			if (edge[currNode][i] != 0) {
				if (edge[currNode][i] > flow)
					flow = edge[currNode][i];
					nextNode = i;
			}
		}
		if(flow<bottleneck) bottleneck = flow;

		if (nextNode == sink) { // reached sink
			return bottleneck;
		} else if (nextNode == -1) { // no path to sink
			return -1;
		} else
			return bottleneck = augmentPath(nextNode, bottleneck);
	}

	void updateFlow(int flowVal) {
		for(int i = 0; i<ctr;i++) {
			flow[augPath[i]][augPath[i+1]] = flowVal;
			edge[augPath[i]][augPath[i+1]] = edge[augPath[i]][augPath[i+1]] - flowVal;
			edge[augPath[i+1]][augPath[i]] = flowVal;
			}
		for(int i= 0; i<SIZE; i++) {
			for(int j= 0; j<SIZE; j++) {
				System.out.print(flow[i][j] + " ");
			}
			System.out.println();
		}
		}
		
	
	void printDFS() {
		for (int i = 0; i < SIZE; i++) {
			System.out.println(augPath[i]);
		}
		System.out.println("Printing flow array");
		
		for(int i= 0; i<SIZE; i++) {
			for(int j= 0; j<SIZE; j++) {
				System.out.print(flow[i][j]);
			}
			System.out.println();
		}
	}
}