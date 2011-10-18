(ns codionics.utilities.test.str-utils
  (:use [codionics.utilities.str-utils :only [between]]
        [midje.sweet]))

;; Globals
(def *test-string* "and miles to go before I sleep")

;; Tests

(fact (between *test-string* "miles" "I") => (just " to go before "))
(fact (between *test-string* "miles" "slept") => (just " to go before I sleep"))
(fact (between *test-string* "miley" "I") => (throws AssertionError (contains "Assert failed:")))