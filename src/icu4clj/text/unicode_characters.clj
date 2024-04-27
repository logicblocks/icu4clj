(ns icu4clj.text.unicode-characters
  (:require
   [icu4clj.text.unicode-set :as icu-tus]))

(defn whitespace-character? [value]
  (cond
    (char? value) (Character/isWhitespace ^char value)
    (integer? value) (Character/isWhitespace (long value))
    (and (string? value) (= (.count (.codePoints ^String value)) 1))
    (Character/isWhitespace (long (.codePointAt ^String value 0)))
    :else false))

(defn non-whitespace-character? [value]
  (not (whitespace-character? value)))

(defn space-separator-character? [value]
  (contains?
    (icu-tus/character-set "[:Zs:]")
    (cond
      (char? value) (Character/toString ^char value)
      (integer? value) (Character/toString (long value))
      :else (str value))))

(def horizontal-tabulation-character
  "\u005Ct")
(def line-feed-character
  "\u005Cn")
(def vertical-tabulation-character
  "\u005Cu000B")
(def form-feed-character
  "\u005Cf")
(def carriage-return-character
  "\u005Cr")
(def file-separator-character
  "\u005Cu001C")
(def group-separator-character
  "\u005Cu001D")
(def record-separator-character
  "\u005Cu001E")
(def unit-separator-character
  "\u005Cu001F")

(def no-break-space-character
  "\u005Cu00A0")
(def figure-space-character
  "\u005Cu2007")
(def narrow-no-break-space
  "\u005Cu202F")
