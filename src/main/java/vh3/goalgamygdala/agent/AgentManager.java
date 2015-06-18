package vh3.goalgamygdala.agent;

import agent.Agent;
import gamygdala.Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wouter on 18/06/15.
 */
public class AgentManager {

    private static AgentManager _instance;

    public static AgentManager getInstance() {
        if(_instance == null){
            _instance = new AgentManager();
        }
        return _instance;
    }


    private Map<String, Agent> agents;
    private Map<String, GoalGamygdalaAgent> ggAgents;
    private Engine engine;

    private AgentManager(){
        agents = new HashMap<String,Agent>();
        ggAgents = new HashMap<String,GoalGamygdalaAgent>();
        engine = Engine.getInstance();
    }

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
