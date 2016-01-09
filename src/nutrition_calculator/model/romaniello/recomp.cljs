(ns ^:figwheel-always nutrition-calculator.model.romaniello.recomp
  (:require [nutrition-calculator.helpers.math :as math]
            [nutrition-calculator.model.romaniello.common :refer [lbm maintenance-calories]]))

(defn round-all-numbers [m]
  (into {} (for [[k v] m] [k (math/round v)])))

(defn calorie-goals [^Vitals vitals]
  (let [maintenance (maintenance-calories vitals)]
    {:workout (+ maintenance 100)
     :rest (- maintenance 500)}))

(defn calc-diet [calories current-lbm goal-lbm protein-coeff carb-coeff]
  (if (or
        (<= goal-lbm 0)
        (<= current-lbm 0)
        (<= calories 0))
    nil
    (let [protein-grams (* protein-coeff goal-lbm)
          carb-grams (* carb-coeff current-lbm)
          protein-cals (* 4 protein-grams)
          carb-cals (* 4 carb-grams)
          fat-cals (- calories (+ protein-cals carb-cals))
          fat-grams (/ fat-cals 9)]
      (round-all-numbers
        {:calories calories
         :protein protein-grams
         :carbs carb-grams
         :fat fat-grams}))))

(defn rest-diet [calories current-lbm goal-lbm]
  (calc-diet calories current-lbm goal-lbm 1.35 0.5))

(defn workout-diet [calories current-lbm goal-lbm]
  (calc-diet calories current-lbm goal-lbm 1.5 1.0))

(defn total-diet [^Vitals vitals goal-lbm]
  (let [{workout-calories :workout rest-calories :rest} (calorie-goals vitals)
        current-lbm (lbm vitals)]
    {:rest (rest-diet rest-calories current-lbm goal-lbm)
     :workout (workout-diet workout-calories current-lbm goal-lbm)}))

