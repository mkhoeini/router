(ns router.react
  (:require [router.core :refer (set-callback!)]))



(def ^:private dom-node (atom nil))

(defn- on-navigation
  "This is the callback that will be called uppon navigation event
  to change the page component."
  [value params]
  (let [elem (if (ifn? value) (value params) value)]
    (.render js/React elem @dom-node)))



(defn initiate!
  "Set the router's callback to render the current routes element to the supplied DOM node."
  [node]
  (reset! dom-node node)
  (set-callback! on-navigation))
