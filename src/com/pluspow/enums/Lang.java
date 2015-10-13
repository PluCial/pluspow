package com.pluspow.enums;


/** 機械翻訳言語 */
public enum Lang {
    ar("ar", "アラビア語", Country.PS, "العربية", false),
    az("az", "アゼルバイジェン語", Country.AZ, "Azərbaycan", false),
    bn("bn", "ベンガル語", Country.BD, "বাঙালি", false),
    be("be", "ベラルーシ語", Country.BY, "беларускі", false),
    bg("bg", "ブルガリア語", Country.BG, "български", false),
    zhch("zh-CN","中国語 (簡体)", Country.CN, "中文（中国）", true),
    zhtw("zh-TW", "中国語 (繁体)", Country.TW, "中國（台灣）", true),
    hr("hr", "クロアチア語", Country.HR, "hrvatska", false),
    cs("cs", "チェコ語", Country.CZ, "čeština", false),
    da("da", "デンマーク語", Country.DK, "danske", false),
    nl("nl", "オランダ語", Country.NL, "nederlandse", false),
    en("en", "英語", Country.US, "English", true),
    tl("tl", "タガログ語", Country.PH, "tagalog", false),
    fi("fi", "フィンランド語", Country.FI, "suomi", false),
    fr("fr", "フランス語", Country.FR, "français", false),
    ka("ka", "ジョージア語", Country.GE, "ქართული", false),
    de("de", "ドイツ語", Country.DE, "Deutsch", false),
    el("el", "ギリシャ語", Country.GR, "Ελληνική", false),
    hi("hi", "ヒンディー語", Country.IN, "हिन्दी", false),
    hu("hu", "ハンガリー語", Country.HU, "magyar", false),
    is("is", "アイスランド語", Country.IS, "Íslenska", false),
    id("id", "インドネシア語", Country.ID, "indonesia", true),
    ga("ga", "アイルランド語", Country.IE, "irish", false),
    it("it", "イタリア語", Country.IT, "italiano", false),
    ja("ja", "日本語", Country.JP, "日本語", false),
    ko("ko", "韓国語", Country.KR, "한국어", true),
    la("la", "ラテン語", Country.VA, "Latine", false),
    lv("lv", "ラトビア語 (レット語)", Country.LV, "Latvijā", false),
    mk("mk", "マカドニア語", Country.MK, "ПЈР македонски", false), 
    ms("ms", "マレー語", Country.MY, "Melayu", false),
    mt("mt", "マルタ語", Country.MT, "Malti", false),
    no("no", "ノルウェー語", Country.NO, "norsk", false),
    fa("fa", "ペルシャ語", Country.IR, "فارسی", false),
    pl("pl", "ポーランド語", Country.PL, "Polskie", false),
    pt("pt", "ポルトガル語", Country.PT, "português", false),
    ro("ro", "ルーマニア語", Country.RO, "Română", false),
    ru("ru", "ロシア語", Country.RU, "Русский", false),
    sk("sk", "スロバキア語", Country.SK, "slovenský", false),
    sl("sl", "スロヴェニア語", Country.SI, "slovenščina", false),
    es("es", "スペイン語", Country.ES, "Español", true),
    sv("sv", "スウェーデン語", Country.SE, "svenska", false),
    th("th", "タイ語", Country.TH, "ภาษาไทย", true),
    tr("tr", "トルコ語", Country.TR, "Türk", false),
    uk("uk", "ウクライナ語", Country.UA, "Український", false),
    lt("lt", "リトアニア語", Country.LT, "Lietuvos", false),
    vi("vi", "ベトナム語",Country.VN, "Tiếng Việt", false),
    sr("sr", "セルビア語", Country.RS, "Srpski", false);
    
    // 検討中
  
  
    
    // 以下除外

//    yi("yi", "イディッシュ語", Country.DE, "ייִדיש", false), 
//    ht("ht", "ハイチ語", Country.HT, "kreyòl ayisyen", false),
//    gl("gl", "ガリシア語", Country.ES, "galego", false),
//    et("et", "エストニア語", Country.EE, "eesti", false),
//    iw("iw", null, Country.IL, "עברית", false),
//    ur("ur", null, Country.IN, "اردو", false),
//    te("te", null,Country.IN, "Teluga", false),
//    ta("ta", null,Country.IN, "தமிழ்", false),
//    kn("kn", null,Country.IN, "ಕನ್ನಡ", false),
//    gu("gu", null,Country.IN, "ગુજરાતી", false),
//    sq("sq", null,Country.AL, "shqiptar", false);
    
    
//  af("af", null, "アフリカーンス語", false),
//  cy("cy", null, "ウェールズ語", false),
//  sw("sw", null, "スワヒリ語", false),
//  eo("eo", null, "エスペラント語", false),
//  ca("ca", null, "カタロニア語", false),
//  eu("eu", null, "バスク語", false),

    private String langKey;
    private Country country;
    private String name;
    private boolean humanTransSupport;

    private Lang(String langKey,String nameJp, Country country, String name, boolean humanTransSupport) {
        this.langKey = langKey;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }
}
