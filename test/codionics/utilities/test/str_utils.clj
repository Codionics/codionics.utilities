(ns codionics.utilities.test.str-utils
  (:use [codionics.utilities.str-utils :only [between without]]
        [midje.sweet]))

;; Globals
(def *test-string* "and miles to go before I sleep")
(def *test-string-with-punctuations* "and miles\n to, go before, I sleep\n")

;; Tests

(fact (between *test-string* "miles" "I") => (just " to go before "))
(fact (between *test-string* "miles" "slept") => (just " to go before I sleep"))
(fact (between *test-string* "miley" "I") => (throws AssertionError (contains "Assert failed:")))

(fact (without (between *test-string-with-punctuations* "miles" "slept") "\n") => (just " to, go before, I sleep"))
(fact (without (between *test-string-with-punctuations* "miles" "slept") "\n" ",") => (just " to go before I sleep"))
(fact (without (between *test-string-with-punctuations* "miles" "slept") "\n" " " ",") => (just "togobeforeIsleep"))
