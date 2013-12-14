package com.cma.gcvms.web.conversation.component;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuModel;

import com.cma.gcvms.web.conversation.Conversation;
import com.cma.gcvms.web.conversation.ConversationManager;

@Named
@Singleton
public class ConversationMenu {
    @Inject
    private ConversationManager conversationManager;

    public boolean getRender() {
        return !conversationManager.conversationMap().isEmpty();
    }

    public MenuModel getModel() {
        MenuModel model = new DynamicMenuModel();
        Conversation currentConversation = conversationManager.getCurrentConversation();
        for (Conversation conversation : conversationManager.conversationMap().values()) {
            DefaultMenuItem htmlMenuItem = new DefaultMenuItem();
            htmlMenuItem.setValue(conversation.getLabel());
            htmlMenuItem.setUrl(conversation.getUrl());
            if (currentConversation != null && currentConversation.getId().equals(conversation.getId())) {
                htmlMenuItem.setDisabled(true);
            }
            if (isNotBlank(conversation.getLabel())) {
                model.addElement(htmlMenuItem);
            }
        }
        return model;
    }
}
