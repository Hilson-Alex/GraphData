package graph;

import lombok.*;

/**
 * An edge connecting two vertex by the key
 */
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "bidirectional")
public class Edge<K> {

    /**
     * The connected vertices
     */
    @Getter @Setter @NonNull
    private K tail, head;

    /**
     * flag to say if an edge is bidirectional
     */
    @Getter @Setter
    private boolean bidirectional = true;

    public static <K> Edge<K> directional (K tail, K head) {
        return new Edge<>(tail, head, false);
    }

    /**
     * verify tail key or any key if it's didirectional
     * @param key the key to be verified
     * @return true if tits the tail key
     */
    public boolean hasTail (Object key){
        return getTail().equals(key) || ( isBidirectional() && getHead().equals(key) );
    }

    /**
     * verify head key or any key if it's didirectional
     * @param key the key to be verified
     * @return true if tits the head key
     */
    public boolean hasHead (Object key){
        return getHead().equals(key) || ( isBidirectional() && getTail().equals(key) );
    }

    // Override Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge<?> edge = (Edge<?>) o;
        if (!edge.getTail().getClass().equals(getTail().getClass())) return false;
        if (bidirectional != edge.bidirectional) return false;
        if (!hasTail(edge.getTail())) return false;
        return hasHead(edge.getHead());
    }

    @Override
    public int hashCode() {
        int result = getTail().hashCode();
        result = 31 * (result + getHead().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String edge = (isBidirectional()) ? " <-> " : " -> ";
        return tail + edge + head;
    }
}
