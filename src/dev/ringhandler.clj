(ns dev.ringhandler
  (:require [org.httpkit.server :as httpkit]
            [ion-starter.core :as app]))

(defn handler [request]
  (app/web-request-handler-ion* request))

(defn run-handler [port]
  (httpkit/run-server handler {:port port}))
