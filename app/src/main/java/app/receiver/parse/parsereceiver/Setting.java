package app.receiver.parse.parsereceiver;

public class Setting {
    private String appId;
    private String clientKey;

    public String getAppId() { return appId; }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    @Override
    public String toString() {
        return "[" + this.appId + "] [" + this.clientKey + "]";
    }
}
