(ns ion-starter.ui.root
  (:require [fulcro.client.primitives :as prim :refer [defsc]]
            #?(:cljs [fulcro.client.dom :as dom] :clj [fulcro.client.dom-server :as dom])))


(defsc Root [this props]
  {}
  (dom/div nil
           "Hello World!"))
