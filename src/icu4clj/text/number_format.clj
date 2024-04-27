(ns icu4clj.text.number-format
  (:require
   [icu4clj.util.u-locale :as u-locale])
  (:import
   [com.ibm.icu.util ULocale$Category]))

(defrecord DecimalFormatSymbols
           [locale
            u-locale
            digits
            zero-digit
            infinity
            nan
            minus-sign
            plus-sign
            exponent-multiplication-sign
            decimal-separator
            monetary-decimal-separator
            grouping-separator
            monetary-grouping-separator
            exponent-separator
            per-mill
            percent
            currency-symbol
            international-currency-symbol
            digit
            significant-digit
            pad-escape
            pattern-separator])

(defn wrap-decimal-format-symbols
  [^com.ibm.icu.text.DecimalFormatSymbols symbols]
  (map->DecimalFormatSymbols
    {::instance                    symbols
     :locale                       (.getLocale symbols)
     :u-locale
     (u-locale/wrap-u-locale (.getULocale symbols))
     :digits                       (vec (.getDigitStrings symbols))
     :zero-digit                   (String/valueOf (.getZeroDigit symbols))
     :infinity                     (.getInfinity symbols)
     :nan                          (.getNaN symbols)
     :minus-sign                   (.getMinusSignString symbols)
     :plus-sign                    (.getPlusSignString symbols)
     :exponent-multiplication-sign (.getExponentMultiplicationSign symbols)
     :decimal-separator            (.getDecimalSeparatorString symbols)
     :monetary-decimal-separator
     (.getMonetaryDecimalSeparatorString symbols)
     :grouping-separator           (.getGroupingSeparatorString symbols)
     :monetary-grouping-separator
     (.getMonetaryGroupingSeparatorString symbols)
     :exponent-separator           (.getExponentSeparator symbols)
     :per-mill                     (.getPerMillString symbols)
     :percent                      (.getPercentString symbols)
     :currency-symbol              (.getCurrencySymbol symbols)
     :international-currency-symbol
     (.getInternationalCurrencySymbol symbols)
     :digit
     (String/valueOf (.getDigit symbols))
     :significant-digit
     (String/valueOf (.getSignificantDigit symbols))
     :pad-escape
     (String/valueOf (.getPadEscape symbols))
     :pattern-separator
     (String/valueOf (.getPatternSeparator symbols))}))

(defn unwrap-decimal-format-symbols
  ^com.ibm.icu.text.DecimalFormatSymbols
  [^DecimalFormatSymbols symbols]
  (::instance symbols))

(defn decimal-format-symbols
  ([]
   (decimal-format-symbols
     (u-locale/default-locale :format)))
  ([locale]
   (wrap-decimal-format-symbols
     (cond
       (instance? java.util.Locale locale)
       (com.ibm.icu.text.DecimalFormatSymbols/getInstance
         ^java.util.Locale locale)

       (instance? com.ibm.icu.util.ULocale locale)
       (com.ibm.icu.text.DecimalFormatSymbols/getInstance
         ^com.ibm.icu.util.ULocale locale)

       (instance? icu4clj.util.u_locale.ULocale locale)
       (com.ibm.icu.text.DecimalFormatSymbols/getInstance
         ^com.ibm.icu.util.ULocale (u-locale/unwrap-u-locale locale))

       :else (com.ibm.icu.text.DecimalFormatSymbols/getInstance)))))
