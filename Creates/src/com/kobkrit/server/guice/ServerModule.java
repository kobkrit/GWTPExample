package com.kobkrit.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.kobkrit.server.SendTextToServerActionHandler;
import com.kobkrit.shared.SendTextToServer;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(SendTextToServer.class, SendTextToServerActionHandler.class);
	}
}
