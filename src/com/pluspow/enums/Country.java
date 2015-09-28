package com.pluspow.enums;

/**
 * 国
 * <pre>
 * 参照：
 * https://ja.wikipedia.org/wiki/ISO_3166-1
 * </pre>
 * @author takahara
 *
 */
public enum Country {
    JP("JPN",392,"日本",true),
    IS("ISL",352,"アイスランド",false),
    IE("IRL",372,"アイルランド",false),
    AZ("AZE",31,"アゼルバイジャン",false),
    AF("AFG",4,"アフガニスタン",false),
    US("USA",840,"アメリカ合衆国",false),
    VI("VIR",850,"アメリカ領ヴァージン諸島",false),
    AS("ASM",16,"アメリカ領サモア",false),
    AE("ARE",784,"アラブ首長国連邦",false),
    DZ("DZA",12,"アルジェリア",false),
    AR("ARG",32,"アルゼンチン",false),
    AW("ABW",533,"アルバ",false),
    AL("ALB",8,"アルバニア",false),
    AM("ARM",51,"アルメニア",false),
    AI("AIA",660,"アンギラ",false),
    AO("AGO",24,"アンゴラ",false),
    AG("ATG",28,"アンティグア・バーブーダ",false),
    AD("AND",20,"アンドラ",false),
    YE("YEM",887,"イエメン",false),
    GB("GBR",826,"イギリス",false),
//    IO("IOT",86,"イギリス領インド洋地域",false),
    VG("VGB",92,"イギリス領ヴァージン諸島",false),
    IL("ISR",376,"イスラエル",false),
    IT("ITA",380,"イタリア",false),
    IQ("IRQ",368,"イラク",false),
    IR("IRN",364,"イラン・イスラム共和国",false),
    IN("IND",356,"インド",false),
    ID("IDN",360,"インドネシア",false),
    WF("WLF",876,"ウォリス・フツナ",false),
    UG("UGA",800,"ウガンダ",false),
    UA("UKR",804,"ウクライナ",false),
    UZ("UZB",860,"ウズベキスタン",false),
    UY("URY",858,"ウルグアイ",false),
    EC("ECU",218,"エクアドル",false),
    EG("EGY",818,"エジプト",false),
    EE("EST",233,"エストニア",false),
    ET("ETH",231,"エチオピア",false),
    ER("ERI",232,"エリトリア",false),
    SV("SLV",222,"エルサルバドル",false),
    AU("AUS",36,"オーストラリア",false),
    AT("AUT",40,"オーストリア",false),
    AX("ALA",248,"オーランド諸島",false),
    OM("OMN",512,"オマーン",false),
    NL("NLD",528,"オランダ",false),
    GH("GHA",288,"ガーナ",false),
    CV("CPV",132,"カーボベルデ",false),
    GG("GGY",831,"ガーンジー",false),
    GY("GUY",328,"ガイアナ",false),
    KZ("KAZ",398,"カザフスタン",false),
    QA("QAT",634,"カタール",false),
//    UM("UMI",581,"合衆国領有小離島",false),
    CA("CAN",124,"カナダ",false),
    GA("GAB",266,"ガボン",false),
    CM("CMR",120,"カメルーン",false),
    GM("GMB",270,"ガンビア",false),
    KH("KHM",116,"カンボジア",false),
    MP("MNP",580,"北マリアナ諸島",false),
    GN("GIN",324,"ギニア",false),
    GW("GNB",624,"ギニアビサウ",false),
    CY("CYP",196,"キプロス",false),
    CU("CUB",192,"キューバ",false),
    CW("CUW",531,"キュラソー",false),
    GR("GRC",300,"ギリシャ",false),
    KI("KIR",296,"キリバス",false),
    KG("KGZ",417,"キルギス",false),
    GT("GTM",320,"グアテマラ",false),
//    GP("GLP",312,"グアドループ",false),
    GU("GUM",316,"グアム",false),
    KW("KWT",414,"旗クウェート",false),
    CK("COK",184,"クック諸島",false),
    GL("GRL",304,"グリーンランド",false),
    CX("CXR",162,"(オーストラリア)の旗クリスマス島",false),
    GE("GEO",268,"グルジア",false),
    GD("GRD",308,"グレナダ",false),
    HR("HRV",191,"クロアチア",false),
    KY("CYM",136,"ケイマン諸島",false),
    KE("KEN",404,"ケニア",false),
    CI("CIV",384,"コートジボワール",false),
    CC("CCK",166,"ココス（キーリング）諸島",false),
    CR("CRI",188,"コスタリカ",false),
    KM("COM",174,"コモロ",false),
    CO("COL",170,"コロンビア",false),
    CG("COG",178,"コンゴ共和国",false),
    CD("COD",180,"コンゴ民主共和国",false),
    SA("SAU",682,"サウジアラビア",false),
    GS("SGS",239,"サウスジョージア・サウスサンドウィッチ諸島",false),
    WS("WSM",882,"サモア",false),
    ST("STP",678,"サントメ・プリンシペ",false),
    BL("BLM",652,"サン・バルテルミー",false),
    ZM("ZMB",894,"ザンビア",false),
//    PM("SPM",666,"サンピエール島・ミクロン島",false),
    SM("SMR",674,"サンマリノ",false),
    MF("MAF",663,"サン・マルタン（フランス領）",false),
    SL("SLE",694,"シエラレオネ",false),
    DJ("DJI",262,"ジブチ",false),
    GI("GIB",292,"ジブラルタル",false),
    JE("JEY",832,"ジャージー",false),
    JM("JAM",388,"ジャマイカ",false),
    SY("SYR",760,"シリア・アラブ共和国",false),
    SG("SGP",702,"シンガポール",false),
//    SX("SXM",534,"シント・マールテン（オランダ領）",false),
    ZW("ZWE",716,"ジンバブエ",false),
    CH("CHE",756,"スイス",false),
    SE("SWE",752,"スウェーデン",false),
    SD("SDN",729,"スーダン",false),
//    SJ("SJM",744,"スヴァールバル諸島およびヤンマイエン島",false),
    ES("ESP",724,"スペイン",false),
    SR("SUR",740,"スリナム",false),
    LK("LKA",144,"スリランカ",false),
    SK("SVK",703,"スロバキア",false),
    SI("SVN",705,"スロベニア",false),
    SZ("SWZ",748,"スワジランド",false),
    SC("SYC",690,"セーシェル",false),
    GQ("GNQ",226,"赤道ギニア",false),
    SN("SEN",686,"セネガル",false),
    RS("SRB",688,"セルビア",false),
    KN("KNA",659,"セントクリストファー・ネイビス",false),
    VC("VCT",670,"セントビンセントおよびグレナディーン諸島",false),
    SH("SHN",654,"セントヘレナ・アセンションおよびトリスタンダクーニャ",false),
    LC("LCA",662,"セントルシア",false),
    SO("SOM",706,"ソマリア",false),
    SB("SLB",90,"ソロモン諸島",false),
    TC("TCA",796,"タークス・カイコス諸島",false),
    TH("THA",764,"タイ",false),
    KR("KOR",410,"韓国",false),
    TW("TWN",158,"台湾（台湾省/中華民国）",false),
    TJ("TJK",762,"タジキスタン",false),
    TZ("TZA",834,"タンザニア",false),
    CZ("CZE",203,"チェコ",false),
    TD("TCD",148,"チャド",false),
    CF("CAF",140,"中央アフリカ共和国",false),
    CN("CHN",156,"中国",false),
    TN("TUN",788,"チュニジア",false),
    KP("PRK",408,"朝鮮民主主義人民共和国",false),
    CL("CHL",152,"チリ",false),
    TV("TUV",798,"ツバル",false),
    DK("DNK",208,"デンマーク",false),
    DE("DEU",276,"ドイツ",false),
    TG("TGO",768,"トーゴ",false),
    TK("TKL",772,"トケラウ",false),
    DO("DOM",214,"ドミニカ共和国",false),
    DM("DMA",212,"ドミニカ国",false),
    TT("TTO",780,"トリニダード・トバゴ",false),
    TM("TKM",795,"トルクメニスタン",false),
    TR("TUR",792,"トルコ",false),
    TO("TON",776,"トンガ",false),
    NG("NGA",566,"ナイジェリア",false),
    NR("NRU",520,"ナウル",false),
    NA("NAM",516,"ナミビア",false),
    AQ("ATA",10,"南極",false),
    NU("NIU",570,"ニウエ",false),
    NI("NIC",558,"ニカラグア",false),
    NE("NER",562,"ニジェール",false),
    EH("ESH",732,"西サハラ",false),
    NC("NCL",540,"ニューカレドニア",false),
    NZ("NZL",554,"ニュージーランド",false),
    NP("NPL",524,"ネパール",false),
    NF("NFK",574,"ノーフォーク島",false),
    NO("NOR",578,"ノルウェー",false),
//    HM("HMD",334,"ハード島とマクドナルド諸島",false),
    BH("BHR",48,"バーレーン",false),
    HT("HTI",332,"ハイチ",false),
    PK("PAK",586,"パキスタン",false),
    VA("VAT",336,"バチカン市国",false),
    PA("PAN",591,"パナマ",false),
    VU("VUT",548,"バヌアツ",false),
    BS("BHS",44,"バハマ",false),
    PG("PNG",598,"パプアニューギニア",false),
    BM("BMU",60,"バミューダ",false),
    PW("PLW",585,"パラオ",false),
    PY("PRY",600,"パラグアイ",false),
    BB("BRB",52,"バルバドス",false),
    PS("PSE",275,"パレスチナ",false),
    HU("HUN",348,"ハンガリー",false),
    BD("BGD",50,"バングラデシュ",false),
    TL("TLS",626,"東ティモール",false),
    PN("PCN",612,"ピトケアン",false),
    FJ("FJI",242,"フィジー",false),
    PH("PHL",608,"フィリピン",false),
    FI("FIN",246,"フィンランド",false),
    BT("BTN",64,"ブータン",false),
//    BV("BVT",74,"ブーベ島",false),
    PR("PRI",630,"プエルトリコ",false),
    FO("FRO",234,"フェロー諸島",false),
    FK("FLK",238,"フォークランド（マルビナス）諸島",false),
    BR("BRA",76,"ブラジル",false),
    FR("FRA",250,"フランス",false),
//    GF("GUF",254,"フランス領ギアナ",false),
    PF("PYF",258,"フランス領ポリネシア",false),
    TF("ATF",260,"フランス領南方・南極地域",false),
    BG("BGR",100,"ブルガリア",false),
    BF("BFA",854,"ブルキナファソ",false),
    BN("BRN",96,"ブルネイ・ダルサラーム",false),
    BI("BDI",108,"ブルンジ",false),
    VN("VNM",704,"ベトナム",false),
    BJ("BEN",204,"ベナン",false),
    VE("VEN",862,"ベネズエラ・ボリバル共和国",false),
    BY("BLR",112,"ベラルーシ",false),
    BZ("BLZ",84,"ベリーズ",false),
    PE("PER",604,"ペルー",false),
    BE("BEL",56,"ベルギー",false),
    PL("POL",616,"ポーランド",false),
    BA("BIH",70,"ボスニア・ヘルツェゴビナ",false),
    BW("BWA",72,"ボツワナ",false),
//    BQ("BES",535,"ボネール、シント・ユースタティウスおよびサバ",false),
    BO("BOL",68,"ボリビア多民族国",false),
    PT("PRT",620,"ポルトガル",false),
    HK("HKG",344,"香港",false),
    HN("HND",340,"ホンジュラス",false),
    MH("MHL",584,"マーシャル諸島",false),
    MO("MAC",446,"マカオ",false),
    MK("MKD",807,"マケドニア",false),
    MG("MDG",450,"マダガスカル",false),
    YT("MYT",175,"マヨット",false),
    MW("MWI",454,"マラウイ",false),
    ML("MLI",466,"マリ",false),
    MT("MLT",470,"マルタ",false),
    MQ("MTQ",474,"マルティニーク",false),
    MY("MYS",458,"マレーシア",false),
    IM("IMN",833,"マン島",false),
    FM("FSM",583,"ミクロネシア連邦",false),
    ZA("ZAF",710,"南アフリカ",false),
    SS("SSD",728,"南スーダン",false),
    MM("MMR",104,"ミャンマー",false),
    MX("MEX",484,"メキシコ",false),
    MU("MUS",480,"モーリシャス",false),
    MR("MRT",478,"モーリタニア",false),
    MZ("MOZ",508,"モザンビーク",false),
    MC("MCO",492,"モナコ",false),
    MV("MDV",462,"モルディブ",false),
    MD("MDA",498,"モルドバ共和国",false),
    MA("MAR",504,"モロッコ",false),
    MN("MNG",496,"モンゴル",false),
    ME("MNE",499,"モンテネグロ",false),
    MS("MSR",500,"モントセラト",false),
    JO("JOR",400,"ヨルダン",false),
    LA("LAO",418,"ラオス人民民主共和国",false),
    LV("LVA",428,"ラトビア",false),
    LT("LTU",440,"リトアニア",false),
    LY("LBY",434,"リビア",false),
    LI("LIE",438,"リヒテンシュタイン",false),
    LR("LBR",430,"リベリア",false),
    RO("ROU",642,"ルーマニア",false),
    LU("LUX",442,"ルクセンブルク",false),
    RW("RWA",646,"ルワンダ",false),
    LS("LSO",426,"レソト",false),
    LB("LBN",422,"レバノン",false),
//    RE("REU",638,"レユニオン",false),
    RU("RUS",643,"ロシア連邦",false);

    private String name;
    private String alpha3;
    private int numeric;
    private boolean support;

    private Country(String alpha3, int numeric, String name, boolean support) {
        this.alpha3 = alpha3;
        this.numeric = numeric;
        this.name = name;
        this.support = support;
    }

    public String getName() {
        return name;
    }

    public boolean isSupport() {
        return support;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public int getNumeric() {
        return numeric;
    }
}
