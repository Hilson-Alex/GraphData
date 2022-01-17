# GraphData

Implementation of a Graph deata structure for storage. Until now it's only for storage, not vizualization.

## Getting Started

this graph representation works with 3 classes:
- [Graph](/src/main/java/graph/Graph.java): <br/>
  The Main class, who handles and connect the vertices;
  
- [Vertex](/src/main/java/graph/Vertex.java): <br/>
  This class serves only for encapsulation and data representation. It represents the vertices of the graph;
  
- [Edge](/src/main/java/graph/Edge.java): <br/>
  This class handles the directional or bidirecctional connection about two vertices.
  
### Using

Every graph is treated as a mixed graph. This means that every graph can contain both 
directional and bidirectional edges.

To use it you can instantiate a empty graph or pass a map with the key/value of the vertices:

```java
// Create an empty graph
Graph <String, Integer> graph = new Graph<>();

// Create a graph with two vertices
Map <String, Integer> map = new Map<>();
map.put("John", 20);
map.put("Alice", 32);
Graph <String, Integer> graph = new Graph<>(map);
```

To add vertices manually you must use the vertex class:

```java
// With the addVertices ypu can pass one vertex, many vertices or an array of vertices
graph.addVertices(Vertex.of("Phil", 46));

graph.addVertices(Vertex.of("Anne", 17), Vertex.of("Ned", 16));

Vertex[] vertices = new Vertex[] {
  // ...vertices
};
graph.addVertices(vertices);
```

Finally, to connect these vertices, you have to use the edge class and pass the vertices keys to connect:

```java
// Create an edge Phil <-> Alice
graph.addEdges(Edge.bidirectional("Phil", "Alice"));

// Create an edge Alice -> Ned
graph.addEdges(Edge.directional("Alice", "Ned"));

// You can pass a boolean to say if it is bidirectional 

// Create an edge Alice -> Anne
graph.addEdges(Edge.of("Alice", "Anne", false));
```
