(ns ^:figwheel-always nutrition-calculator.core
  (:require
            ; [nutrition-calculator.router :as router]
             [nutrition-calculator.components.nutrition-calculator :as nutrition-calculator]
            [reagent.core :as r]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console. In less than a second. Beauty.")

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

;; Navigation


;; Main UI

(defn main-ui []
  [:div
   ;(router/current-screen)
   (nutrition-calculator/component)
   ])

(r/render-component [main-ui]
                    (js/document.getElementById "app"))
