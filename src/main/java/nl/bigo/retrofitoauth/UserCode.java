package nl.bigo.retrofitoauth;

import com.google.gson.annotations.SerializedName;

public class UserCode extends BaseResponse {

    @SerializedName("device_code")
    private String deviceCode;

    @SerializedName("user_code")
    private String userCode;

    @SerializedName("verification_url")
    private String verificationUrl;

    @SerializedName("expires_in")
    private Long expiresIn;

    private Integer interval;

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getVerificationUrl() {
        return verificationUrl;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public Integer getInterval() {
        return interval;
    }

    @Override
    public String toString() {

        if (super.getError() != null) {
            return "UserCode{error='" + super.getError() + "'}";
        }

        return "UserCode{" +
                "deviceCode='" + deviceCode + '\'' +
                ", userCode='" + userCode + '\'' +
                ", verificationUrl='" + verificationUrl + '\'' +
                ", expiresIn=" + expiresIn +
                ", interval=" + interval +
                '}';
    }
}
