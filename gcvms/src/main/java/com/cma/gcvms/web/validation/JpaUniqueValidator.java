package com.cma.gcvms.web.validation;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.cma.gcvms.domain.Identifiable;
import com.cma.gcvms.repository.support.JpaUniqueUtil;
import com.cma.gcvms.util.ResourcesUtil;

@Named
@Scope(SCOPE_PROTOTYPE)
@FacesValidator("jpaUniqueValidator")
public class JpaUniqueValidator implements Validator {

    @Inject
    private JpaUniqueUtil jpaUniqueUtil;
    @Inject
    private ResourcesUtil resourcesUtil;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (ConditionalValidatorUtil.doValidation(context)) {
            if (entity == null && isBlank(property)) {
                return;
            }

            String errorCode = jpaUniqueUtil.validateSimpleUnique(entity, property, value);
            if (errorCode != null) {
                FacesMessage fm = new FacesMessage(resourcesUtil.getProperty(errorCode));
                fm.setSeverity(SEVERITY_ERROR);
                throw new ValidatorException(fm);
            }
        }
    }

    private Identifiable<?> entity;
    private String property;

    public void setEntity(Identifiable<?> entity) {
        this.entity = entity;
    }

    public Identifiable<?> getEntity() {
        return entity;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
