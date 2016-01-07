(ns nutrition-calculator.actions
  (:require [nutrition-calculator.session :as session :refer [app-state user-input]]))


(defonce id-counters {:condition (atom 1)
                      :medication (atom 1)})


(defn new-id [t]
  (let [a (get id-counters t)
        id @a]
    (swap! a inc)
    id))

(defn add-condition [name]
  (let [{:keys [id] :as condition} {:name name :id (new-id :condition)}]
    (swap! app-state assoc-in [:conditions id] condition)))

(defn add-medication [name]
  (let [{:keys [id] :as medication} {:name name :id (new-id :medication) :conditions {} }]
    (swap! app-state assoc-in [:medications id] medication)))

(defn- mapify [v]
  (reduce merge (map #(hash-map (first %) (last %)) v)))

; (println "mapify: "
;          (mapify [[:1 1] [:2 "hello"]]))

(defn delete-condition [cid]
  (swap! app-state (fn [{:keys [conditions medications] :as old}]
                     (let [new-meds (for [[k medication] (seq medications)]
                                                [k (update-in medication [:conditions] #(dissoc % cid))])
                           new-conds (remove #(= cid (first %)) (seq conditions))
                           new-state (-> old
                                       (assoc :medications (mapify new-meds))
                                       (assoc :conditions (mapify new-conds)))]
                       new-state))))

(defn add-condition-to-medication [{mid :id :as medication} cid]
  (swap! app-state assoc-in [:medications mid :conditions cid] cid))

(defn remove-condition-from-medication [{mid :id :as medication} cid]
  (swap! app-state update-in [:medications mid :conditions] #(dissoc % cid)))

(defn set-compliance [{mid :id :as medication} status]
  (swap! app-state assoc-in [:medications mid :compliance-status] status))
