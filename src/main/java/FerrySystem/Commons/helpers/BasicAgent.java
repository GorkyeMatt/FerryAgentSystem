package FerrySystem.Commons.helpers;

import jade.core.Agent;

/**
 * Adds Logger field
 */
public class BasicAgent extends Agent
{
    protected Logger logger;

    public BasicAgent()
    {
        this.logger = new SimpleLogger();
    }

    public Logger getLogger() {return logger;}
}
