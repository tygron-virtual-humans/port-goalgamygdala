package vh3.goalgamygdala.parser;

import krTools.language.Term;

import java.util.List;

/**
 * Created by wouter on 28/05/15.
 */
public interface ITermParser {

    double parseDouble(Term term);

    int parseInt(Term term);

    String parseString(Term term);

    boolean parseBoolean(Term term);

    List<Object> parseList(Term term);

    List<Integer> parseIntList(Term term);

    List<Double> parseDoubleList(Term term);

    List<String> parseStringList(Term term);

    List<Boolean> parseBooleanList(Term term);
}
