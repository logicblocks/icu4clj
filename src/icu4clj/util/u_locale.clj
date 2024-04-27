(ns icu4clj.util.u-locale
  (:import
   [com.ibm.icu.util ULocale$Category]))

(defrecord ULocale
           [name
            base-name
            language
            script
            country
            variant
            display-name
            display-name-with-dialect
            display-language
            display-language-with-dialect
            display-script
            display-country
            display-variant
            iso3-language
            iso3-country
            character-orientation
            line-orientation
            right-to-left?])

(defn wrap-u-locale [^com.ibm.icu.util.ULocale locale]
  (map->ULocale
    {::instance                     locale
     :name                          (.getName locale)
     :base-name                     (.getBaseName locale)
     :language                      (.getLanguage locale)
     :script                        (.getScript locale)
     :country                       (.getCountry locale)
     :variant                       (.getVariant locale)
     :display-name                  (.getDisplayName locale)
     :display-name-with-dialect     (.getDisplayNameWithDialect locale)
     :display-language              (.getDisplayLanguage locale)
     :display-language-with-dialect (.getDisplayLanguageWithDialect locale)
     :display-script                (.getDisplayScript locale)
     :display-country               (.getDisplayCountry locale)
     :display-variant               (.getDisplayVariant locale)
     :iso3-language                 (.getISO3Language locale)
     :iso3-country                  (.getISO3Country locale)
     :character-orientation         (.getCharacterOrientation locale)
     :line-orientation              (.getLineOrientation locale)
     :right-to-left?                (.isRightToLeft locale)}))

(defn unwrap-u-locale ^com.ibm.icu.util.ULocale [^ULocale locale]
  (::instance locale))

(def categories
  {:format  ULocale$Category/FORMAT
   :display ULocale$Category/DISPLAY})

(def locales
  {:english       (wrap-u-locale com.ibm.icu.util.ULocale/ENGLISH)
   :french        (wrap-u-locale com.ibm.icu.util.ULocale/FRENCH)
   :german        (wrap-u-locale com.ibm.icu.util.ULocale/GERMAN)
   :italian       (wrap-u-locale com.ibm.icu.util.ULocale/ITALIAN)
   :japanese      (wrap-u-locale com.ibm.icu.util.ULocale/JAPANESE)
   :korean        (wrap-u-locale com.ibm.icu.util.ULocale/KOREAN)
   :chinese       (wrap-u-locale com.ibm.icu.util.ULocale/CHINESE)
   :simplified-chinese
   (wrap-u-locale com.ibm.icu.util.ULocale/SIMPLIFIED_CHINESE)
   :traditional-chinese
   (wrap-u-locale com.ibm.icu.util.ULocale/TRADITIONAL_CHINESE)
   :france        (wrap-u-locale com.ibm.icu.util.ULocale/FRANCE)
   :germany       (wrap-u-locale com.ibm.icu.util.ULocale/GERMANY)
   :italy         (wrap-u-locale com.ibm.icu.util.ULocale/ITALY)
   :japan         (wrap-u-locale com.ibm.icu.util.ULocale/JAPAN)
   :korea         (wrap-u-locale com.ibm.icu.util.ULocale/KOREA)
   :china         (wrap-u-locale com.ibm.icu.util.ULocale/CHINA)
   :prc           (wrap-u-locale com.ibm.icu.util.ULocale/CHINA)
   :taiwan        (wrap-u-locale com.ibm.icu.util.ULocale/TAIWAN)
   :uk            (wrap-u-locale com.ibm.icu.util.ULocale/UK)
   :us            (wrap-u-locale com.ibm.icu.util.ULocale/US)
   :canada        (wrap-u-locale com.ibm.icu.util.ULocale/CANADA)
   :canada-french (wrap-u-locale com.ibm.icu.util.ULocale/CANADA_FRENCH)})

(defn available-locales []
  (mapv wrap-u-locale
    (vec (com.ibm.icu.util.ULocale/getAvailableLocales))))

(defn iso-languages []
  (vec (com.ibm.icu.util.ULocale/getISOLanguages)))

(defn iso-countries []
  (vec (com.ibm.icu.util.ULocale/getISOCountries)))

(defn default-locale
  ([]
   (wrap-u-locale
     (com.ibm.icu.util.ULocale/getDefault)))
  ([category]
   (wrap-u-locale
     (com.ibm.icu.util.ULocale/getDefault
       (get categories category category)))))

(defn locale [keyword-or-locale-id]
  (if (contains? locales keyword-or-locale-id)
    (get locales keyword-or-locale-id)
    (wrap-u-locale
      (com.ibm.icu.util.ULocale/createCanonical
        ^String keyword-or-locale-id))))
