package nl.bigo.retrofitoauth;

import retrofit.http.*;

public interface GoogleAccountsService {

    public static final String BASE_URL = "https://accounts.google.com";

    @POST("/o/oauth2/device/code")
    @FormUrlEncoded
    UserCode getUserCode(@Field("client_id") String clientId,
                         @Field("scope") String scope);

    @POST("/o/oauth2/token")
    @FormUrlEncoded
    AccessToken getAccessToken(@Field("client_id") String clientId,
                               @Field("client_secret") String clientSecret,
                               @Field("code") String code,
                               @Field("grant_type") String grantType);

    @POST("/o/oauth2/token")
    @FormUrlEncoded
    RefreshToken refreshAccessToken(@Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("refresh_token") String refreshToken,
                                    @Field("grant_type") String grantType);
}
