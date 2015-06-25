package vh3.goalgamygdala.agent;

import agent.Agent;
import gamygdala.Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wouter on 18/06/15.
 * The manager of the Agents. In a separate class because multiple classes need to
 * access this information.
 */
public class AgentManager {

    private static AgentManager _instance;

    public static AgentManager getInstance() {
        if(_instance == null){
            _instance = new AgentManager();
        }
        return _instance;
    }


    private final Map<String, Agent> agents;
    private final Map<String, GoalGamygdalaAgent> ggAgents;
    private final Engine engine;

    private AgentManager(){
        agents = new HashMap<String,Agent>();
        ggAgents = new HashMap<String,GoalGamygdalaAgent>();
        engine = Engine.getInstance();
    }

    /**
     * Create a new agent and its interface, and add them to
     * their respective maps.
     * @param name the name of the agent
     */
    public void createAgent(String name){
        Agent agent = engine.createAgent(name);
        agents.put(name,agent);
        ggAgents.put(name,new GoalGamygdalaAgent(agent));
    }

    public Agent getAgent(String name){
        return agents.get(name);
    }

    public GoalGamygdalaAgent getGoalGamygdalaAgent(String name){
        return ggAgents.get(name);
    }
}
