package com.cma.gcvms.web.faces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Scope;

@Scope("view")
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewScoped {
}
