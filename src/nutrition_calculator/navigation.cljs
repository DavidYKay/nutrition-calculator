(ns nutrition-calculator.navigation
  (:require [nutrition-calculator.session :refer [app-state]]))

(defn go [s]
  (swap! app-state assoc :current-screen s))

(defn interview-step [cid]
  (swap! app-state assoc :current-interview-condition cid))
