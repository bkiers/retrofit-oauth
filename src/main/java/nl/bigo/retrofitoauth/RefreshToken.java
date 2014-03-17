package nl.bigo.retrofitoauth;

import com.google.gson.annotations.SerializedName;

public class RefreshToken extends BaseResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Long expiresIn;

    @SerializedName("id_token")
    private String idToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    @Override
    public String toString() {

        if (super.getError() != null) {
            return "RefreshToken{error='" + super.getError() + "'}";
        }

        return "RefreshToken{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", idToken='" + idToken + '\'' +
                '}';
    }
}
