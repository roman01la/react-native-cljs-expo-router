(ns app.not-found
  (:require [expo-router :as r]
            [expo-status-bar :refer [StatusBar]]
            [react-native :as rn]
            [uix.core :as uix :refer [$ defui]]))

(defui root []
  (js/console.log "NOT FOUND")
  ($ r/Stack.Screen {:options #js {:title "Ooops!"}}))
