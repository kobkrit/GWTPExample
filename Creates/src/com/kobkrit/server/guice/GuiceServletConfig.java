package com.kobkrit.server.guice;

import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.Injector;
import com.google.inject.Guice;
import com.kobkrit.server.guice.ServerModule;
import com.kobkrit.server.guice.DispatchServletModule;

public class GuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServerModule(), new DispatchServletModule());
	}
}
