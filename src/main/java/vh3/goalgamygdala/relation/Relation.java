package vh3.goalgamygdala.relation;

/**
 * Created by wouter on 17/06/15.
 * Defines a Relation between two agents, so that is can be stored
 * and applied later, when the target agent comes to exist.
 */
class Relation {
    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public double getValue() {
        return value;
    }

    private final String source;
    private final String dest;
    private final double value;

    public Relation(String source, String dest, double value) {
        this.source = source;
        this.dest = dest;
        this.value = value;
    }
}
