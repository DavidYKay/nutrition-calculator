(ns ^:figwheel-always nutrition-calculator.dev
  (:require
    [nutrition-calculator.core :as core]
    [nutrition-calculator.session :refer [app-state]]
    [nutrition-calculator.actions :refer [add-condition]]
  ))

(def dummy-app-state {:conditions {4 {:id 4 :name "Hypertension"}
                                   5 {:id 5 :name "Diabetes"}
                                   6 {:id 6 :name "Asthma"}
                                   7 {:id 7 :name "Trina-itis"}
                                   8 {:id 8 :name "Osteoperosis"}}})

(defn init-dev-data []
  (swap! app-state #(merge % dummy-app-state)))

(init-dev-data)
