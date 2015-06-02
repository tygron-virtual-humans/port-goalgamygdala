package vh3.goalgamygdala;

import agent.Agent;
import agent.AgentInternalState;
import data.Emotion;
import data.Goal;
import jpl.Compound;
import krTools.language.Term;
import vh3.goalgamygdala.parser.ITermParser;
import vh3.goalgamygdala.parser.PrologTermParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wouter on 28/05/15.
 */
public class GoalGamygdalaAgent {

    private Agent agent;
    private ITermParser termParser;

    public GoalGamygdalaAgent(Agent agent)
    {
        this.agent = agent;
        this.termParser = PrologTermParser.getInstance();
    }

    public void adoptGoal(List<Term> terms){
        String name = termParser.parseString(terms.get(0));
        double utility = termParser.parseDouble(terms.get(1));
        boolean isMaintenanceGoal = termParser.parseBoolean(terms.get(2));

        agent.addGoal(new Goal(name,utility,isMaintenanceGoal));
    }

    public void dropGoal(List<Term> terms)
    {
        String name = termParser.parseString(terms.get(0));

        agent.removeGoal(agent.getGoalByName(name));
    }

    public List<Compound> getEmotions() {
        AgentInternalState state = agent.getEmotionalState(agent.gain);

        List<Compound> res = new ArrayList<Compound>();


        Iterator<Emotion> emotions = state.iterator();
        while(emotions.hasNext()){
            Emotion emotion = emotions.next();
            res.add(new Compound("emotion",new jpl.Term[]{
                    new jpl.Atom(emotion.name),
                    new jpl.Float(emotion.intensity)
            }));
        }

        return res;
    }

}
