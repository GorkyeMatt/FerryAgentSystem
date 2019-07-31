package FerrySystem.Commons.helpers;

import jade.lang.acl.ACLMessage;

public interface Logger {
    String getAgentName();

    void setAgentName(String agentName);

    void log(String message);
    void log(ACLMessage message);
}
