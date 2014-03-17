# Retrofit OAuth

A small demo how to use [Retrofit](http://square.github.io/retrofit) to
retrieve an OAuth2 access token from a Google account and use it to read
the user's Google profile.

## Run the demo

1. create a new project: [console.developers.google.com](https://console.developers.google.com), 
   and go to
    * `APIs & auth`
        * `Credentials`
            * `Create New Client ID`
            * `Installed application` and `Other`
            * `Create Client ID`
            * write down the `Client ID` and `Client secret`
        * `APIs`
            * enable `Google+ API`
1. create a file called `local.properties` in the root of this project
   and add the keys `client_id` and `client_secret` to it (and the
   corresponding values from step 1, of course)
1. run the [main class](https://github.com/bkiers/retrofit-oauth/blob/master/src/main/java/nl/bigo/retrofitoauth/Main.java)
   by executing `mvn exec:exec`, and follow the instructions on the console
