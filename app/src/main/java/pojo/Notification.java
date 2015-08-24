package pojo;

import java.io.Serializable;

public class Notification implements Serializable
{
    private String id;
    private String alert;
    private String badge;
    private String sound;
    private String title;
    private String payload;
    private String created_at;

    public Notification()
    {
    }

    public Notification(long id, String alert, String badge, String sound, String title, String payload, String created_at)
    {
        this.id = String.valueOf(id);
        this.alert = alert;
        this.badge = badge;
        this.sound = sound;
        this.title = title;
        this.payload = payload;
        this.created_at = created_at;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAlert()
    {
        return alert;
    }

    public void setAlert(String alert)
    {
        this.alert = alert;
    }

    public String getBadge()
    {
        return badge;
    }

    public void setBadge(String badge)
    {
        this.badge = badge;
    }

    public String getSound()
    {
        return sound;
    }

    public void setSound(String sound)
    {
        this.sound = sound;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getPayload()
    {
        return payload;
    }

    public void setPayload(String payload)
    {
        this.payload = payload;
    }
}
