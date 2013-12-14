package com.cma.gcvms.web.faces;

import static com.cma.gcvms.web.conversation.ConversationHolder.getCurrentConversation;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import com.cma.gcvms.web.conversation.Conversation;

public class ConversationScope implements Scope {

    @Override
    public String getConversationId() {
        return getCurrentConversation().getId();
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Conversation currentConversation = getCurrentConversation();
        Object bean = currentConversation.getBean(name);

        if (bean == null) {
            bean = objectFactory.getObject();
            currentConversation.addBean(name, bean);
        }

        return bean;
    }

    @Override
    public Object remove(String name) {
        throw new UnsupportedOperationException("remove is done during conversation.end");
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // no ops
    }

    @Override
    public Object resolveContextualObject(String key) {
        // TODO: is it really required?      
        throw new UnsupportedOperationException("remove is done during conversation.end");
    }
}