package graph;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Graph class that handles the vertices
 * and edges
 */
@NoArgsConstructor
public class Graph<K, T> {

    /**
     * vertices of this graph
     */
    @Getter
    private Map<K, T> vertices = new HashMap<>();

    /**
     * edges of this graph
     */
    private List<Edge<K>> edges = new ArrayList<>();

    /**
     * creates a graph with those vertices
     * @param vertices the vertices that are created
     *                with the graph
     */
    public Graph (Map<K, T> vertices) {
        this.vertices = vertices;
    }

    /**
     * add one or more vertices
     * @param vertex the vertices to be added
     */
    @SafeVarargs
    public final void addVertices (Vertex<K, T>... vertex) {
        vertices.putAll(Arrays.stream(vertex).collect(Collectors.toMap(Vertex::getKey, Vertex::getContent)));
    }

    /**
     * add one or more edges.
     * if the graph does not contains one or both
     * of the vertices of the edge, it is ignored
     * @param newEdges the edges to be added
     */
    @SafeVarargs
    public final void addEdges(Edge<K>... newEdges) {
        Arrays.stream(newEdges).parallel().forEach(edge -> {
            if (vertices.containsKey(edge.getHead()) && vertices.containsKey(edge.getTail())) {
                edges.add(edge);
            }
        });
    }

    /**
     * add only one edges.
     * if the graph does not contains one or both
     * of the vertices of the edge, it is ignored
     * @param edge the edge to be added
     * @return true if the edge was added, false
     *          otherwise
     */
    public final boolean addEdge(Edge<K> edge) {
        if (vertices.containsKey(edge.getHead()) && vertices.containsKey(edge.getTail())) {
            edges.add(edge);
            return true;
        }
        return false;
    }

    /**
     * get a vertex by its name
     * @param key the name of the vertex
     * @return one vertex with this name or null
     * if theres none.
     */
    public T getVertex (K key) {
        return vertices.get(key);
    }

    /**
     * get all edges connected with this vertex
     * @param key the name of the vertex
     *                    wich the edge is
     *                    connected to
     * @return all edges connected to it or an
     * empty list
     */
    public List<Edge<K>> getEdges (K key) {
        return edges.parallelStream()
                .filter(edge -> edge.hasHead(key) || edge.hasTail(key))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * get all edges connected with this vertex
     * @param vertex the vertex wich the edge is
     *               connected to
     * @return all edges connected to it or an
     * empty list
     */
    public List<Edge<K>> getEdges (Vertex<K, T> vertex) {
        T content = getVertex(vertex.getKey());
        if (content.equals(vertex.getContent())) {
            return getEdges(vertex.getKey());
        }
        return List.of();
    }

    /**
     * get an edge based on the head and tail vertices
     * if the edge is not bidirectional or an edge that
     * connects the two vertices if it is bidirectional
     * @param labelIn the tail vertex if it isn't
     *                bidirectional any vertex if it is
     * @param labelOut the head vertex if it isn't
     *                bidirectional any vertex if it is
     * @return one edge that matches the criteria or null
     * if none matches
     */
    public Edge<K> getEdge (K labelIn, K labelOut) {
        return edges.parallelStream()
                .filter(edge -> edge.hasTail(labelIn) && edge.hasHead(labelOut)).findAny().orElse(null);

    }

    /**
     * get all edges based on the head and tail vertices
     * if the edge is not bidirectional or an edge that
     * connects the two vertices if it is bidirectional
     * @param labelIn the tail vertex if it isn't
     *                bidirectional any vertex if it is
     * @param labelOut the head vertex if it isn't
     *                bidirectional any vertex if it is
     * @return all edges that matches the criteria
     */
    public List<Edge<K>> getEdges (K labelIn, K labelOut) {
        return edges.parallelStream()
                .filter(edge -> edge.hasTail(labelIn) && edge.hasHead(labelOut)).collect(Collectors.toList());

    }

    /**
     * Remove a vertex from the graph
     * @param label the vertex label
     * @return the removed vertex
     */
    public Vertex<K, T> removeVertex (K label) {
        getEdges(label).parallelStream().forEach(edges::remove);
        return Vertex.of(vertices.remove(label), label);
    }

    /**
     * Remove an bidirectional edge that mathes the parameters
     * @param tail the tail on directioned edges or any
     *             if it is bidirectional
     * @param head the head on directioned edges or any
     *             if it is bidirectional
     */
    public void removeEdge (K tail, K head) {
        Edge<K> edge = Edge.bidirectional(tail, head);
        edges.remove(edge);
    }

    /**
     * Remove an edge that mathes the parameters
     * @param tail the tail on directioned edges or any
     *             if it is bidirectional
     * @param head the head on directioned edges or any
     *             if it is bidirectional
     * @param bidirectional if the edge is bidirectional
     */
    public void removeEdge (K tail, K head, boolean bidirectional) {
        Edge<K> edge = Edge.of(tail, head, bidirectional);
        edges.remove(edge);
    }

    /**
     * @return a copy of all the edges
     */
    public List<Edge<K>> getEdges() {
        return this.edges.parallelStream().collect(Collectors.toUnmodifiableList());
    }

    /**
     * set the edges to the edges in the list that
     * contains only verices on the graph
     * @param edges the new edges
     */
    public void setEdges(List<Edge<K>> edges) {
        List<Edge<K>> list = new ArrayList<>(edges.size());
        edges.forEach(edge -> {
            if (vertices.containsKey(edge.getHead()) && vertices.containsKey(edge.getTail())) {
                list.add(edge);
            }
        });
        this.edges = list;
    }

    @Override
    public String toString() {
        return "Graph: {\n" +
                "  V: " + vertices.keySet() +
                "\n  E: " + edges.stream().map(Edge::toString).collect(Collectors.joining("\n     ")) +
                "\n}";
    }
}
