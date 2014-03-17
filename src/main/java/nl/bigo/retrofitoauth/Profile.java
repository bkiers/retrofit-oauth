package nl.bigo.retrofitoauth;

import com.google.gson.annotations.SerializedName;

public class Profile extends BaseResponse {

    private String id;
    private String email;

    @SerializedName("given_name")
    private String givenName;

    @SerializedName("family_name")
    private String familyName;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public String toString() {

        if (super.getError() != null) {
            return "Profile{error='" + super.getError() + "'}";
        }

        return "Profile{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                '}';
    }
}
