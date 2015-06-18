package vh3.goalgamygdala.relation;

/**
 * Created by wouter on 17/06/15.
 */
public class Relation {
    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public double getValue() {
        return value;
    }

    private String source, dest;
    private double value;

    public Relation(String source, String dest, double value) {
        this.source = source;
        this.dest = dest;
        this.value = value;
    }
}
