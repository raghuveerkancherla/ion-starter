(ns ion-starter.core
  (:require
    [clojure.data.json :as json]
    [clojure.edn :as edn]
    [clojure.java.io :as io]
    [clojure.pprint :as pp]
    [datomic.client.api :as d]
    [datomic.ion.lambda.api-gateway :as apigw]
    [ion-starter.system :as system]))

(def get-client
  "This function will return a local implementation of the client
interface when run on a Datomic compute node. If you want to call
locally, fill in the correct values in the map."
  (memoize #(d/client {:server-type :ion
                       :region      "us-east-1"
                       :system      "myapp"
                       ;:query-group "stu-8"
                       :endpoint    "http://entry.myapp.us-east-1.datomic.net:8182/"
                       :proxy-port  8182})))


(defn web-request-handler-ion*
  "Lambda ion that returns sample database items matching type."
  [{:keys [headers body] :as request}]
  (let [started-system (system/get-ion-starter-system)]
    ((-> @started-system
       :handler
       :middleware) request)
    ))

    ;(if (= (:uri request) "/api")
    ;  {:status 200
    ;   :headers {"Content-Type" "text/html"}
    ;   :body "Got a api request"}
    ;  {:status 200
    ;   :headers {"Content-Type" "text/html"}
    ;   :body (slurp (io/resource "ion-starter/index.html"))}))

(def web-request-handler-ion
  "API Gateway web service ion for items-by-type"
  (apigw/ionize web-request-handler-ion*))
