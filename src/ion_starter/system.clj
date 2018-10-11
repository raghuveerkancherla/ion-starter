(ns ion-starter.system
  (:require [com.stuartsierra.component :as component]
            [ion-starter.components :as ion-components]))


(defonce system (atom nil))

(defn started? [sys]
  (-> sys :config :value))


(defn init-ion-starter-system [system-components]
  (when (not (started? @system))
    (when-let [new-system (apply component/system-map system-components)]
      (reset! system new-system))))

(defn get-ion-starter-system []
  (if (started? @system)
    system

    (let [handler-component (ion-components/make-request-handler-component :parser-injections #{:config})
          config-component (ion-components/make-config-component :config-path "config/dev.edn")
          ]
      (init-ion-starter-system [:handler handler-component
                                :config config-component])
      (swap! system component/start)
      system)))

(defn stop-system
  "Stop the server."
  []
  (when (started? @system)
    (swap! system component/stop))
  (reset! system nil))
