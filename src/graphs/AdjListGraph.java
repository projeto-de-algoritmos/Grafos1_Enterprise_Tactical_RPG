package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import graphs.exceptions.GraphException;
import graphs.exceptions.InexistentNode;
import graphs.exceptions.NodeAlreadyExists;

public class AdjListGraph<NodeT> {
	private Integer nodesAmount = 0;
	private Integer edgesAmount = 0;

	private Map<NodeT, List<NodeT>> adj = new HashMap<NodeT, List<NodeT>>();

	public void addNodes(List<NodeT> nodeDatas) throws GraphException {
		for (NodeT nodeData : nodeDatas) {
			addNode(nodeData);
		}
	}

	public void addNode(NodeT nodeData) throws GraphException {
		if (adj.containsKey(nodeData)) {
			throw new NodeAlreadyExists("Node " + nodeData + " already exists");
		}

		adj.put(nodeData, new LinkedList<NodeT>());
		nodesAmount++;
	}

	public List<NodeT> getNodes() {
		List<NodeT> nodes = new ArrayList<NodeT>();

		for (NodeT node : adj.keySet()) {
			nodes.add(node);
		}

		return nodes;
	}

	public void addEdge(UndirectedEdge<NodeT> edge) throws GraphException {
		if (!adj.containsKey(edge.getV())) {
			throw new InexistentNode("Node " + edge.getV().toString() + " does not exist");
		}

		if (!adj.containsKey(edge.getW())) {
			throw new InexistentNode("Node " + edge.getW().toString() + " does not exist");
		}

		adj.get(edge.getV()).add(edge.getW());
		adj.get(edge.getW()).add(edge.getV());

		edgesAmount++;
	}

	public void addEdges(List<UndirectedEdge<NodeT>> edges) throws GraphException {
		for (UndirectedEdge<NodeT> edge : edges) {
			addEdge(edge);
		}
	}

	public List<UndirectedEdge<NodeT>> getEdges() {
		ArrayList<UndirectedEdge<NodeT>> edges = new ArrayList<UndirectedEdge<NodeT>>();

		for (NodeT v : adj.keySet()) {
			for (NodeT w : adj.get(v)) {
				edges.add(new UndirectedEdge<NodeT>(v, w));
			}
		}

		return edges;
	}

	public String getDotFile() {
		List<UndirectedEdge<NodeT>> edges = getEdges();

		StringBuilder temp = new StringBuilder();

		temp.append("strict graph G {\n");

		for (UndirectedEdge<NodeT> edge : edges) {
			temp.append("\t" + edge.getV() + " -- " + edge.getW() + ";\n");
		}

		temp.append("}");

		return temp.toString();
	}

	public void printDotFile() {
		System.out.println(getDotFile());
	}

	public AdjListGraph<NodeT> dfs() {
		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListGraph<NodeT> tree = new AdjListGraph<NodeT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Stack<NodeT> nodeStack = new Stack<NodeT>();

		for (NodeT node : nodeVisited.keySet()) {
			if (!nodeVisited.get(node)) {
				nodeStack.push(node);
				nodeVisited.put(node, true);

				while (!nodeStack.empty()) {
					NodeT w = nodeStack.pop();

					for (NodeT v : adj.get(w)) {
						if (!nodeVisited.get(v)) {
							try {
								tree.addEdge(new UndirectedEdge<NodeT>(v, w));
							} catch (GraphException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							nodeVisited.put(v, true);
							nodeStack.push(v);
						}
					}
				}
			}
		}

		return tree;
	}

	public AdjListGraph<NodeT> dfs(NodeT startNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListGraph<NodeT> tree = new AdjListGraph<NodeT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Stack<NodeT> nodeStack = new Stack<NodeT>();

		nodeStack.push(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeStack.empty()) {
			NodeT w = nodeStack.pop();

			for (NodeT v : adj.get(w)) {
				if (!nodeVisited.get(v)) {
					try {
						tree.addEdge(new UndirectedEdge<NodeT>(v, w));
					} catch (GraphException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					nodeVisited.put(v, true);
					nodeStack.push(v);
				}
			}
		}

		return tree;
	}

	public AdjListGraph<NodeT> dfs(NodeT startNode, NodeT endNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		if (!adj.containsKey(endNode)) {
			throw new InexistentNode("Node " + endNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListGraph<NodeT> tree = new AdjListGraph<NodeT>();
		Boolean foundEndNode = false;

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Stack<NodeT> nodeStack = new Stack<NodeT>();

		nodeStack.push(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeStack.empty() && !foundEndNode) {
			NodeT w = nodeStack.pop();

			for (NodeT v : adj.get(w)) {
				if (!nodeVisited.get(v)) {
					try {
						tree.addEdge(new UndirectedEdge<NodeT>(v, w));
					} catch (GraphException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					nodeVisited.put(v, true);
					nodeStack.push(v);

					if (v.equals(endNode)) {
						foundEndNode = true;
						break;
					}
				}
			}
		}

		return tree;
	}

	public AdjListGraph<NodeT> bfs() {
		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListGraph<NodeT> tree = new AdjListGraph<NodeT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		for (NodeT node : nodeVisited.keySet()) {
			if (!nodeVisited.get(node)) {
				nodeQueue.add(node);
				nodeVisited.put(node, true);

				while (!nodeQueue.isEmpty()) {
					NodeT w = nodeQueue.remove();

					for (NodeT v : adj.get(w)) {
						if (!nodeVisited.get(v)) {
							try {
								tree.addEdge(new UndirectedEdge<NodeT>(v, w));
							} catch (GraphException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							nodeVisited.put(v, true);
							nodeQueue.add(v);
						}
					}
				}
			}
		}

		return tree;
	}

	public AdjListGraph<NodeT> bfs(NodeT startNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListGraph<NodeT> tree = new AdjListGraph<NodeT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		nodeQueue.add(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeQueue.isEmpty()) {
			NodeT w = nodeQueue.remove();

			for (NodeT v : adj.get(w)) {
				if (!nodeVisited.get(v)) {
					try {
						tree.addEdge(new UndirectedEdge<NodeT>(v, w));
					} catch (GraphException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					nodeVisited.put(v, true);
					nodeQueue.add(v);
				}
			}
		}

		return tree;
	}
	
	public AdjListGraph<NodeT> bfs(NodeT startNode, NodeT endNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		if (!adj.containsKey(endNode)) {
			throw new InexistentNode("Node " + endNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListGraph<NodeT> tree = new AdjListGraph<NodeT>();
		Boolean foundEndNode = false;

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		nodeQueue.add(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeQueue.isEmpty() && !foundEndNode) {
			NodeT w = nodeQueue.remove();

			for (NodeT v : adj.get(w)) {
				if (!nodeVisited.get(v)) {
					try {
						tree.addEdge(new UndirectedEdge<NodeT>(v, w));
					} catch (GraphException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					nodeVisited.put(v, true);
					nodeQueue.add(v);
					
					if (v.equals(endNode)) {
						foundEndNode = true;
						break;
					}
				}
			}
		}

		return tree;
	}

	public AdjListGraph<NodeT> getAnyCycle() {
		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListGraph<NodeT> tree = new AdjListGraph<NodeT>();
		Map<NodeT, NodeT> parents = new HashMap<NodeT, NodeT>();
		NodeT firstNode = null;
		Boolean first = true;

		// In the beginning all nodes are their own parents
		for (NodeT node : getNodes()) {
			parents.put(node, node);
		}

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		for (NodeT node : nodeVisited.keySet()) {
			if (!nodeVisited.get(node)) {
				if (first) {
					first = false;
					firstNode = node;
				}

				nodeQueue.add(node);
				nodeVisited.put(node, true);

				while (!nodeQueue.isEmpty()) {
					NodeT w = nodeQueue.remove();

					for (NodeT v : adj.get(w)) {
						if (!nodeVisited.get(v)) {
							try {
								tree.addEdge(new UndirectedEdge<NodeT>(v, w));
							} catch (GraphException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							parents.put(v, w);

							nodeVisited.put(v, true);
							nodeQueue.add(v);
						} else if (!parents.get(w).equals(v)) {
							System.out.println("Cycle detected!");
							System.out.println("Found " + v + " (already visited) " + " on adjacency of " + w);
							System.out.println(v + " has parent " + parents.get(v));
							System.out.println(w + " has parent " + parents.get(w));

							List<UndirectedEdge<NodeT>> edgesV = new ArrayList<UndirectedEdge<NodeT>>();
							List<UndirectedEdge<NodeT>> edgesW = new ArrayList<UndirectedEdge<NodeT>>();
							List<UndirectedEdge<NodeT>> edgesCycles = new ArrayList<UndirectedEdge<NodeT>>();

							List<UndirectedEdge<NodeT>> largest = null;
							List<UndirectedEdge<NodeT>> smallest = null;

							System.out.println("DFS tree");
							tree.printDotFile();

							try {
								edgesV = tree.bfs(firstNode, v).getEdges();
								edgesW = tree.bfs(firstNode, w).getEdges();
							} catch (GraphException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// add the current connection to one of the trees
							edgesV.add(new UndirectedEdge<NodeT>(w, v));
							edgesV.add(new UndirectedEdge<NodeT>(v, w));

							System.out.println("edgesV");
							for (UndirectedEdge<NodeT> e : edgesV) {
								System.out.println(e.getV() + " " + e.getW());
							}

							System.out.println("edgesW");
							for (UndirectedEdge<NodeT> e : edgesW) {
								System.out.println(e.getV() + " " + e.getW());
							}

							if (edgesV.size() >= edgesW.size()) {
								largest = edgesV;
								smallest = edgesW;
							} else {
								largest = edgesW;
								smallest = edgesV;
							}

							largest = new ArrayList<UndirectedEdge<NodeT>>(edgesV);
							smallest = new ArrayList<UndirectedEdge<NodeT>>(edgesW);

							// Remove the equal edges
							largest.removeAll(edgesW);
							smallest.removeAll(edgesV);

							edgesCycles.addAll(largest);
							edgesCycles.addAll(smallest);

							System.out.println("paths xor");
							for (UndirectedEdge<NodeT> e : edgesCycles) {
								System.out.println(e.getV() + " " + e.getW());
							}

							AdjListGraph<NodeT> cycle = new AdjListGraph<NodeT>();

							try {
								cycle.addNodes(getNodes());
								cycle.addEdges(edgesCycles);
							} catch (GraphException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

//							System.out.println(v + " with parent " + parents.get(v) + " on " + w + " list already visited");
//							try {
//								tree.addEdge(new Edge<NodeT>(v, w));
//							} catch (GraphException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}

							return cycle;
						}
					}
				}
			}
		}

		return null;
	}

	public Integer getNodesAmount() {
		return nodesAmount;
	}

	public Integer getEdgesAmount() {
		return edgesAmount;
	}
}
