package vh3.goalgamygdala.parser;

import krTools.language.Term;
import swiprolog.language.PrologTerm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wouter on 28/05/15.
 */
public class PrologTermParser implements ITermParser {
    private static PrologTermParser ourInstance = new PrologTermParser();

    public static PrologTermParser getInstance() {
        return ourInstance;
    }

    private PrologTermParser() {
    }

    private jpl.Term getTerm(Term term){
        return ((PrologTerm)term).getTerm();
    }

    @Override
    public double parseDouble(Term term){
        jpl.Term thisTerm = getTerm(term);

        return parseJplDouble(thisTerm);
    }

    private double parseJplDouble(jpl.Term jplTerm){
        if(!(jplTerm.isFloat() || jplTerm.isInteger()))
            throw new IllegalArgumentException();

        return jplTerm.doubleValue();
    }

    @Override
    public int parseInt(Term term) {
        jpl.Term thisTerm = getTerm(term);

        return parseJplInt(thisTerm);
    }

    private int parseJplInt(jpl.Term jplTerm){
        if(!(jplTerm.isInteger()))
            throw new IllegalArgumentException();

        return jplTerm.intValue();
    }

    @Override
    public String parseString(Term term) {
        jpl.Term thisTerm = getTerm(term);

        return parseJplString(thisTerm);
    }

    private String parseJplString(jpl.Term jplTerm){
        if(!(jplTerm.isAtom()))
            throw new IllegalArgumentException();

        jpl.Atom thisAtom = (jpl.Atom) jplTerm;

        return thisAtom.name();
    }

    @Override
    public boolean parseBoolean(Term term) {
        jpl.Term thisTerm = getTerm(term);

        return parseJplBoolean(thisTerm);
    }

    private Boolean parseJplBoolean(jpl.Term jplTerm){
        if(jplTerm.isJTrue())
            return true;
        if(jplTerm.isJFalse())
            return false;

        throw new IllegalArgumentException();
    }

    @Override
    public List<Object> parseList(Term term) {
        jpl.Term jplTerm = getTerm(term);

        return parseJplList(jplTerm);
    }

    private List<Object> parseJplList(jpl.Term jplTerm){
        if(!(jplTerm.isCompound()))
            throw new IllegalArgumentException();

        jpl.Compound thisCompound = (jpl.Compound) jplTerm;

        List<Object> res = new ArrayList<Object>();
        while(!thisCompound.arg(1).name().equals("[]")){
            res.add(parseJplAny(thisCompound.arg(0)));
            thisCompound = (jpl.Compound)thisCompound.arg(1);
        }
        res.add(parseJplAny(thisCompound.arg(0)));

        return res;
    }

    private Object parseJplAny(jpl.Term jplTerm){
        if(jplTerm.isFloat()){
            return jplTerm.doubleValue();
        }

        if(jplTerm.isInteger()){
            return jplTerm.intValue();
        }

        if(jplTerm.isAtom()){
            String name = jplTerm.name();
            if(name.equalsIgnoreCase("true"))
                return true;
            if(name.equalsIgnoreCase("false"))
                return false;
            return name;
        }

        if(jplTerm.isCompound()){
            parseJplList(jplTerm);
        }

        throw new IllegalArgumentException();
    }

    @Override
    public List<Integer> parseIntList(Term term) {
        jpl.Term jplTerm = getTerm(term);

        if(!(jplTerm.isCompound()))
            throw new IllegalArgumentException();

        jpl.Compound thisCompound = (jpl.Compound) jplTerm;

        List<Integer> res = new ArrayList<Integer>();

        while(!thisCompound.arg(1).name().equals("[]")){
            res.add(parseJplInt(thisCompound.arg(0)));
            thisCompound = (jpl.Compound)thisCompound.arg(1);
        }
        res.add(parseJplInt(thisCompound.arg(0)));

        return res;
    }

    @Override
    public List<Double> parseDoubleList(Term term) {
        jpl.Term jplTerm = getTerm(term);

        if(!(jplTerm.isCompound()))
            throw new IllegalArgumentException();

        jpl.Compound thisCompound = (jpl.Compound) jplTerm;

        List<Double> res = new ArrayList<Double>();

        while(!thisCompound.arg(1).name().equals("[]")){
            res.add(parseJplDouble(thisCompound.arg(0)));
            thisCompound = (jpl.Compound)thisCompound.arg(1);
        }
        res.add(parseJplDouble(thisCompound.arg(0)));

        return res;
    }

    @Override
    public List<String> parseStringList(Term term) {
        jpl.Term jplTerm = getTerm(term);

        if(!(jplTerm.isCompound()))
            throw new IllegalArgumentException();

        jpl.Compound thisCompound = (jpl.Compound) jplTerm;

        List<String> res = new ArrayList<String>();

        while(!thisCompound.arg(1).name().equals("[]")){
            res.add(parseJplString(thisCompound.arg(0)));
            thisCompound = (jpl.Compound)thisCompound.arg(1);
        }
        res.add(parseJplString(thisCompound.arg(0)));

        return res;
    }

    @Override
    public List<Boolean> parseBooleanList(Term term) {
        jpl.Term jplTerm = getTerm(term);

        if(!(jplTerm.isCompound()))
            throw new IllegalArgumentException();

        jpl.Compound thisCompound = (jpl.Compound) jplTerm;

        List<Boolean> res = new ArrayList<Boolean>();

        while(!thisCompound.arg(1).name().equals("[]")){
            res.add(parseJplBoolean(thisCompound.arg(0)));
            thisCompound = (jpl.Compound)thisCompound.arg(1);
        }
        res.add(parseJplBoolean(thisCompound.arg(0)));

        return res;
    }
}
