(ns icu4clj.text.unicode-set-patterns
  (:refer-clojure :exclude [complement])
  (:require
   [clojure.string :as string]

   [icu4clj.text.unicode-characters :as icu-tuc]))

(defn complement [pattern]
  (str "[^" pattern "]"))

(defn union [& patterns]
  (str "[" (string/join patterns) "]"))

(defn difference [& patterns]
  (str "[" (string/join " - " patterns) "]"))

(def non-breaking-space-pattern
  (union
    icu-tuc/no-break-space-character
    icu-tuc/figure-space-character
    icu-tuc/narrow-no-break-space))

(def space-separator-pattern
  "[:Zs:]")
(def line-separator-pattern
  "[:Zl:]")
(def paragraph-separator-pattern
  "[:Zp:]")

(def other-whitespace-pattern
  (union
    icu-tuc/horizontal-tabulation-character
    icu-tuc/line-feed-character
    icu-tuc/vertical-tabulation-character
    icu-tuc/form-feed-character
    icu-tuc/carriage-return-character
    icu-tuc/file-separator-character
    icu-tuc/group-separator-character
    icu-tuc/record-separator-character
    icu-tuc/unit-separator-character))

(def space-pattern
  (union
    space-separator-pattern
    line-separator-pattern
    paragraph-separator-pattern
    other-whitespace-pattern))

(def whitespace-pattern
  (difference
    space-pattern
    non-breaking-space-pattern))

(def non-whitespace-pattern
  (complement whitespace-pattern))

(def ascii-digit-pattern
  "[0-9]")

(def non-ascii-digit-pattern
  (complement ascii-digit-pattern))

(def lowercase-ascii-alphabetic-pattern
  "[a-z]")

(def non-lowercase-ascii-alphabetic-pattern
  (complement lowercase-ascii-alphabetic-pattern))

(def uppercase-ascii-alphabetic-pattern
  "[A-Z]")

(def non-uppercase-ascii-alphabetic-pattern
  (complement uppercase-ascii-alphabetic-pattern))

(def ascii-alphabetic-pattern
  (union
    lowercase-ascii-alphabetic-pattern
    uppercase-ascii-alphabetic-pattern))

(def non-ascii-alphabetic-pattern
  (complement ascii-alphabetic-pattern))

(def lowercase-ascii-alphanumeric-pattern
  (union
    ascii-digit-pattern
    lowercase-ascii-alphabetic-pattern))

(def non-lowercase-ascii-alphanumeric-pattern
  (complement lowercase-ascii-alphanumeric-pattern))

(def uppercase-ascii-alphanumeric-pattern
  (union
    ascii-digit-pattern
    uppercase-ascii-alphabetic-pattern))

(def non-uppercase-ascii-alphanumeric-pattern
  (complement uppercase-ascii-alphanumeric-pattern))

(def ascii-alphanumeric-pattern
  (union
    ascii-digit-pattern
    lowercase-ascii-alphabetic-pattern
    uppercase-ascii-alphabetic-pattern))

(def non-ascii-alphanumeric-pattern
  (complement ascii-alphanumeric-pattern))
