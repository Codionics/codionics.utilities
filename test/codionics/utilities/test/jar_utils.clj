(ns codionics.utilities.test.jar-utils
  (:use [codionics.utilities.test.core]
        [codionics.utilities.jar-utils]
        [codionics.utilities.str-utils]
        [codionics.utilities.file-utils]
        [midje.sweet])
  (:import [java.io File]
           [java.util.jar JarFile JarEntry]))

;; Globals
(def *manifest* "MANIFEST.MF")

;; Tests
(background (before :facts (setup))
            (after :facts (teardown)))

(fact (instance? JarFile (jar-file *test-file-name*)) => true)
(fact (instance? JarEntry (jar-entry (jar-file *test-file*) *manifest*)) => true)
(fact (some #(= *test-file* %) (jar-files (current-dir))) => true?)
(fact (without (contents-of-a-file-in-jar (jar-file (last (jar-files (current-dir)))) *manifest*) "\r" "\n") => "")