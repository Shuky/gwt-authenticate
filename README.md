gwt-authenticate
================

Authentication for Single Page Applications.

Dependencies
============

* [gwt-jsonrpc](https://github.com/MNCC/gwt-jsonrpc)

How to get started ?
====================

Add the following line to your *.gwt.xml file :

```xml
	<inherits name='com.google.gwtjsonrpc.GWTJSONRPC'/>
	<inherits name='fr.mncc.gwttoolbox.authenticate.authenticate'/>
```

Example : securing JSON-RPC Web services
========================================

* Create Web services interfaces.

```java
    public interface LoginService extends SecureService {
      void login(String username, String password, AsyncCallback<Boolean> callback);
      void logout(AsyncCallback<Boolean> callback);
    }
```

```java
    public interface HelloWorldService extends SecureService {
      void getHelloWorld(AsyncCallback<String> callback);
    }
```

* Implement Web services.

```java
    /**
     * Public Web service (e.g. anybody will be able to access it).
     * Must extend PublicServiceImpl.
     */
    public class LoginServiceImpl extends PublicServiceImpl implements LoginService {

      @Override
      public void login(String username, String password, AsyncCallback<Boolean> callback) {
        if (username == null || username.isEmpty() || !username.equals("user")) {
          callback.onFailure(new Exception("Invalid username."));
          return;
        }
        if (password == null || password.isEmpty() || !password.equals("test")) {
          callback.onFailure(new Exception("Invalid password."));
          return;
        }
        saveCurrentUser(username, UserRoles.USER);  // Identify user as logged-in
        callback.onSuccess(true);
      }

      @Override
      public void logout(AsyncCallback<Boolean> callback) {
        removeCurrentUser();    // Disconnect user
        callback.onSuccess(true);
      }
    }
```

```java
    /**
     * Private Web service (e.g. only logged-in users will be able to access it).
     * Must extend PrivateServiceImpl.
     */
    public class HelloWorldServiceImpl extends PrivateServiceImpl implements HelloWorldService {

      @Override
      public void getHelloWorld(AsyncCallback<String> callback) {
        callback.onSuccess("Hello World!");
      }
    }
```

* Add Web services to you WEB.xml file.

```xml
    <servlet>
        <servlet-name>loginService</servlet-name>
        <servlet-class>module_name.server.LoginServiceImpl</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>loginService</servlet-name>
        <url-pattern>/module_name/loginService</url-pattern>
    </servlet-mapping>
```

```xml
    <servlet>
        <servlet-name>helloWorldService</servlet-name>
        <servlet-class>module_name.server.HelloWorldServiceImpl</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>helloWorldService</servlet-name>
        <url-pattern>/module_name/helloWorldService</url-pattern>
    </servlet-mapping>
```

* Call Web services from the client.

```java
    // Setup Web services
    final HelloWorldService helloWorldService_ = GWT.create(HelloWorldService.class);
    final LoginService loginService_ = GWT.create(LoginService.class);

    // Setup security manager
    final SecurityManager securityManager = new SecurityManager();
    securityManager.registerService(helloWorldService_, "helloWorldService");
    securityManager.registerService(loginService_, "loginService");

    // Call your Web services as usual
    helloWorldService_.login("user", "test", new SecureAsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                // TODO : process error
            }

            @Override
            public void onSuccess(Boolean isOk) {
                if (isOk) {
                    helloWorldService_.getHelloWorld(new SecureAsyncCallback<String>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            // TODO : process error
                        }

                        @Override
                        public void onSuccess(String html) {
                            // TODO : display html string
                        }
                    });
                }
            }
    });
```

Example : securing AJAX routes
==============================



