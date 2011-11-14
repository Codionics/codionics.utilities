(ns codionics.utilities.test.math-utils
  (:use [clojure.string :only [upper-case]]
    [codionics.utilities.test.core]
    [codionics.utilities.math-utils :only [seq-to-base decimal-to-base]]
    [midje.sweet]))

;; Tests
(fact (seq-to-base -10 2) => (throws AssertionError (contains "Assert failed:")))
(fact (seq-to-base 10 -2) => (throws AssertionError (contains "Assert failed:")))

(fact (seq-to-base 1234501 10) => [1 2 3 4 5 0 1])
(fact (seq-to-base 0 11) => [0])
(fact (seq-to-base 9 2) => [1 0 0 1])
(fact (seq-to-base Integer/MAX_VALUE 42) => [16 18 5 24 15 1])

(let [n (rand-int 100000)]
  (fact (seq-to-base n n) => [1 0]))

(fact (decimal-to-base 1234 37) => (throws AssertionError (contains "Assert failed:")))

(fact (decimal-to-base 1234 2) => (Integer/toString 1234 2))
(fact (decimal-to-base 1234 3) => (Integer/toString 1234 3))
(fact (decimal-to-base 1234 4) => (Integer/toString 1234 4))
(fact (decimal-to-base 1234 16) => (upper-case (Integer/toString 1234 16)))
(fact (decimal-to-base 17 18) => (upper-case (Integer/toString 17 18)))
(fact (decimal-to-base Integer/MAX_VALUE 35) => (upper-case (Integer/toString Integer/MAX_VALUE 35)))