package network.quant.util;


public class Oauth2RefreshResponse {

    String id_token;
    String access_token;
    String token_type;
    String expires_in;

    public Oauth2RefreshResponse() {

        System.out.println("Oauth2RefreshResponse empty constructor");
    }

    public Oauth2RefreshResponse(String idToken, String accessToken, String tokenType, String expiresIn) {
        System.out.println("Oauth2RefreshResponse response created");
        this.id_token = idToken;
        this.access_token = accessToken;
        this.token_type = accessToken;
        this.expires_in = expiresIn;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "Oauth2RefreshResponse{" +
                "id_token='" + id_token + '\'' +
                ", access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}