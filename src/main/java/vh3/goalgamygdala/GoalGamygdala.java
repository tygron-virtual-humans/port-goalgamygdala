package vh3.goalgamygdala;

import agent.Agent;
import data.Belief;
import data.Goal;
import exception.GoalCongruenceMapException;
import gamygdala.Engine;
import krTools.language.Term;
import vh3.goalgamygdala.agent.AgentManager;
import vh3.goalgamygdala.agent.GoalGamygdalaAgent;
import vh3.goalgamygdala.parser.ITermParser;
import vh3.goalgamygdala.parser.PrologTermParser;
import vh3.goalgamygdala.relation.Relation;
import vh3.goalgamygdala.relation.RelationManager;

import java.util.*;

/**
 * Created by wouter on 28/05/15.
 * The interface between GOAL and Gamygdala.
 * It expects GOAL to just forward their method calls with their GOAL Terms,
 * so that GOAL does not have to know anything about the implementation of
 * GAMYGDALA itself.
 */
public class GoalGamygdala {
    private static GoalGamygdala ourInstance = new GoalGamygdala();

    public static GoalGamygdala getInstance() {
        return ourInstance;
    }

    private Engine engine;
    private ITermParser termParser;
    private AgentManager agentManager;
    private RelationManager relationManager;

    private GoalGamygdala() {
        engine = Engine.getInstance();

        //Assume we're using prolog for now
        termParser = PrologTermParser.getInstance();

        agentManager = AgentManager.getInstance();
        relationManager = RelationManager.getInstance();
    }

    /**
     * Creates a new agent. Every agent in GOAL that wants to use gamygdala is supposed to
     * use this method.
     * After creating the agent, any pending relations towards that agent are also processed.
     * @param name The name of the new agent.
     */
    public void createAgent(String name) {
        agentManager.createAgent(name);
        relationManager.processNewRelations(name);
    }

    /**
     * Gets the interface Agent for agent-specific features.
     * @param name
     * @return
     */
    public GoalGamygdalaAgent getAgentByName(String name) {
        return agentManager.getGoalGamygdalaAgent(name);
    }

    /**
     * Appraises the action specified in the terms.
     * @param currentAgentName The current agent
     * @param terms The terms that have been specified
     * @throws IllegalArgumentException
     */
    public void appraise(String currentAgentName, List<Term> terms) throws IllegalArgumentException{
        if(terms.size() < 5) {
            throw new IllegalArgumentException();
        }

        Agent currentAgent = agentManager.getAgent(currentAgentName);
        double likelihood = termParser.parseDouble(terms.get(0));
        Agent actor = agentManager.getAgent(termParser.parseString(terms.get(1)));

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
                    agentManager.getAgent(currentAgentName)
                    );
        } catch (GoalCongruenceMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decays all emotions by forwarding this call to the engine.
     */
    public void decayAll(){
        engine.decayAll();
    }

    /**
     * Make an agent adopt a goal
     * @param agentName The agent that adopts the goal
     * @param terms The specifications of the goal
     */
    public void adoptGoal(String agentName, List<Term> terms){
        String name = termParser.parseString(terms.get(0));
        double utility = termParser.parseDouble(terms.get(1));
        boolean isMaintenanceGoal = termParser.parseBoolean(terms.get(2));

        engine.createGoalForAgent(agentManager.getAgent(agentName),name,utility,isMaintenanceGoal);
    }

    /**
     * Create a relation between to agents.
     * @param agentName The agent that wants to create a relation
     * @param terms The target and intensity of the relation
     */
    public void createRelation(String agentName, List<Term> terms){
        String otherAgentName = termParser.parseString(terms.get(0));
        double relation = termParser.parseDouble(terms.get(1));

        relationManager.createRelation(agentName,otherAgentName,relation);
    }


}
