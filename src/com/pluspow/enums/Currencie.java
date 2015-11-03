package com.pluspow.enums;

/**
 * 通過
 * <pre>
 * 参照：
 * https://developers.google.com/adsense/host/appendix/currencies?hl=ja
 * https://ja.wikipedia.org/wiki/%E7%8F%BE%E8%A1%8C%E9%80%9A%E8%B2%A8%E3%81%AE%E4%B8%80%E8%A6%A7
 * </pre>
 * @author takahara
 *
 */
public enum Currencie {
    ISK ("kr"),
    GBP("£"),
    AZN("man."),
    INR("₹"),
    EUR ("€"),
    SHP ("£"),
    AFN ("؋"),
    USD ("$"),
    AED ("د.إ"),
    DZD ("د.ج"),
    ARS ("$"),
    AWG ("ƒ"),
    ALL ("L"),
    AOA ("KzTu"),
    XCD ("$"),
    YER ("﷼"),
    ILS ("₪"),
    IQD ("ع.د"),
    IRR ("﷼"),
    IDR ("Rp"),
    XPF("₣"),
    UGX ("Ush"),
    UAH ("₴"),
    UZS ("Us"),
    UYU ("$"),
    EGP ("£"),
    ETB ("Br"),
    ERN ("Nfk"),
    SVC ("₡"),
    AUD ("$"),
    OMR ("ر.ع."),
    GHC ("₵"),
    CVE ("Esc"),
    GYD ("$"),
    QAR ("ر.ق"),
    CAD ("$"),
    XAF ("Fr"),
    GMD ("D"),
    KHR ("៛"),
    GNF ("Fr"),
    XOF ("Fr"),
    CUC ("$"),
    ANG ("ƒ"),
    KGS ("сом"),
    GTQ ("Q"),
    KWD ("د.ك"),
    NZD ("$"),
    DKK ("kr"),
    HRK ("kn"),
    KYD ("$"),
    KES ("S"),
    CRC ("₡"),
    KMF ("Fr"),
    COP ("$"),
    CDF ("Fr"),
    SAR ("ر.س"),
    WST ("S$"),
    STD ("Db"),
    ZMK ("ZK"),
    SLL ("Le"),
    DJF ("Fr"),
    GIP ("£"),
    JMD ("$"),
    SYP ("£"),
    SGD ("$"),
    ZWD ("$"),
    SDG ("£"),
    SEK ("kr"),
    SRD ("$"),
    LKR ("ரூ"),
    SZL ("L"),
    SCR ("₨"),
    CSD ("Din."),
    SBD ("$"),
    THB ("฿"),
    KRW ("₩"),
    TWD ("NT$"),
    TJS ("ЅМ"),
    TZS ("Tsh"),
    CZK ("Kč"),
    CNY ("¥"),
    TND ("د.ت"),
    KPW ("₩"),
    CLP ("$"),
    DOP ("$"),
    TTD ("$"),
    TMM ("m"),
    TOP ("T$"),
    NGN ("₦"),
    NAD ("$"),
    NIO ("$"),
    MAD ("د.م."),
    JPY ("¥"),
    NPR ("₨"),
    NOK ("kr"),
    BHD ("ب.د"),
    HTG ("G"),
    PKR ("₨"),
    PAB ("B/."),
    VUV ("VT"),
    BSD ("$"),
    PGK ("K"),
    BMD ("$"),
    PYG ("₲"),
    BBD ("$"),
    HUF ("Ft"),
    BDT ("৲"),
    BTN ("Nu"),
    FJD ("$"),
    PHP ("₱"),
    FKP ("£"),
    BRL ("R$"),
    BGN ("лв"),
    BND ("$"),
    BIF ("Fr"),
    VND ("₫"),
    VEB ("Bs"),
    BYR ("р."),
    BZD ("$"),
    PEN ("S/."),
    PLN ("zł"),
    BAM ("KM"),
    BWP ("P"),
    BOB ("Bs"),
    HKD ("HK$"),
    HNL ("L"),
    MOP ("P"),
    MKD ("ден"),
    MWK ("MK"),
    MYR ("RM"),
    ZAR ("R"),
    SSP ("£"),
    MMK ("K"),
    MXN ("$"),
    MUR ("₨"),
    MRO ("UM"),
    MZN ("MTn"),
    MVR ("Rf"),
    MDL ("L"),
    MNT ("₮"),
    JOD ("د.ا"),
    LAK ("₭"),
    LTL ("Lt"),
    LYD ("ل.د"),
    CHF ("S₣"),
    LRD ("$"),
    RON ("L"),
    RWF ("Fr"),
    LSL ("L"),
    RUB("₽"),
    GEL("ლ"),
    YTL("₤"),
    LBP ("L.L.");

    private String symbol;

    private Currencie(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
