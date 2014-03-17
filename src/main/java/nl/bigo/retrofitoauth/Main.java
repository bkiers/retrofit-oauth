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

            // Get the access token after the user verified access.
            AccessToken accessToken = accountsService.getAccessToken(
                    clientId, clientSecret, userCode.getDeviceCode(), "http://oauth.net/grant_type/device/1.0");
            System.out.println(accessToken);

            // Use Retrofit to get the user's Google Profile using the access token.
            GoogleApisService apisService = ServiceGenerator.createService(GoogleApisService.class, GoogleApisService.BASE_URL, accessToken);
            System.out.println(apisService.getProfile());
        }
        catch (RetrofitError error) {
            if (error.getResponse() == null) {
                System.out.println("response=" + error.getResponse());
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
