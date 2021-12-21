package graph;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class GraphTest {

    private static Graph<Integer, String> graph;

    @Before
    public void demoGraph() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Teste0");
        map.put(1, "Teste1");
        map.put(2, "Teste2");
        graph = new Graph<>(map);
        graph.addEdge(Edge.bidirectional(1, 2));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void TestIfCanAddVertices () {
        Vertex<Integer, String>[] vertices = new Vertex[] {
                Vertex.of("A", 10),
                Vertex.of("B", 20),
                Vertex.of("C", 30),
        };
        graph.addVertex(vertices);
        assertEquals("A", graph.getVertex(10));
        assertEquals("B", graph.getVertex(20));
        assertEquals("C", graph.getVertex(30));
    }

    @Test
    public void TestGetInvalidVertex () {
        assertNull(graph.getVertex(5));
    }

    @Test
    public void TestIfCanAddAndGetAnEdge () {
        Edge<Integer> edge = Edge.bidirectional(0, 1);
        assertTrue(graph.addEdge(edge));

        assertEquals(graph.getEdge(0, 1), edge);
    }

    @Test
    public void TestIfCanRemoveEdge () {
        graph.addEdge(Edge.of(1, 2, false));
        graph.removeEdge(1, 2);
        assertEquals(1, graph.getEdges().size());
        assertFalse(graph.getEdge(1, 2).isBidirectional());

        graph.addEdge(Edge.bidirectional(1, 2));
        graph.removeEdge(1, 2, false);
        assertEquals(1, graph.getEdges().size());
        assertTrue(graph.getEdge(1, 2).isBidirectional());
    }

    @Test
    public void TestIfCanAddMultipleEdges () {
        graph.addEdges(
                Edge.bidirectional(0, 1),
                Edge.bidirectional(1, 2),
                Edge.bidirectional(0, 2)
        );

        assertEquals(4, graph.getEdges().size());
    }

    @Test
    public void TestIfCanAddAnEdgeWithInvalidVertex () {
        Edge<Integer> edge = Edge.bidirectional(0, 100);
        assertFalse(graph.addEdge(edge));
    }

    @Test
    public void TestIfCanGetAnEdge () {
        Edge<Integer> edge = Edge.bidirectional(2, 1);
        assertEquals(edge, graph.getEdge(2, 1));
        assertEquals(edge, graph.getEdge(1, 2));
    }

    @Test
    public void TestIfCanGetMultipleEdgesBasedOnOneVertex () {
        graph.addEdge(Edge.bidirectional(2, 0));

        // Trying with a different vertex using the same key
        assertEquals(0, graph.getEdges(Vertex.of("", 2)).size());

        // Trying with an identical vertex
        assertEquals(2, graph.getEdges(Vertex.of("Teste2", 2)).size());
    }

    @Test
    public void TestIfCanGetAllEdgesConnectingTwoVertices () {
        graph.addEdge(Edge.bidirectional(2, 1));
        assertEquals(2, graph.getEdges(2, 1).size());
    }

    @Test
    public void TestIfCanGetAListEdges () {
        Edge<Integer> edge = Edge.bidirectional(0, 1);
        graph.addEdge(edge);
        assertEquals(2, graph.getEdges(1).size());
    }

    @Test
    public void TestIfCanGetAnInvalidEdge () {
        assertNull(graph.getEdge(0, 100));
    }

    @Test
    public void TestIfRemoveVertexRemoveEdges () {
        graph.removeVertex(1);
        assertEquals(0, graph.getEdges(1).size());
    }

    @Test
    public void TestIfSetEdgesFilterTheEdges () {
        List<Edge<Integer>> edges = List.of(
                // valid edges
                Edge.bidirectional(0, 1),
                Edge.bidirectional(1, 2),
                Edge.bidirectional(0, 2),

                // invalid edges
                Edge.bidirectional(10, 0),
                Edge.bidirectional(10, 20),
                Edge.bidirectional(5, 2)
        );

        graph.setEdges(edges);
        assertEquals(3, graph.getEdges().size());

    }

    @Test
    public void TestIfCanCreateEmptyGraph () {
        Graph<Integer, String> demo = new Graph<>();
        assertEquals(0, demo.getEdges().size());
        assertEquals(0, demo.getVertices().size());
    }

    @Test
    public void TestIfToStringMatchesTheVerticesAndEdges () {
        graph.addEdge(Edge.of(1, 0, false));
        String toString = """
                Graph: {
                  V: [0, 1, 2]
                  E: 1 <-> 2
                     1 -> 0
                }""";
        assertEquals(toString, graph.toString());
    }

}
