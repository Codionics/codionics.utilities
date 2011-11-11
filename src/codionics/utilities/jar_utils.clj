(ns codionics.utilities.jar-utils
  (:use [clojure.contrib.jar :only [jar-file? filenames-in-jar]]
        [codionics.utilities.file-utils :only [exists?]])
  (:import [java.io File FileOutputStream]
           [java.util.jar JarFile Manifest JarOutputStream]))

;; Globals
(def *temp-file-name* "temp.jar")

;; Functions
(defn create-jar-file
  "Creates a jar file with a manifest."
  ([] (create-jar-file *temp-file-name*))
  ([name] (let [name-with-extn (if-not (or (.endsWith name ".jar")
                                               (.endsWith name ".JAR"))
                                     (str name ".jar")
                                     name)
                    out (JarOutputStream. (FileOutputStream. name-with-extn) (Manifest.))]
            (.close out))))

(defn jar-file
  "Returns a java.util.jar.JarFile for a java.io.File f."
  [f]
  (JarFile. f))

(defn jar-entry
  "Returns JarEntry for file-in-jar in the jar-file."
  [jar-file file-in-jar]
  (let [fil (filter #(.endsWith % file-in-jar) (filenames-in-jar jar-file))]
    (if-not (empty? fil)
      (.getJarEntry jar-file (first fil)))))

(defn jar-files
  "Returns all the jar files (as java.io.File objects) in the dir."
  [dir]
  {:pre [(exists? dir)]}
  (filter #(jar-file? %) (file-seq (File. dir))))

(defn contents-of-a-file-in-jar
  "Returns the contents of filename-in-jar in jar-file."
  [jar-file filename-in-jar]
  (let [file-in-jar (jar-entry jar-file filename-in-jar)]
    (if (nil? file-in-jar)
      ""
      (slurp (.getInputStream jar-file file-in-jar)))))
