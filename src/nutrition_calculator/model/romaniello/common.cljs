(ns ^:figwheel-always nutrition-calculator.model.romaniello.common
  (:require [nutrition-calculator.helpers.math :as math]))

(defrecord Vitals [weight bfp])

(defn lbm [^Vitals vitals]
  "Finds LBM from body fat percentage."
  (let [weight (:weight vitals)
        bfp (:bfp vitals)]
    (- weight (* bfp weight))))

(defn maintenance-calories-per-lbm [bfp]
  "Finds maintenance calories per lb based on body fat."
  (cond
    (<=   bfp  0.12 ) 17
    (<=   bfp  0.15 ) 16
    (<=   bfp  0.19 ) 15
    (<=   bfp  0.22 ) 14
    (>    bfp  0.22 ) 13))

(defn maintenance-calories [^Vitals vitals]
  (let [lbm (lbm vitals)]
    (math/round (* (maintenance-calories-per-lbm (:bfp vitals)) lbm))))
