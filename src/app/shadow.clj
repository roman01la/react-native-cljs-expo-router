(ns app.shadow
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [shadow.build.targets.react-native]
            [shadow.build.output]))

(defn fake-expo-fs [ns-root id->sym sources]
  (->> sources
       (filter (comp #(str/starts-with? % ns-root) second))
       (map (fn [[_ path :as id]]
              [(-> path
                   (str/replace #"^app" ".")
                   (str/replace #"layout\.clj[cs]$" "_layout.js")
                   (str/replace #"not_found\.clj[cs]$" "+not-found.js")
                   (str/replace #"tabs\/" "(tabs)/")
                   (str/replace #"\.clj[cs]$" ".js"))
               (-> id id->sym munge (str ".root"))]))))

(defn ->js-object [entries]
  (str "{"
       (->> entries
            (map (fn [[k v]] (str "\"" k "\": " v)))
            (str/join ", "))
       "}"))

(defn bootstrap-expo-context [fs-obj]
  (str "var _expo_components = " fs-obj ";"
       "var __uix_react = require('react');"
       "export var ctx = function(path) {
         if (_expo_components[path]) {
           return { default: (props) => __uix_react.createElement(_expo_components[path], props) };
         }
       };
       ctx.keys = () => Object.keys(_expo_components);"))

(defn inject-expo-context [state {:keys [sources] :as mod}]
  (when-let [ns-root (some-> state :shadow.build/config :expo/root-ns munge str)]
    (let [id->sym (set/map-invert (:sym->id state))
          js-str (bootstrap-expo-context (->js-object (fake-expo-fs ns-root id->sym sources)))]
      (update mod :append str js-str))))

(defonce __init
  (do
    ;; dev build
    (alter-var-root #'shadow.build.targets.react-native/flush-unoptimized!
      (fn [f]
        (fn [state mod]
          (f state (inject-expo-context state mod)))))
    ;; prod build
    (alter-var-root #'shadow.build.output/flush-optimized-module
      (fn [f]
        (fn [state mod]
          (f state (inject-expo-context state mod)))))))