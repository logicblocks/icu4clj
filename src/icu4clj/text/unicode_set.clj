(ns icu4clj.text.unicode-set
  (:refer-clojure :exclude [complement])
  (:import
   [com.ibm.icu.text
    UnicodeSet
    UnicodeSet$EntryRange]))

(defn ^UnicodeSet unicode-set [set-or-pattern]
  (if (instance? UnicodeSet set-or-pattern)
    set-or-pattern
    (doto (UnicodeSet. ^String set-or-pattern)
      (.freeze))))

(defn ^UnicodeSet complement [set-or-pattern]
  (.complement (unicode-set set-or-pattern)))

(defn codepoint-ranges [set-or-pattern]
  (vec (mapv (fn [^UnicodeSet$EntryRange range]
               (vec [(.codepoint range) (.codepointEnd range)]))
         (.ranges (unicode-set set-or-pattern)))))

(defn character-vector [set-or-pattern]
  (vec (unicode-set set-or-pattern)))

(defn character-set [set-or-pattern]
  (set (unicode-set set-or-pattern)))
