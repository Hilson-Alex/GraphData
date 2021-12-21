package graph;

import lombok.*;

/**
 * A Graph vertex
 * @param <T> the type of the content
 */
@Data(staticConstructor = "of")
public class Vertex <K, T> {

    /**
     * The content of the vertex
     */
    @NonNull
    private T content;

    /**
     * The name of the vertex
     */
    @NonNull
    private K key;

}
