(ns codionics.utilities.str-utils
  (:refer-clojure :exclude [replace])
  (:use [clojure.string :only [replace]]))

(defn- betw [s start start-index end-index]
  {:pre [(and (>= start-index 0) (< start-index (count s)))]}
  (subs s (+ start-index (count start)) end-index))

(defn between [s start end]
  "Returns the substring of s between start and end i.e. after start and before end.
   If end is not in s, returns the substring of s after start till the end (length of string).
   The start has to be in s, else an exception is thrown."
  (let [len (count s)
        start-index (.indexOf s start)
        end-index (.indexOf s end)]
    (if (or (= end-index -1) (> end-index len))
      (betw s start start-index len)
      (betw s start start-index end-index))))

(defn without [s & args]
  "Replaces each of the args with an empty string (i.e. \"\") and returns the resulting string."
  (if (empty? args)
     s
     (recur (replace s (str (first args)) "") (rest args))))
