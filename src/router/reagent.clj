(ns router.reagent
  (:require [router.core :as rt]
            [reagent.core :as reagent]))



(defmacro match
  "An alternative to router.core/match to fasilitate Reagent."
  [route param elem]
  (let [f `(fn [~param] (reagent/as-element ~elem))]
    `(rt/match ~route ~f)))
