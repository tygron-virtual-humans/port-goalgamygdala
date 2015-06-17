package vh3.goalgamygdala;

import agent.Agent;
import data.Belief;
import data.Goal;
import exception.GoalCongruenceMapException;
import gamygdala.Engine;
import krTools.language.Term;
import vh3.goalgamygdala.parser.ITermParser;
import vh3.goalgamygdala.parser.PrologTermParser;

import java.util.*;

/**
 * Created by wouter on 28/05/15.
 */
public class GoalGamygdala {
    private static GoalGamygdala ourInstance = new GoalGamygdala();

    public static GoalGamygdala getInstance() {
        return ourInstance;
    }

    private Engine engine;
    private Map<String, Agent> agents;
    private Map<String, GoalGamygdalaAgent> ggAgents;
    private ITermParser termParser;

    private GoalGamygdala() {
        engine = Engine.getInstance();
        agents = new HashMap<String, Agent>();
        ggAgents = new HashMap<String, GoalGamygdalaAgent>();

        //Assume we're using prolog for now
        termParser = PrologTermParser.getInstance();
    }

    public void createAgent(String name) {
        Agent agent = engine.createAgent(name);
        agents.put(name,agent);
        ggAgents.put(name,new GoalGamygdalaAgent(agent));
    }

    public GoalGamygdalaAgent getAgentByName(String name) {
        return ggAgents.get(name);
    }

    public void appraise(String currentAgentName, List<Term> terms) throws IllegalArgumentException{
        if(terms.size() < 5) {
            throw new IllegalArgumentException();
        }

        Agent currentAgent = agents.get(currentAgentName);
        double likelihood = termParser.parseDouble(terms.get(0));
        Agent actor = agents.get(termParser.parseString(terms.get(1)));

        //Get the goals
        ArrayList<Goal> affectedGoals = new ArrayList<Goal>();

        for(String goalName : termParser.parseStringList(terms.get(2))) {
            affectedGoals.add(currentAgent.getGoalByName(goalName));
        }

        //Get the goal congruences
        //Hack until gamygdala is fixed
        ArrayList<Double> goalCongruences = new ArrayList<Double>(termParser.parseDoubleList(terms.get(3)));

        boolean isIncremental = termParser.parseBoolean(terms.get(4));

        try {
            engine.appraise(new Belief(
                    likelihood,actor,affectedGoals,goalCongruences,isIncremental),
                    agents.get(currentAgentName)
                    );
        } catch (GoalCongruenceMapException e) {
            e.printStackTrace();
        }
    }

    public void decayAll(){
        engine.decayAll();
    }

    public void adoptGoal(String agentName, List<Term> terms){
        String name = termParser.parseString(terms.get(0));
        double utility = termParser.parseDouble(terms.get(1));
        boolean isMaintenanceGoal = termParser.parseBoolean(terms.get(2));

        engine.createGoalForAgent(agents.get(agentName),name,utility,isMaintenanceGoal);
    }

    public void createRelation(String agentName, List<Term> terms){
        String otherAgentName = termParser.parseString(terms.get(0));
        double relation = termParser.parseDouble(terms.get(1));

        engine.createRelation(agents.get(agentName),agents.get(otherAgentName),relation);
    }


}
