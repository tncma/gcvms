package com.cma.gcvms.web.faces;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

import org.omnifaces.exceptionhandler.FullAjaxExceptionHandler;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory wrapped;

    /**
     * Construct a new full conversation aware exception handler factory around the given wrapped factory.
     * @param wrapped The wrapped factory.
     */
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * Returns a new instance of {@link CustomExceptionHandler} which wraps the original exception handler.
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(new FullAjaxExceptionHandler(wrapped.getExceptionHandler()));
    }

    /**
     * Returns the wrapped factory.
     */
    @Override
    public ExceptionHandlerFactory getWrapped() {
        return wrapped;
    }
}