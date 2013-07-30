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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.rpc.AsyncCallback;
import fr.mncc.gwttoolbox.authenticate.client.googleplus.dto.GooglePlusToken;
import fr.mncc.gwttoolbox.authenticate.client.googleplus.dto.GooglePlusUserEmail;
import fr.mncc.gwttoolbox.authenticate.client.googleplus.dto.GooglePlusUserInfo;
import fr.mncc.gwttoolbox.base.client.json.Jsonp;

public final class GooglePlusUtils {

  public static void inject() {
    HeadElement headElement = (HeadElement) Document.get().getElementsByTagName("head").getItem(0);
    if (headElement != null) {
      ScriptElement scriptElement = Document.get().createScriptElement("{parsetags:'explicit'}");
      if (scriptElement != null) {
        scriptElement.setSrc("https://apis.google.com/js/client:plusone.js");
        headElement.appendChild(scriptElement);
      }
    }
  }

  public static native boolean isGapiReady(String namespace) /*-{
      return typeof ($wnd.gapi) != 'undefined' && typeof ($wnd.gapi[namespace]) != 'undefined';
  }-*/;

  public static void getUserInfo(final AsyncCallback<GooglePlusUserInfo> callback) {
    Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {

      @Override
      public boolean execute() {
        if (!GooglePlusUtils.isGapiReady("client"))
          return true;

        getUserInfoNative(callback);
        return false;
      }
    });
  }

  public static void getUserEmail(final AsyncCallback<GooglePlusUserEmail> callback) {
    Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {

      @Override
      public boolean execute() {
        if (!GooglePlusUtils.isGapiReady("client"))
          return true;

        getUserEmailNative(callback);
        return false;
      }
    });
  }

  public static void logout(GooglePlusToken token, AsyncCallback<JavaScriptObject> callback) {
    Jsonp.get("https://accounts.google.com/o/oauth2/revoke?token=" + token.accessToken(), callback);
  }

  private static native void getUserInfoNative(AsyncCallback<GooglePlusUserInfo> callback) /*-{
      var callbackWrapper = function(obj) {
          callback.@fr.mncc.gwttoolbox.authenticate.client.googleplus.dto.GooglePlusUserInfo::onSuccess(*)(obj);
      };
      $wnd.gapi.client.load('plus', 'v1', function() {
          var request = $wnd.gapi.client.plus.people.get({'userId': 'me'});
          request.execute(callbackWrapper);
      });
  }-*/;

  private static native void getUserEmailNative(AsyncCallback<GooglePlusUserEmail> callback) /*-{
      var callbackWrapper = function(obj) {
          callback.@fr.mncc.gwttoolbox.authenticate.client.googleplus.dto.GooglePlusUserEmail::onSuccess(*)(obj);
      };
      $wnd.gapi.client.load('oauth2', 'v2', function() {
          var request = $wnd.gapi.client.oauth2.userinfo.get();
          request.execute(callbackWrapper);
      });
  }-*/;
}
