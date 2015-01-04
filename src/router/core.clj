(ns router.core
  (:require [secretary.core :as sty :include-macroes true]))



(defmacro match
  "Match a route and return a value."
  [route value]
  `(sty/defroute ~route {:as p#} [~value p#]))
