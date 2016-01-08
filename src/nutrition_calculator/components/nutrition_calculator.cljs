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
                :label "Weight (pounds)"
                :placeholder 180})
   (make-input {:name :bfp
                :label "Body Fat Percentage"
                :placeholder 0.12})
   (make-input {:name :goal-lbm
                :label "Desired Lean Body Mass (pounds)"
                :placeholder 160})

   [:table
      [:tr
       [:th "Workout Day / Rest Day"]
       [:th "Maintenance Calories"]
       [:th "Calories"]
       [:th "Protein (grams)"]
       [:th "Fat (grams)"]
       [:th "Carbs (grams)"]]
      (diet-row :workout)
      (diet-row :rest)]])
