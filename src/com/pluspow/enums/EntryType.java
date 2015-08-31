package com.pluspow.enums;

/**
 * テキストリソース役割
 * <pre>
 * 追加や変更の場合は必ずTextResourcesService 内のUpdateも合わせて修正
 * </pre>
 * @author takahara
 *
 */
public enum EntryType {
    REGISTER("確認メールを送信しました！", "メールの指示に従ってアカウントの登録を完了してください。", "/client/addAccount"), 
    RESET_PASSWORD("パスワード再設定メールを送信しました。", "メールの指示に従ってパスワードの再設定を行ってください。", "/client/settingPassword");

    private String title;
    private String messege;
    private String redirectPath;

    private EntryType(String title, String messege, String redirectPath) {
        this.title = title;
        this.messege = messege;
        this.redirectPath = redirectPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }
}
