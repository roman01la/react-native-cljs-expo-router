(ns app.layout
  (:require [expo-router :as r]
            [expo-status-bar :refer [StatusBar]]
            [react-native :as rn]
            [uix.core :as uix :refer [$ defui]]
            [app.not-found]
            [app.tabs.layout]))

(defui ^:export root []
  (js/console.log "MAIN LAYOUT")
  ($ :<>
     ($ StatusBar {:style "auto"})
     ($ r/Stack
        ($ r/Stack.Screen {:name "(tabs)" :options #js {:headerShown false}})
        ($ r/Stack.Screen {:name "+not-found"}))))
