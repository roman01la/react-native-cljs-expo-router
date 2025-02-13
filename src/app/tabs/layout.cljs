(ns app.tabs.layout
  (:require [expo-router :as r]
            [expo-status-bar :refer [StatusBar]]
            [react-native :as rn]
            [uix.core :as uix :refer [$ defui]]
            [app.tabs.index]
            [app.tabs.explore]))

(defui ^:export root []
  (js/console.log "TABS LAYOUT")
  ($ r/Tabs
     {:screenOptions #js {:headerShown false}}
     ($ r/Tabs.Screen {:name "index"
                       :options #js {:title "Home"}})
     ($ r/Tabs.Screen {:name "explore"
                       :options #js {:title "Explore"}})))
