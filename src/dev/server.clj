(ns dev.server
  (:require [ion-starter.system :as system]
            [ion-starter.components :as ion-components]
            [com.stuartsierra.component :as component]
            [fulcro.easy-server :as easy-server]))



(defn start []
  "
  Dev version of system component along with a web server.
  Similar function exists in ion-starter.system/get-ion-starter-system that does not start a local web server
  "
  (if (system/started? @system/system)
    system/system

    (let [handler-component (ion-components/make-request-handler-component :parser-injections #{:config})
          config-component (ion-components/make-config-component :config-path "config/dev.edn")
          web-server-component (easy-server/make-web-server)
          ]
      (system/init-ion-starter-system [:handler handler-component
                                       :config config-component
                                       :server web-server-component])
      (swap! system/system component/start)
      system/system)))

