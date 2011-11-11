(ns codionics.utilities.debug-utils)

;; the following macro is taken from John Lawrence Aspden's blog -
;; http://www.learningclojure.com/search?q=astonishing+macro

(defmacro def-let
  "like let, but binds the expressions globally."
  [bindings & more]
  (let [let-expr (macroexpand `(let ~bindings))
        names-values (partition 2 (second let-expr))
        defs   (map #(cons 'def %) names-values)]
    (concat (list 'do) defs more)))