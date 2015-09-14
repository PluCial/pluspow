package com.pluspow.enums;


/** 機械翻訳言語 */
public enum Lang {
    af("af", "アフリカーンス語", false),
    sq("sq", "アルバニア語", false),
    ar("ar", "アラビア語", false),
    az("az", "アゼルバイジャン語", false),
    eu("eu", "バスク語", false),
    bn("bn", "ベンガル語", false),
    be("be", "ベラルーシ語", false),
    bg("bg", "ブルガリア語", false),
    ca("ca", "カタロニア語", false),
    zhCN("zh-CN", "中国語(簡体字)", true),
    zhTW("zh-TW", "中国語(繁体字)", true),
    hr("hr", "クロアチア語", false),
    cs("cs", "チェコ語", false),
    da("da", "デンマーク語", false),
    nl("nl", "オランダ語", false),
    en("en", "英語", true),
    eo("eo", "エスペラント語", false),
    et("et", "エストニア語", false),
    tl("tl", "フィリピン語", false),
    fi("fi", "フィンランド語", false),
    fr("fr", "フランス語", false),
    gl("gl", "ガリシア語", false),
    ka("ka", "ジョージア語", false),
    de("de", "ドイツ語", false),
    el("el", "ギリシャ語", false),
    gu("gu", "グジャラート語", false),
    ht("ht", "ハイチ語", false),
    iw("iw", "ヘブライ語", false),
    hi("hi", "ヒンディー語", false),
    hu("hu", "ハンガリー語", false),
    is("is", "アイスランド語", false),
    id("id", "インドネシア語", true),
    ga("ga", "アイリッシュ語", false),
    it("it", "イタリア語", false),
    ja("ja", "日本語", false),
    kn("kn", "カンナダ語", false),
    ko("ko", "韓国語", true),
    la("la", "ラテン語", false),
    lv("lv", "ラトビア語", false),
    lt("lt", "リトアニア語", false),
    mk("mk", "マケドニア語", false),
    ms("ms", "マレー語", false),
    mt("mt", "マルタ語", false),
    no("no", "ノルウェー語", false),
    fa("fa", "ペルシア語", false),
    pl("pl", "ポーランド語", false),
    pt("pt", "ポルトガル語", false),
    ro("ro", "ルーマニア語", false),
    ru("ru", "ロシア語", false),
    sr("sr", "セルビア語", false),
    sk("sk", "スロバキア語", false),
    sl("sl", "スロベニア語", false),
    es("es", "スペイン語", true),
    sw("sw", "スワヒリ語", false),
    sv("sv", "スウェーデン語", false),
    ta("ta", "タミル語", false),
    te("te", "テルグ語", false),
    th("th", "タイ語", true),
    tr("tr", "トルコ語", false),
    uk("uk", "ウクライナ語", false),
    ur("ur", "ウルドゥー語", false),
    vi("vi", "ベトナム語", false),
    cy("cy", "ウェールズ語", false),
    yi("yi", "イディッシュ語", false);

    private String langKey;
    private String name;
    private boolean humanTransSupport;

    private Lang(String langKey, String name, boolean humanTransSupport) {
        this.langKey = langKey;
        this.name = name;
        this.humanTransSupport = humanTransSupport;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getName() {
        return name;
    }

    public boolean isHumanTransSupport() {
        return humanTransSupport;
    }
}
