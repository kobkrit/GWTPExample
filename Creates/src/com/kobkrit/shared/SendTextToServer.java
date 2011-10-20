package com.kobkrit.shared;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import com.kobkrit.client.Entity.Message;

/**
 * An action that can be sent using an {@link DispatchAsync} (client-side)
 * corresponding to a {@link com.gwtplatform.dispatch.server.Dispatch}
 * (server-side).
 */
public class SendTextToServer extends
    UnsecuredActionImpl<SendTextToServerResult> {

  private Message textToServer;

  public SendTextToServer(final Message textToServer) {
    this.textToServer = textToServer;
  }

  /**
   * For serialization only.
   */
  @SuppressWarnings("unused")
  private SendTextToServer() {
  }

  public Message getTextToServer() {
    return textToServer;
  }
}