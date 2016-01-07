(ns nutrition-calculator.router
  (:require
    [nutrition-calculator.session :refer [app-state]]
    [nutrition-calculator.components.nutrition-calculator :as nutrition-calculator]))

(defn current-screen []
  (let [nav-state (:current-screen @app-state)]
    (cond (= :patient nav-state) [nutrition-calculator/component]
          :else [:div [:h2 "Unknown nav state"]
                 [:p "State was: " nav-state]])))
