package nl.bigo.retrofitoauth;

import retrofit.RetrofitError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static final String PROPERTIES_FILE_NAME = "local.properties";

    private static final String[] MANDATORY_PROPERTIES = { "client_id", "client_secret" };

    private static Properties loadPropertiesFile(String fileName) {

        Properties properties = null;

        try {
            properties = new Properties();
            properties.load(new FileInputStream(fileName));
        }
        catch (IOException e) {
            System.err.println("something went wrong reading the properties file: " + e.getMessage());
            System.exit(1);
        }

        boolean success = true;

        for (String key : MANDATORY_PROPERTIES) {

            String value = properties.getProperty(key);

            if (value == null || value.trim().isEmpty()) {
                System.err.println("missing property: '" + key + "'");
                success = false;
            }
        }

        if (!success) {
            System.exit(1);
        }

        return properties;
    }

    public static void main(String[] args) {

        Properties properties = loadPropertiesFile(PROPERTIES_FILE_NAME);

        try {
            final Scanner keyboard = new Scanner(System.in);

            final String clientId = properties.getProperty(MANDATORY_PROPERTIES[0]);
            final String clientSecret = properties.getProperty(MANDATORY_PROPERTIES[1]);

            GoogleAccountsService accountsService = ServiceGenerator.createService(GoogleAccountsService.class, GoogleAccountsService.BASE_URL);

            // First get the code the user will enter in a browser.
            UserCode userCode = accountsService.getUserCode(clientId, "email profile");
            System.out.println(userCode);

            // Give instructions to the user.
            System.out.printf("visit '%s' and enter the code: '%s' (press return once this is done)",
                    userCode.getVerificationUrl(), userCode.getUserCode());
            keyboard.nextLine();

            // Get the access token after the user verified access and get the user's profile with it.
            AccessToken accessToken = accountsService.getAccessToken(clientId, clientSecret,
                    userCode.getDeviceCode(), GoogleAccountsService.ACCESS_GRANT_TYPE);
            // Store this refresh token separately: subsequent access tokens will not include it! Getting
            // a new refresh token would mean asking the user to enter another code!
            final String refreshToken = accessToken.getRefreshToken();
            GoogleApisService apisService = ServiceGenerator.createService(GoogleApisService.class, GoogleApisService.BASE_URL, accessToken);
            System.out.println("token: " + accessToken);
            System.out.println(apisService.getProfile());

            // Now pretend that our access token expired and get a new access token with the refresh token.
            AccessToken refreshedAccessToken = accountsService.refreshAccessToken(clientId, clientSecret,
                    refreshToken, GoogleAccountsService.REFRESH_GRANT_TYPE);
            apisService = ServiceGenerator.createService(GoogleApisService.class, GoogleApisService.BASE_URL, refreshedAccessToken);
            System.out.println("refreshed token: " + refreshedAccessToken); // Note: no refreshToken in here!
            System.out.println(apisService.getProfile());
        }
        catch (RetrofitError error) {
            if (error.getResponse() == null) {
                System.out.println("error=" + error);
            }
            else {
                System.out.println("response=" + error.getResponse() + ", status=" + error.getResponse().getStatus());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
