/**
 * Copyright (c) 2013 MNCC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author http://www.mncc.fr
 */
package fr.mncc.gwttoolbox.authenticate.client.googleplus.dto;

import com.google.gwt.core.client.JavaScriptObject;

public class GooglePlusToken extends JavaScriptObject {

  protected GooglePlusToken() {

  }

  public final native String accessToken() /*-{
    return this.access_token;
  }-*/;

  public final native String authuser() /*-{
      return this.authuser;
  }-*/;

  public final native String clientId() /*-{
      return this.client_id;
  }-*/;

  public final native String code() /*-{
      return this.code;
  }-*/;

  public final native String cookiePolicy() /*-{
      return this.cookie_policy;
  }-*/;

  public final native String expiresAt() /*-{
      return this.expires_at;
  }-*/;

  public final native String expiresIn() /*-{
      return this.expires_in;
  }-*/;

  public final native String gUserCookiePolicy() /*-{
      return this.g_user_cookie_policy;
  }-*/;

  public final native String idToken() /*-{
      return this.id_token;
  }-*/;

  public final native String issuedAt() /*-{
      return this.issued_at;
  }-*/;

  public final native String prompt() /*-{
      return this.prompt;
  }-*/;

  public final native String responseType() /*-{
      return this.response_type;
  }-*/;

  public final native String scope() /*-{
      return this.scope;
  }-*/;

  public final native String sessionState() /*-{
      return this.session_state;
  }-*/;

  public final native String state() /*-{
      return this.state;
  }-*/;

  public final native String tokenType() /*-{
      return this.token_type;
  }-*/;
}
