package graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void TestIfHasTailWorksWithBidirectionalEdges () {
        Edge<String> edge = Edge.bidirectional("A", "B");
        assertTrue(edge.hasTail("A"));
        assertTrue(edge.hasTail("B"));
    }

    @Test
    public void TestIfHasTailWorksWithUnidirectionalEdges () {
        Edge<String> edge = Edge.of("A", "B", false);
        assertTrue(edge.hasTail("A"));
        assertFalse(edge.hasTail("B"));
    }
    @Test
    public void TestIfHasHeadWorksWithBidirectionalEdges () {
        Edge<String> edge = Edge.bidirectional("A", "B");
        assertTrue(edge.hasHead("A"));
        assertTrue(edge.hasHead("B"));
    }

    @Test
    public void TestIfHasHeadWorksWithUnidirectionalEdges () {
        Edge<String> edge = Edge.of("A", "B", false);
        assertFalse(edge.hasHead("A"));
        assertTrue(edge.hasHead("B"));
    }

    @Test
    public void TestIfEqualsCompareBidirectionalEdgesWithInvertedHeadAndTail () {
        Edge<String> edge1 = Edge.bidirectional("A", "B");
        Edge<String> edge2 = Edge.bidirectional("B", "A");
        assertEquals (edge1, edge2);
    }

    @Test
    public void TestIfEqualsIsFalseOnUnidirectionalEdgesWithDifferentHeads () {
        Edge<String> edge1 = Edge.of("A", "B", false);
        Edge<String> edge2 = Edge.of("B", "A", false);
        assertNotEquals(edge1, edge2);
    }

    @Test
    public void TestIfEqualsIsTrueOnUnidirectionalEdgesWithEqualHeads () {
        Edge<String> edge1 = Edge.of("A", "B", false);
        Edge<String> edge2 = Edge.of("A", "B", false);
        assertEquals (edge1, edge2);
    }

    @Test
    public void TestBidirectionalEqualsUnidirectional () {
        Edge<String> edge1 = Edge.of("A", "B", false);
        Edge<String> edge2 = Edge.bidirectional("A", "B");
        assertNotEquals (edge1, edge2);
    }

}
