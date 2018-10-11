(ns ion-starter.components
  (:require [com.stuartsierra.component :as component]
            [fulcro.easy-server :as easy-server]
            [fulcro.server :as server]))


(defn make-request-handler-component
  "*`parser`             OPTIONAL, an om parser function for parsing requests made of the server. To report errors, the
                         parser must throw an ExceptionInfo with a map with keys `:status`, `:headers`, and `:body`.
                         This map will be converted into the response sent to the client. Defaults to `server/fulcro-parser`

   *`parser-injections`  a vector of keywords which represent components which will be injected as the om parsing env.

   *`extra-routes`       OPTIONAL, a map containing `:routes` and `:handlers`,
                           where routes is a bidi routing data structure,
                           and handlers are map from handler name to a function of type :: Env -> BidiMatch -> Res
                           see `handler/wrap-extra-routes` & handler-spec for more.

   *`app-name`           OPTIONAL, a string that will turn \"/api\" into \"<app-name>/api\""
  [& {:keys [app-name parser parser-injections extra-routes]
      :or   {parser (server/fulcro-parser)}
      :as   params}]
  {:pre [(some-> parser fn?)
         (or (nil? extra-routes)
             (and (map? extra-routes)
                  (:routes extra-routes)
                  (map? (:handlers extra-routes))))
         (or (nil? parser-injections)
             (and (set? parser-injections)
                  (every? keyword? parser-injections)))]}
  (let [handler             (easy-server/build-handler parser parser-injections
                                                       :extra-routes extra-routes
                                                       :app-name app-name)]
    handler))


(defn make-config-component
  "  *`config-path`        OPTIONAL, a string of the path to your configuration file on disk.
                          The system property -Dconfig=/path/to/conf can also be passed in from the jvm."
  [& {:keys [config-path]
      :as params
      :or   {config-path "/usr/local/etc/fulcro.edn"}}]
  (server/new-config config-path))
