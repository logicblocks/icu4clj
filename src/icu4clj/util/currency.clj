(ns icu4clj.util.currency
  (:import
   [java.time Instant]
   [java.util Date]))

(defrecord Currency
           [display-name
            currency-code
            numeric-code
            symbol
            rounding-increment
            default-fraction-digits])

(defn available?
  ([currency]
   (available? currency (Instant/now) nil))
  ([currency ^Instant from ^Instant to]
   (let [code
         (if (instance? com.ibm.icu.util.Currency currency)
           (.getCurrencyCode ^com.ibm.icu.util.Currency currency)
           currency)]
     (com.ibm.icu.util.Currency/isAvailable
       code (when from (Date/from from)) (when to (Date/from to))))))

(defn historic? [^String code]
  (not (available? code)))

(defn wrap-currency [^com.ibm.icu.util.Currency currency]
  (map->Currency
    {::instance               currency
     :display-name            (.getDisplayName currency)
     :currency-code           (.getCurrencyCode currency)
     :numeric-code            (.getNumericCode currency)
     :symbol                  (.getSymbol currency)
     :rounding-increment      (.getRoundingIncrement currency)
     :default-fraction-digits (.getDefaultFractionDigits currency)}))

(defn available-currencies []
  (sort-by :currency-code
    (mapv wrap-currency
      (vec (com.ibm.icu.util.Currency/getAvailableCurrencies)))))

(defn available-currency-codes []
  (map :currency-code (available-currencies)))
