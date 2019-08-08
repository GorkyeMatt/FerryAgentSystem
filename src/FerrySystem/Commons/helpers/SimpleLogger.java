package FerrySystem.Commons.helpers;

import jade.lang.acl.ACLMessage;

public class SimpleLogger implements Logger {
    private String agentName;

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void log(String message){
        System.out.println(agentName + ":\n" + message + "\n");
    }

    public void log(ACLMessage message){
        System.out.println(agentName + ":\n" + message + "\n");
    }
}