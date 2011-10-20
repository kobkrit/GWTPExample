package com.kobkrit.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.kobkrit.client.place.ClientPlaceManager;
import com.kobkrit.client.core.MainPagePresenter;
import com.kobkrit.client.core.MainPageView;
import com.kobkrit.client.place.DefaultPlace;
import com.kobkrit.client.place.NameTokens;
import com.kobkrit.client.core.ResponsePresenter;
import com.kobkrit.client.core.ResponseView;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class, MainPageView.class, MainPagePresenter.MyProxy.class);

		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.main);

		bindPresenter(ResponsePresenter.class, ResponsePresenter.MyView.class, ResponseView.class, ResponsePresenter.MyProxy.class);
	}
}
