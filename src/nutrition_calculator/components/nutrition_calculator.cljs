(ns ^:figwheel-always nutrition-calculator.components.nutrition-calculator
  (:require [reagent.core :as r]
            [nutrition-calculator.session :refer [app-state user-input]]
            ;[nutrition-calculator.model.romaniello.alpha :as alpha]
            [nutrition-calculator.model.romaniello.recomp :as recomp]
            [nutrition-calculator.model.romaniello.common :refer [lbm maintenance-calories Vitals]]
            ))

(defn make-input [{:keys [name label placeholder]}]
  [:div
   [:div label]
   [:input
    {:aria-describedby "exampleHelpText"
     :placeholder placeholder
     :type "text"
     :value (name @user-input)
     :on-change (fn [ev]
                  (swap! user-input assoc name (-> ev .-target .-value)))}]])

(defn vitals []
  (Vitals. (:weight @user-input)
           (:bfp @user-input)))

(defn diet-row [k]
  (let [v (vitals)
        current-lbm (lbm v)
        goal-lbm (:goal-lbm @user-input)
        {workout-cals :workout rest-cals :rest} (recomp/calorie-goals v)
        {:keys [calories protein fat carbs]} (if (= :workout k)
                                               (recomp/workout-diet workout-cals current-lbm goal-lbm)
                                               (recomp/rest-diet rest-cals current-lbm goal-lbm))
        ]
    [:tr
     [:td (name k)]
     [:td (maintenance-calories v)]
     [:td calories]
     [:td protein]
     [:td fat]
     [:td carbs]]))

(defn component []
  [:div.row

   [:h2 "John Romaniello's Body Recomposition Calculator"]

   [:div "This calculator is based on the formulas found on Roman's blog. See "
    [:a {:href "http://romanfitnesssystems.com/articles/calorie-calculations-for-body-recompositioning/"} "link"] "."]

   [:hr]

   (make-input {:name :weight
                :label "Weight"
                :placeholder 180})
   (make-input {:name :bfp
                :label "BFP"
                :placeholder 0.12})
   (make-input {:name :goal-lbm
                :label "Goal LBM"
                :placeholder 160})

   ; [:div "Output"]
   ; [:div "Weight: " (:weight @user-input)]
   ; [:div "BFP: " (:bfp @user-input)]
   ; [:div "Goal LBM: " (:goal-lbm @user-input)]

   [:table
      [:tr
       [:th "Workout / Rest"]
       [:th "Maintenance Calories"]
       [:th "Calories"]
       [:th "Protein"]
       [:th "Fat"]
       [:th "Carbs"]]
      (diet-row :workout)
      (diet-row :rest)]])
      ;(diet-row (alpha/daily-diet (Time. 1 1) (vitals)))]])
