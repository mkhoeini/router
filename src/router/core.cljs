(ns router.core
  (:require [secretary.core :as secretary]
            [goog.history.EventType :as EventType]
            [goog.events :as events])
  (:import [goog.history Html5History]
           [goog Uri]))



;; The main history manager
(def history (Html5History.))



;; Default configurations for history
(def default-config
  {:use-fragment false
   :path-prefix ""
   :enabled true})



(defn config!
  "Config the history manager."
  ([] (config! default-config))
  ([c]
   (let [conf (merge default-config c)]
     (doto history
       (.setUseFragment (:use-fragment conf))
       (.setPathPrefix (:path-prefix conf))
       (.setEnabled (:enabled conf))))))



(defn enable-router!
  "Enable or disable the history manager."
  ([] (enable-router! true))
  ([en?] (.setEnabled history en?)))

(defn disable-router! []
  (enable-router! false))



;; The callback to be called when navigation happens
(def ^:private callback (atom identity))

(defn set-callback!
  "Set the callback function for navigation event."
  [cb]
  (reset! callback cb))



;; Listen to navigation events and call the callback
(events/listen history EventType/NAVIGATE
               (fn [nav]
                 (let [params (secretary/dispatch! (.-token nav))]
                   (apply @callback params))))



(defn current-route
  "Return the current route"
  []
  (.getToken history))

(defn navigate!
  "Go to a new route"
  [nr]
  (.setToken history nr))



(defn prevent-default-refresh!
  "Prevent links from redirecting."
  []
  (events/listen js/document "click"
                 (fn [e]
                   (let [path (->> (.. e -target -href)
                                   (.parse Uri)
                                   (.getPath))]
                     (when (secretary/locate-route path)
                       (.setToken history path)
                       (.preventDefault e))))))
