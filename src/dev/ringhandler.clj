(ns dev.ringhandler
  (:require [org.httpkit.server :as httpkit]
            [datomic.ion.starter :as app]))

(defn handler [request]
  (app/items-by-type-web* request))

(defn run-handler [port]
  (httpkit/run-server handler {:port port}))
