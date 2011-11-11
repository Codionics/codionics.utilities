(ns codionics.utilities.test.str-utils
  (:use [codionics.utilities.test.core]
        [codionics.utilities.str-utils :only [between without]]
        [midje.sweet]))

;; Tests
(fact (between *test-string* "miles" "I") => (just " to go before "))
(fact (between *test-string* "miles" "slept") => (just " to go before I sleep"))
(fact (between *test-string* "miley" "I") => (throws AssertionError (contains "Assert failed:")))

(fact (without (between *test-string-with-punctuations* "miles" "slept") "\n") => (just " to, go before, I sleep"))
(fact (without (between *test-string-with-punctuations* "miles" "slept") "\n" ",") => (just " to go before I sleep"))
(fact (without (between *test-string-with-punctuations* "miles" "slept") "\n" " " ",") => (just "togobeforeIsleep"))





