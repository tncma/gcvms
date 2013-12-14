package com.cma.gcvms.web.faces;

import java.util.Iterator;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.persistence.OptimisticLockException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;

import com.cma.gcvms.web.util.ExceptionUtil;
import com.cma.gcvms.web.util.MessageUtil;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public CustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public void handle() {
		Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents()
				.iterator();

		if (unhandledExceptionQueuedEvents.hasNext()) {
			Throwable e = unhandledExceptionQueuedEvents.next().getContext()
					.getException();

			// map general purpose exception to error message
			if (ExceptionUtil.isCausedBy(e, OptimisticLockException.class)) {
				MessageUtil.getInstance()
						.error("error_concurrent_modification");
				unhandledExceptionQueuedEvents.remove();
			} else if (ExceptionUtil.isCausedBy(e,
					DataIntegrityViolationException.class)) {
				MessageUtil.getInstance().error(
						"error_data_integrity_violation");
				unhandledExceptionQueuedEvents.remove();
			} else if (ExceptionUtil.isCausedBy(e, AccessDeniedException.class)) {
				// works only if the spring security filter is before the
				// exception filter,
				// that is if the exception filter handles the exception first.
				MessageUtil.getInstance().error("error_access_denied");
				unhandledExceptionQueuedEvents.remove();
			}
			// exception will be handled by the wrapped exception handler.
		}

		wrapped.handle();
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}
}
