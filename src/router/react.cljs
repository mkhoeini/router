(ns router.react
  (:require [router.core :refer (set-callback!)]))



(def ^:private dom-node (atom nil))
(def ^:private callback (atom (fn [v p] [v p])))

(defn- on-navigation
  "This is the callback that will be called uppon navigation event
  to change the page component."
  [value params]
  (let [[v p] (@callback value params)
        elem (if (ifn? v) (v p) v)]
    (.render js/React elem @dom-node)))



(defn initiate!
  "Set the router's callback to render the current routes element to the supplied DOM node."
  [node & [cb]]
  (reset! dom-node node)
  (when-not (nil? cb) (reset! callback cb))
  (set-callback! on-navigation))
