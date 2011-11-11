(ns codionics.utilities.test.file-utils
  (:use [codionics.utilities.file-utils]
        [midje.sweet]))

;; Globals
(def *temp-file-name* "temp.txt")

;; Tests
(fact (exists? (current-dir)) => true)
(fact (current-dir) => (System/getProperty "user.dir"))
(fact (get-absolute-path *temp-file-name*) => (str (current-dir) "/" *temp-file-name*))