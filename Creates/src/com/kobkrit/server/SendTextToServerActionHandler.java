package com.kobkrit.server;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.kobkrit.client.Entity.Message;
import com.kobkrit.shared.FieldVerifier;
import com.kobkrit.shared.SendTextToServer;
import com.kobkrit.shared.SendTextToServerResult;

public class SendTextToServerActionHandler implements ActionHandler<SendTextToServer, SendTextToServerResult> {

	private final ServletContext servletContext;

	private final Provider<HttpServletRequest> requestProvider;

	@Inject
	public SendTextToServerActionHandler(final ServletContext servletContext, final Provider<HttpServletRequest> requestProvider) {

		this.servletContext = servletContext;
		this.requestProvider = requestProvider;
	}

	@Override
	public SendTextToServerResult execute(SendTextToServer action, ExecutionContext context) throws ActionException {
		Message input = action.getTextToServer();

		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input.getText())) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new ActionException("Name must be at least 4 characters long");
		}

		MessageDAO m = new MessageDAO();
		m.put(input);
		assert input.getId() != null;

		return new SendTextToServerResult(input);
	}

	@Override
	public void undo(SendTextToServer action, SendTextToServerResult result, ExecutionContext context) throws ActionException {
		MessageDAO m = new MessageDAO();
		m.delete(result.getResponse());
	}

	@Override
	public Class<SendTextToServer> getActionType() {
		return SendTextToServer.class;
	}
}
