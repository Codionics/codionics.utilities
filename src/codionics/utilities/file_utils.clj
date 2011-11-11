(ns codionics.utilities.file-utils
  (:use [clojure.string :only [join]])
  (:import [java.io File]))

(defn current-dir
  "Returns the current working directory."
  []
  (System/getProperty "user.dir"))

(defn get-absolute-path
  "Joins the path of the dir and file-in-dir."
  ([filename] (get-absolute-path (current-dir) filename))
  ([dir file-in-dir] (join File/separator [dir file-in-dir])))

(defn exists?
  "Returns true if the dir or the file-in-dir exists in the dir."
  ([dir] (exists? dir nil))
  ([dir file-in-dir]
     (.exists (File.
               (if (nil? file-in-dir)
                 dir
                 (get-absolute-path dir file-in-dir))))))