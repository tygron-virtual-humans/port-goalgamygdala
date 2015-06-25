package vh3.goalgamygdala.agent;

import agent.Agent;
import agent.AgentInternalState;
import data.Emotion;
import jpl.Compound;
import krTools.language.Term;
import vh3.goalgamygdala.parser.TermParser;
import vh3.goalgamygdala.parser.PrologTermParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wouter on 28/05/15.
 * The interface towards agent-specific gamygdala actions such as drop and get emotions.
 */
public class GoalGamygdalaAgent {

    private final Agent agent;
    private final TermParser termParser;

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
            double intensity = Math.ceil(1000000000d*emotion.getIntensity())/1000000000d;
            res.add(new Compound("emotion",new jpl.Term[]{
                    new jpl.Atom(emotion.getName()),
                    new jpl.Float(intensity)
            }));
        }

        return res;
    }

}
