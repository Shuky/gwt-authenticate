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
package fr.mncc.gwttoolbox.authenticate.client.googleplus;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import fr.mncc.gwttoolbox.authenticate.client.googleplus.dto.GooglePlusToken;
import fr.mncc.gwttoolbox.base.client.ui.Indicator;

public abstract class GooglePlusSignInButton extends Composite {

  public static final String BUTTON_WIDTH_ICON = "iconOnly";
  public static final String BUTTON_WIDTH_STANDARD = "standard";
  public static final String BUTTON_WIDTH_WIDE = "wide";
  public static final String BUTTON_HEIGHT_SHORT = "short";
  public static final String BUTTON_HEIGHT_STANDARD = "standard";
  public static final String BUTTON_HEIGHT_TALL = "tall";
  public static final String BUTTON_THEME_LIGHT = "light";
  public static final String BUTTON_THEME_DARK = "dark";
  private final String clientId_;
  private final String buttonWidth_;
  private final String buttonHeight_;
  private final String buttonTheme_;

  public GooglePlusSignInButton(String clientId) {
    this(clientId, BUTTON_WIDTH_STANDARD, BUTTON_HEIGHT_STANDARD, BUTTON_THEME_DARK);
  }

  public GooglePlusSignInButton(String clientId, String buttonWidth, String buttonHeight,
      String buttonTheme) {
    clientId_ = clientId;
    buttonWidth_ =
        buttonWidth == null || buttonWidth.isEmpty() ? BUTTON_WIDTH_STANDARD : buttonWidth;
    buttonHeight_ =
        buttonHeight == null || buttonHeight.isEmpty() ? BUTTON_HEIGHT_STANDARD : buttonHeight;
    buttonTheme_ = buttonTheme == null || buttonTheme.isEmpty() ? BUTTON_THEME_DARK : buttonTheme;
    initWidget(new HTML());
  }

  protected abstract void onSuccess(GooglePlusToken token);

  protected void onDenied(GooglePlusToken token) {
    Indicator.showError("Access denied.");
  }

  protected void onFailure(GooglePlusToken token) {
    Indicator.showError("Access failure.");
  }

  @Override
  protected void onLoad() {
    Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {

      @Override
      public boolean execute() {
        if (!GooglePlusUtils.isGapiReady("signin"))
          return true;

        render(getElement(), clientId_, buttonWidth_, buttonHeight_, buttonTheme_);
        setCallback();
        return false;
      }
    });
  }

  @Override
  protected void onUnload() {
    removeCallback();
  }

  private native void render(Element element, String clientId, String buttonWidth,
      String buttonHeight, String buttonTheme) /*-{
      $wnd.gapi.signin.render(element, {
          callback: 'googlePlusSignInCallback',
          cookiepolicy: 'single_host_origin',
          clientid: clientId,
          width: buttonWidth,
          height: buttonHeight,
          theme: buttonTheme,
          scope: 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email'
      });
  }-*/;

  private native void setCallback() /*-{
      var self = this;
      $wnd.googlePlusSignInCallback = function(authResult) {
          if (authResult['access_token']) {
              self.@fr.mncc.gwttoolbox.authenticate.client.googleplus.GooglePlusSignInButton::onSuccess(*)(authResult);
          } else if (authResult['error'] === 'access_denied') {
              self.@fr.mncc.gwttoolbox.authenticate.client.googleplus.GooglePlusSignInButton::onDenied(*)(authResult);
          } else {
              self.@fr.mncc.gwttoolbox.authenticate.client.googleplus.GooglePlusSignInButton::onFailure(*)(authResult);
          }
      };
  }-*/;

  private native void removeCallback() /*-{
      if (typeof ($wnd.googlePlusSignInCallback) != 'undefined')
          delete $wnd.googlePlusSignInCallback;
  }-*/;
}
