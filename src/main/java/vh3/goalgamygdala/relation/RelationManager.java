package vh3.goalgamygdala.relation;

import agent.Agent;
import gamygdala.Engine;
import vh3.goalgamygdala.agent.AgentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wouter on 17/06/15.
 */
public class RelationManager {
    private static RelationManager ourInstance;

    private Engine engine;
    private AgentManager agentManager;

    public static RelationManager getInstance() {
        if(ourInstance == null){
            ourInstance = new RelationManager();
        }
        return ourInstance;
    }

    private Map<String,List<Relation>> relationsToBeProcessed;

    private RelationManager(){
        relationsToBeProcessed = new HashMap<String,List<Relation>>();
        this.engine = Engine.getInstance();
        this.agentManager = AgentManager.getInstance();
    }

    public void createRelation(String source, String target, double relation){
        Agent sourceAgent = agentManager.getAgent(source);
        Agent targetAgent = agentManager.getAgent(target);

        if(targetAgent == null){
            List<Relation> relations = relationsToBeProcessed.get(target);
            if(relations == null){
                relations = new ArrayList<Relation>();
            }
            relations.add(new Relation(source,target,relation));
            relationsToBeProcessed.put(target,relations);
        }
        else{
            engine.createRelation(agentManager.getAgent(source),agentManager.getAgent(target),relation);
        }
    }

    public void processNewRelations(String newAgent){
        List<Relation> relations = relationsToBeProcessed.get(newAgent);
        if(relations != null){
            for(Relation r : relations){
                engine.createRelation(agentManager.getAgent(r.getSource()),agentManager.getAgent(r.getDest()),r.getValue());
            }
        }
    }
}
