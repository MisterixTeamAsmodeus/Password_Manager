package com.misterixteam.asmodeus.passwordmanager;

import java.util.Objects;

public class PasswordContainer {
    private int id;
    private String uriToImage;
    private String webSite;
    private String login;
    private String password;

    public PasswordContainer(int id, String uriToImage, String webSite, String login, String password) {
        this.id = id;
        this.uriToImage = uriToImage;
        this.webSite = webSite;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PasswordContainer() {
        uriToImage = "";
        webSite = "";
        login = "";
        password = "";
    }

    public String getUriToImage() {
        return uriToImage;
    }

    public void setUriToImage(String uriToImage) {
        this.uriToImage = uriToImage;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getLogin(String key) {
        return Encrypt.decode(login, key);
    }

    public void setLogin(String login, String key) {
        this.login = Encrypt.encode(login, key);
    }

    public String getPassword(String key) {
        return Encrypt.decode(password, key);
    }

    public void setPassword(String password, String key) {
        this.password = Encrypt.encode(password, key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordContainer that = (PasswordContainer) o;
        return id == that.id && Objects.equals(uriToImage, that.uriToImage) && Objects.equals(webSite, that.webSite) && Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uriToImage, webSite, login, password);
    }
}
