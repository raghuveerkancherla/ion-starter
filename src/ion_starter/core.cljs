(ns ion-starter.core
  (:require [fulcro.client :as fc]
            [ion-starter.ui.root :as root]))



(defonce app (atom (fc/new-fulcro-client)))


(defn mount []
  (reset! app (fc/mount @app root/Root "app")))

(mount)

