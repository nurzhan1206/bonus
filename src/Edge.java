import java.util.Objects;

public class Edge implements Comparable<Edge> {
    public final int u;
    public final int v;
    public final int weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("(%d <-> %d, w: %d)", u, v, weight);
    }

    // Реализуем equals и hashCode, чтобы их можно было корректно удалять из списков
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (u == edge.u && v == edge.v && weight == edge.weight) ||
                (u == edge.v && v == edge.u && weight == edge.weight);
    }

    @Override
    public int hashCode() {
        // Порядок не важен, поэтому используем u + v и u * v
        return Objects.hash(Math.min(u, v), Math.max(u, v), weight);
    }
}