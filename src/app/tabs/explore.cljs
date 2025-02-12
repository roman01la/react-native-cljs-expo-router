(ns app.tabs.explore
  (:require [expo-router :as r]
            [expo-status-bar :refer [StatusBar]]
            [react-native :as rn]
            [uix.core :as uix :refer [$ defui]]))

(defui root []
  (js/console.log "TAB EXPLORE")
  ($ rn/View {:style {:flex 1
                      :justify-content :center
                      :align-items :center}}
     ($ rn/Text {:style {:font-size 24
                         :font-weight 600}}
        "This is Explore tab")))
