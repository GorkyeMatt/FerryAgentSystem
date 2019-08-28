package FerrySystem.Commons.helpers;

import jade.core.Agent;

public class BasicAgent extends Agent
{
    protected Logger logger;

    public BasicAgent()
    {
        this.logger = new SimpleLogger();
    }

    public Logger getLogger() {return logger;}
}
