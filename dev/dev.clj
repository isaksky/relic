(ns dev
  (:require [clj-async-profiler.core :as clj-async-profiler]
            [shadow.cljs.devtools.api :as s.api]
            [shadow.cljs.devtools.server :as s.server]
            [criterium.core :as criterium]))

(def profiler-port 5001)

(defonce profiler-server
  (delay
    (let [port profiler-port
          url (str "http://localhost:" port)]
      (println "Starting serving profiles on" url)
      (clj-async-profiler/serve-files port))))

(defmacro profile
  "Profiles the given code body with clj-async-profiler, see (browse-profiler) to look at the resulting flamegraph.

  e.g (profile (reduce + (my-function)))

  Options are the same as clj-async-profiler/profile."
  [options? & body]
  `(clj-async-profiler/profile ~options? ~@body))

(defn start-profiler
  "Start clj-async-profiler see also: (stop-profiler) (browse-profiler)

  Options are the same as clj-async-profiler/start."
  ([] (clj-async-profiler/start))
  ([options] (clj-async-profiler/start options)))

(defn stop-profiler
  "Stops clj-async-profiler, see (browse-profiler) to go look at the profiles in a nice little UI."
  []
  (let [file (clj-async-profiler/stop)]
    (println "Saved flamegraph to" (str file))))

(defn browse-profiler
  "Opens the clj-async-profiler page in your browser, you can go look at your flamegraphs and start/stop the profiler
  from here."
  []
  @profiler-server
  (clojure.java.browse/browse-url (str "http://localhost:" profiler-port)))

(defn cljs-repl
  ([] (cljs-repl :browser))
  ([build-id]
   (s.server/start!)
   (s.api/watch build-id)
   (s.api/repl build-id)))

(defn cljs-test []
  (s.api/compile :test))

(defmacro bench [expr]
  `(criterium/quick-bench ~expr))

(defn stop-shadow []
  (s.server/stop!))

(defn clj-tests []
  (require 'clojure.test)
  (clojure.test/run-all-tests #"com\.wotbrew\.relic(.*)-test"))