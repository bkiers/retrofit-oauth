package nl.bigo.retrofitoauth;

import retrofit.http.*;

public interface GoogleAccountsService {

    String BASE_URL = "https://accounts.google.com";
    String ACCESS_GRANT_TYPE = "http://oauth.net/grant_type/device/1.0";
    String REFRESH_GRANT_TYPE = "refresh_token";

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
    AccessToken refreshAccessToken(@Field("client_id") String clientId,
                                   @Field("client_secret") String clientSecret,
                                   @Field("refresh_token") String refreshToken,
                                   @Field("grant_type") String grantType);
}
