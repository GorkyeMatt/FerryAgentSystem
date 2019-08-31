package FerrySystem.Commons.helpers;

import jade.lang.acl.ACLMessage;

public class SimpleLogger implements Logger {
    private String agentName;

    private boolean displaySendReceived = true;
    private boolean displayLog = true;

    public void setDisplayLog(boolean displayLog)
    {
        this.displayLog = displayLog;
    }

    public void setDisplaySendReceived(boolean displaySendReceived)    {
        this.displaySendReceived = displaySendReceived;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void log(String message){
        if(displayLog)
            System.out.println(agentName + ":\n" + message + "\n");
    }

    public void log(ACLMessage message){
        if(displayLog)
            System.out.println(agentName + ":\n" + message + "\n");
    }

    @Override
    public void logSend(ACLMessage message){
        if(displaySendReceived)
            System.out.println(agentName + ":\nsending:\n:" + message + "\n");
    }

    @Override
    public void logReceived(ACLMessage message){
        if(displaySendReceived)
            System.out.println(agentName + ":\nreceived:\n:" + message + "\n");
    }
}
