package com.cma.gcvms.web.conversation.component;

import static com.cma.gcvms.web.conversation.ConversationHolder.getCurrentConversation;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Iterator;
import java.util.Stack;

import javax.inject.Named;
import javax.inject.Singleton;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuModel;

import com.cma.gcvms.web.conversation.Conversation;
import com.cma.gcvms.web.conversation.ConversationContext;

/**
 * The Conversation breadcrumb displays the current conversation contextes.
 * Usage: &lt;breadcrumb model="#{conversationBreadCrumb.model}" rendered="#{conversationBreadCrumb.render}" /&gt;
 */
@Named
@Singleton
public class ConversationBreadCrumb {

    public boolean getRender() {
        Conversation currentConversation = getCurrentConversation();
        return currentConversation == null ? false : !currentConversation.getConversationContextes().isEmpty();
    }

    public MenuModel getModel() {
        MenuModel model = new DynamicMenuModel();
        Stack<ConversationContext<?>> ctxStack = getCurrentConversation().getConversationContextes();
        int beforeLastIndex = ctxStack.size() - 2;
        Iterator<ConversationContext<?>> iterator = ctxStack.iterator();

        // first item is rendered as ui-icon-home => we don't want it so we disable it.
        DefaultMenuItem menuItem = new DefaultMenuItem();
        menuItem.setRendered(false);
        model.addElement(menuItem);

        int i = 0;
        while (iterator.hasNext()) {
            ConversationContext<?> ctx = iterator.next();
            if (isNotBlank(ctx.getLabel())) {
                menuItem = new DefaultMenuItem();
                menuItem.setValue(ctx.getLabel());
                if (i == beforeLastIndex && beforeLastIndex > 0) {
                    // calls back button action which will trigger the callback 
                    // as if the user had pressed on 'back' button.
                    menuItem.setOnclick("APP.menu.back()");
                    menuItem.setImmediate(true);
                } else {
                    menuItem.setDisabled(true);
                }

                model.addElement(menuItem);
            }
            i++;
        }
        return model;
    }
}
