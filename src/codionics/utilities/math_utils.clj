(ns codionics.utilities.math-utils
  (:refer-clojure :exclude [replace])
  (:use [clojure.string :only [replace]]))

(defn seq-to-base
  "Returns the sequence of digits after converting a non-negative decimal number n to an arbitrary base b.
   Individual digits are represented with their integer values, e.g. 15 is [1 5] in base 10, [1 1 1 1] in
   base 2 and [15] in base 16."
  [n b]
  {:pre [(and (>= n 0) (>= b 0))]}
  (cond (= n 0) [0]
    :else (loop [num n l []]
            (if (= num 0)
              l
              (recur (quot num b) (cons (rem num b) l))))))

(defn decimal-to-base
  "Returns a string representation of a non-negative decimal number n to an arbitrary base b (<= 36)."
  [n b]
  {:pre [(and (>= n 0) (>= b 0) (< b 37))]}
  (let [alphabets (vec (map char (range 65 91)))
        num-seq (seq-to-base n b)]
    (if (<= b 10)
      (apply str num-seq)
      (apply str (map #(if (> % 9)
                         (alphabets (- % 10))
                         %) num-seq)))))