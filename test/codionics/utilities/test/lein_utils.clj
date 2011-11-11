(ns codionics.utilities.test.lein-utils
  (:use [codionics.utilities.test.core]
        [codionics.utilities.jar-utils]
        [codionics.utilities.lein-utils]
        [midje.sweet]))

;; Tests
(background (before :facts (setup))
            (after :facts (teardown)))

(fact (lein-jar-file? *test-file*) => false)
(fact (read-section *test-string-with-punctuations* "miles" "slept") => "to,gobefore,Isleep")

(comment
  ;; examples
  (jar-filename-dependencies-list (File. "/home/manoj/dev/codionics.utilities/lib/unifycle-0.5.0.jar") ":dependencies" ":dev-dependencies")
  (projects-depending-on "/home/manoj/dev/codionics.utilities/lib" "clojars")
  (projects-dev-depending-on "/home/manoj/dev/codionics.utilities/lib" "clojars"))