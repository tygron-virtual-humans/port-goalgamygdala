package vh3.goalgamygdala.agent;

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
 * The interface towards agent-specific gamygdala actions such as drop and get emotions.
 */
public class GoalGamygdalaAgent {

    private Agent agent;
    private ITermParser termParser;

    public GoalGamygdalaAgent(Agent agent)
    {
        this.agent = agent;
        this.termParser = PrologTermParser.getInstance();
    }

    /**
     * Drops a goal for the agent.
     * @param terms The specification of the goal in GOAL terms.
     */
    public void dropGoal(List<Term> terms)
    {
        String name = termParser.parseString(terms.get(0));
        agent.removeGoal(agent.getGoalByName(name));
    }

    /**
     * Gets the emotions of the agent.
     * @return the list of emotions in a format that GOAL understands.
     */
    public List<Compound> getEmotions() {
        AgentInternalState state = agent.getEmotionalState(agent.gain);
        List<Compound> res = new ArrayList<Compound>();

        for(Emotion emotion : state){
            res.add(new Compound("emotion",new jpl.Term[]{
                    new jpl.Atom(emotion.getName()),
                    new jpl.Float(emotion.getIntensity())
            }));
        }

        return res;
    }

}
