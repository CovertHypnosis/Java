package twentyeight.chapter;

import java.util.*;

public abstract class AbstractGraph<V> implements Graph<V> {
	protected List<V> vertices = new ArrayList<>(); // Store vertices
	protected List<List<Edge>> neighbors = new ArrayList<>(); // Adjacency lists
	
	/** Construct an empty graph */
	protected AbstractGraph() {}
	
	/** Construct a graph from vertices and edges stored in arrays */
	protected AbstractGraph(V[] vertices, int[][] edges) {
		for (int i = 0; i < vertices.length; i++)
			addVertex(vertices[i]);
		
		createAdjacencyLists(edges, vertices.length);
	}
	
	/** Construct a graph from vertices and edges stored in List */
	protected AbstractGraph(List<V> vertices, List<Edge> edges) {
		for (int i = 0; i < vertices.size(); i++)
			addVertex(vertices.get(i));
		
		createAdjacencyLists(edges, vertices.size());
	}
	
	/** Construct a graph for integer vertices 0, 1, 2 and edge list */
	protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
		for (int i = 0; i < numberOfVertices; i++)
			addVertex((V)(new Integer(i)));
		
		createAdjacencyLists(edges, numberOfVertices);
	}
	
	/** Construct a graph from integer vertices 0, 1, and edge array */
	protected AbstractGraph(int[][] edges, int numberOfVertices) {
		for (int i = 0; i < numberOfVertices; i++)
			addVertex((V)(new Integer(i)));
		
		createAdjacencyLists(edges, numberOfVertices);
	}
	
	/** Create adjacency lists for each vertex */
	private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
		for (int i = 0; i < edges.length; i++)
			addEdge(edges[i][0], edges[i][1]);
	}
	
	/** Create adjacency lists for each vertex */
	private void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
		for (Edge edge: edges)
			addEdge(edge.u, edge.v);
	}
	
	@Override /** Return the number of vertices in the graph */
	public int getSize() {
		return vertices.size();
	}
	
	@Override /** Return the vertices in the graph */
	public List<V> getVertices() {
		return vertices;
	}
	
	@Override /** Return the object for the specified vertex */
	public V getVertex(int index) {
		return vertices.get(index);
	}
	
	@Override /** Return the index for the specified vertex object */
	public int getIndex(V v) {
		return vertices.indexOf(v);
	}
	
	@Override /** Return the neighbors of the specified vertex */
	public List<Integer> getNeighbors(int index) {
		List<Integer> result = new ArrayList<>();
		for (Edge e: neighbors.get(index))
			result.add(e.v);
		
		return result;
	}
	
	@Override /** Return the degree for a specified vertex */
	public int getDegree(int v) {
		return neighbors.get(v).size();
	}
	
	@Override
	public void printEdges() {
		for (int u = 0; u < neighbors.size(); u++) {
			System.out.print(getVertex(u) + " (" + u + "): ");
			for (Edge e: neighbors.get(u)) {
				System.out.print("(" + getVertex(e.u) + ", " + getVertex(e.v) + ") ");
			}
			System.out.println();
		}
	}
	
	@Override /** Clear the graph */
	public void clear() {
		vertices.clear();
		neighbors.clear();
	}
	
	@Override /** Add a vertex to the graph */
	public boolean addVertex(V vertex) {
		if (!vertices.contains(vertex)) {
			vertices.add(vertex);
			neighbors.add(new ArrayList<Edge>());
			return true;
		} else
			return false;
	}
	
	/** Add an edge to the graph */
	protected boolean addEdge(Edge e) {
		if (e.u < 0 || e.u > getSize() - 1)
			throw new IllegalArgumentException("No such index: " + e.u);
		
		if (e.v < 0 || e.v > getSize() - 1)
			throw new IllegalArgumentException("No such index: " + e.v);
		
		if (!neighbors.get(e.u).contains(e)) {
			neighbors.get(e.u).add(e);
			return true;
		} else
			return false;
	}
	
	@Override /** Add an edge to the graph */
	public boolean addEdge(int u, int v) {
		return addEdge(new Edge(u, v));
	}
	
	/** Edge inner class inside the AbstractGraph class */
	public static class Edge {
		public int u; // Starting vertex of the edge
		public int v; // Ending vertex of the edge
		
		
		public Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}
		
		@Override
		public boolean equals(Object o) {
			return u == ((Edge)o).u && v == ((Edge)o).v;
		}
	}
	
	
	@Override /** Obtain a DFS tree starting from vertex v */
	public Tree dfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1;
		
		boolean[] isVisited = new boolean[vertices.size()];
		
		dfs(v, parent, searchOrder, isVisited);
		
		return new Tree(v, parent, searchOrder);
	}
	
	/** Recursive method for DFS search */
	private void dfs(int u, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
		searchOrder.add(u);
		isVisited[u] = true;
		
		for (Edge e: neighbors.get(u)) {
			if (!isVisited[e.v]) {
				parent[e.v] = u;
				dfs(e.v, parent, searchOrder, isVisited);
			}
		}
	}
	
	@Override /** Starting bfs search from vertex v */
	public Tree bfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1;
		
		LinkedList<Integer> queue = new LinkedList<>();
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v);
		isVisited[v] = true;
		
		while (!queue.isEmpty()) {
			int u = queue.poll();
			searchOrder.add(u);
			for (Edge e: neighbors.get(u)) {
				if (!isVisited[e.v]) {
					queue.offer(e.v);
					parent[e.v] = u;
					isVisited[e.v] = true;
				}
			}
		}
		
		return new Tree(v, parent, searchOrder);
	}
	
	/** Tree inner class inside the AbstractGraph class */
	public class Tree {
		private int root; // The root of the tree
		private int[] parent; // Store the parent of each vertex
		private List<Integer> searchOrder; // Store the search order
		
		/** Construct a tree with root, parent, and searchOrder */
		public Tree(int root, int[] parent, List<Integer> searchOrder) {
			this.root = root;
			this.parent = parent;
			this.searchOrder = searchOrder;
		}
		
		/** Return the root of the tree */
		public int getRoot() {
			return root;
		}
		
		/** Return the parent of vertex v */
		public int getParent(int v) {
			return parent[v];
		}
		
		/** Return an array representing search order */
		public List<Integer> getSearchOrder() {
			return searchOrder;
		}
		
		/** Return number of vertices found */
		public int getNumberOfVerticesFound() {
			return searchOrder.size();
		}
		
		/** Return the path of vertices from a vertex to the root */
		public List<V> getPath(int index) {
			ArrayList<V> path = new ArrayList<>();
			
			do {
				path.add(vertices.get(index));
				index = parent[index];
			} while (index != -1);
			
			return path;
		}
		
		/** Print a path from the root to vertex v */
		public void printPath(int index) {
			List<V> path = getPath(index);
			System.out.print("A path from " + vertices.get(root) + " to " +
					vertices.get(index) + ": ");
			for (int i = path.size() - 1; i >= 0; i--)
				System.out.print(path.get(i) + " ");
		}
		
		/** Print the whole tree */
		public void printTree() {
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for (int i = 0; i < parent.length; i++) {
				if (parent[i] != -1) {
					System.out.print("(" + vertices.get(parent[i]) + ", " +
							vertices.get(i) + ") ");
				}
			}
			
			System.out.println();
		}
	}
}
