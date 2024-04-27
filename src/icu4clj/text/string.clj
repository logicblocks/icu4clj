(ns icu4clj.text.string)

(defn codepoint-seq [^String value]
  (iterator-seq (.iterator (.codePoints value))))
