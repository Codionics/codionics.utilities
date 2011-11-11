(ns codionics.utilities.lein-utils
  (:refer-clojure :exclude [replace])
  (:use [clojure.string :only [join replace]]
        [clojure.contrib.jar :only [jar-file? filenames-in-jar]]
        [clojure.contrib.string :only [substring?]]
        [codionics.utilities.str-utils :only [between]]
        [codionics.utilities.file-utils :only [exists?]]
        [codionics.utilities.debug-utils :only [def-let]]
        [codionics.utilities.jar-utils :only [jar-entry jar-file jar-files contents-of-a-file-in-jar]])
  (:import [java.io File]))

;; Globals
(def *dependencies* ":dependencies")
(def *dev-dependencies* ":dev-dependencies")
(def *project-file-name* "project.clj")

;; Functions
(defn lein-jar-file?
  "Returns true if a jar file contains a project.clj file."
  [jar-filename]
  (let [f (if (instance? File jar-filename)
            jar-filename
            (File. jar-filename))
        jar-fil (jar-file f)]
    (and (jar-file? f)
         (not (nil? (jar-entry jar-fil *project-file-name*))))))

(defn lein-dir-structure?
  "Returns true if dir contains a /lib directory and a project.clj file."
  [dir]
  (and (exists? dir)
       (exists? (join File/separator [dir "lib"]))
       (exists? dir *project-file-name*)))

(defn read-section
  "Returns the content between the start and end from the contents of a
   lein project.clj file (lein-project-file-contents)."
  [lein-project-file-contents start end]
  (if (or (nil? lein-project-file-contents)
          (not (substring? start lein-project-file-contents)))
    ""
    (let [content (between lein-project-file-contents start end)
          clean-content (replace (replace content #"\n" "") " " "")]
      clean-content)))

(defn jar-filename-dependencies-list
  "Returns a list of the jar-file-name and the contents between start and end."
  [f start end]
  (let [jf (jar-file f)]
    (list (.getName jf) (read-section (contents-of-a-file-in-jar jf *project-file-name*) start end))))

(defn- depending-on
  [lein-project-dir lib start end]
  (let [lein-jars (filter #(lein-jar-file? %) (jar-files lein-project-dir))
        jar-filename-dependencies-seq (map #(jar-filename-dependencies-list % start end) lein-jars)
        jar-files-with-dependency-on-lib (filter #(substring? lib (second %)) jar-filename-dependencies-seq)]
    (apply concat jar-files-with-dependency-on-lib)))

(defn projects-depending-on
  "Returns a list of jar files from lein-project-dir which depend on lib.
   The first element of the list is the jar file name and the second element
   is the content of the :dependencies section from the project.clj file."
  [lein-project-dir lib]
  (depending-on lein-project-dir lib *dependencies* *dev-dependencies*))

(defn projects-dev-depending-on
  "Returns a list of jar files from lein-project-dir which depend on lib.
   The first element of the list is the jar file name and the second element
   is the content of the :dev-dependencies section from the project.clj file."
  [lein-project-dir lib]
  ;; giving the second argument as "junk" will cause it to be read till the end
  ;; of the file or until "junk" itself is encountered in the file.
  (depending-on lein-project-dir lib *dev-dependencies* "junk"))
