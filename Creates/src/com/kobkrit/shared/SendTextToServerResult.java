package com.kobkrit.shared;

import com.gwtplatform.dispatch.shared.Result;
import com.kobkrit.client.Entity.Message;

/**
 * The result of a {@link SendTextToServer} action.
 */
public class SendTextToServerResult implements Result {

  private Message response;

  public SendTextToServerResult(final Message response) {
    this.response = response;
  }

  /**
   * For serialization only.
   */
  @SuppressWarnings("unused")
  private SendTextToServerResult() {
  }

  public Message getResponse() {
    return this.response;
  }

}