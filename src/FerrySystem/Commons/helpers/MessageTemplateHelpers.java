package FerrySystem.Commons.helpers;

import jade.lang.acl.MessageTemplate;

public class MessageTemplateHelpers {

    public static MessageTemplate and(MessageTemplate... templates){
        MessageTemplate messageTemplate = templates[0];
        for(MessageTemplate template: templates)
            messageTemplate = MessageTemplate.and(messageTemplate, template);
        return  messageTemplate;
    }
}
