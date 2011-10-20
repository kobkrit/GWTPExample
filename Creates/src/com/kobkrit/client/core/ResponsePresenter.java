package com.kobkrit.client.core;

import java.util.Stack;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.kobkrit.client.Entity.Message;
import com.kobkrit.client.place.NameTokens;
import com.kobkrit.shared.SendTextToServer;
import com.kobkrit.shared.SendTextToServerResult;

public class ResponsePresenter extends Presenter<ResponsePresenter.MyView, ResponsePresenter.MyProxy> {

	public interface MyView extends View {

		HasClickHandlers getCloseClickHandlers();

		void setServerResponse(String serverResponse);

		void setTextToServer(String textToServer);

		HasClickHandlers getUndoClickHandlers();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.response)
	public interface MyProxy extends ProxyPlace<ResponsePresenter> {
	}

	public static final String textToServerParam = "textToServer";

	private final DispatchAsync dispatcher;

	private final PlaceManager placeManager;

	private String textToServer;
	
	private Stack<Action> actionList = new Stack<Action>();
	private Stack<Result> resultList = new Stack<Result>();

	@Inject
	public ResponsePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DispatchAsync dispatcher, final PlaceManager placeManager) {
		super(eventBus, view, proxy);

		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		textToServer = request.getParameter(textToServerParam, null);
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getView().getCloseClickHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				placeManager.revealPlace(new PlaceRequest(NameTokens.main));
			}
		}));
		registerHandler(getView().getUndoClickHandlers().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				undo();
			}
		}));
	}

	protected void undo() {
		assert !actionList.isEmpty() && !resultList.isEmpty(); 
		dispatcher.undo(actionList.pop(), resultList.pop(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(final Throwable caught) {
				getView().setServerResponse("An error occured: " + caught.getMessage());
			}
			@Override
			public void onSuccess(final Void result) {
				getView().setServerResponse("Already undo!");
			}
		});
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().setTextToServer(textToServer);
		getView().setServerResponse("Waiting for response...");
		Message m = new Message();
		m.setText(textToServer);
		
		final SendTextToServer action = new SendTextToServer(m);
		
		dispatcher.execute(action, new AsyncCallback<SendTextToServerResult>() {
			@Override
			public void onFailure(Throwable caught) {
				getView().setServerResponse("An error occured: " + caught.getMessage());
			}
	
			@Override
			public void onSuccess(SendTextToServerResult result) {
				getView().setServerResponse("Success! " +result.getResponse());
				actionList.push(action);
				resultList.push(result);
			}
		});
	}
}
