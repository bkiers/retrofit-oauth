package nl.bigo.retrofitoauth;

import retrofit.http.GET;

public interface GoogleApisService {

    public static final String BASE_URL = "https://www.googleapis.com";

    @GET("/oauth2/v1/userinfo?alt=json")
    Profile getProfile();
}
