package com.cma.gcvms.web.validation;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.BeanValidator;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;


@Named
@Scope(SCOPE_PROTOTYPE)
public class LenientBeanValidator extends BeanValidator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (ConditionalValidatorUtil.doValidation(context)) {
            super.validate(context, component, value);
        }
    }
}
