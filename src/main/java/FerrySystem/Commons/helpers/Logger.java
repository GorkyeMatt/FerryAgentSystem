package FerrySystem.Commons.helpers;

import jade.lang.acl.ACLMessage;

public interface Logger {
    void setAgentName(String agentName);

    void log(String message);
    void log(ACLMessage message);

    void logSend(ACLMessage message);
    void logReceived(ACLMessage message);

    void setDisplayLog(boolean displayLog);
    void setDisplaySendReceived(boolean displaySendReceived);
}
