package com.cma.gcvms.web.faces;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.persistence.OptimisticLockException;

import com.cma.gcvms.web.util.ExceptionUtil;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;


public class OptimisticLockExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    /**
     * Construct a new exception handler around the given wrapped exception handler.
     * @param wrapped The wrapped exception handler.
     */
    public OptimisticLockExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void handle() {
        Iterable<ExceptionQueuedEvent> optimisticLockEvents = Iterables.filter(getUnhandledExceptionQueuedEvents(), optimisticLockCausePredictate);

        if (!Iterables.isEmpty(optimisticLockEvents)) {
            Iterables.removeIf(getUnhandledExceptionQueuedEvents(), Predicates.alwaysTrue());
        }

        wrapped.handle();
    }

    /**
     * <code>true</code> if exception cause is of type {@link OptimisticLockException}
     */
    private Predicate<ExceptionQueuedEvent> optimisticLockCausePredictate = new Predicate<ExceptionQueuedEvent>() {
        @Override
        public boolean apply(ExceptionQueuedEvent event) {
            Throwable exception = event.getContext().getException();
            return ExceptionUtil.isCausedBy(exception, OptimisticLockException.class);
        }
    };

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }
}
