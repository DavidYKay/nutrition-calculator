(ns nutrition-calculator.session
  (:require [reagent.core :as r :refer [atom]]))

(defonce app-state
  (atom {}))

(defonce user-input
  (atom {:lbm ""}))
