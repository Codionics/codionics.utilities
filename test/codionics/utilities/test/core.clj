(ns codionics.utilities.test.core
  (:use [codionics.utilities.core]
        [codionics.utilities.jar-utils]
        [codionics.utilities.file-utils])
  (:import [java.io File]))

;; Globals
(def *test-string* "and miles to go before I sleep")
(def *test-string-with-punctuations* "and miles\n to, go before, I sleep\n")

(def *test-file-name* "temp.jar")
(def *test-file* (File. (get-absolute-path *test-file-name*)))

;; Setup and Teardown
(defn setup []
  (create-jar-file *test-file-name*))

(defn teardown []
  (.delete (File. *test-file-name*)))
