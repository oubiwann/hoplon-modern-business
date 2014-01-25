(ns io.cogitat.devserver
  (:require [clojure.string :as string]
            [compojure.route :as route]
            [compojure.core :refer [defroutes]]
            [compojure.handler :refer [site]]
            [org.httpkit.server :as httpd]
            [tailrecursion.boot.core :refer [deftask]]))


(defn- log [msg & vals]
  (let [line (apply format msg vals)]
    (locking System/out (println line))))

(defn logging [handler]
  (fn [{:keys [request-method uri] :as req}]
    (let [start (System/currentTimeMillis)
          resp (handler req)
          finish (System/currentTimeMillis)
          total (- finish start)
          method (string/upper-case (name request-method))
          now (java.util.Date.)]
      (log "%s - %s - %s - (%dms) - %s" now method uri total resp)
      resp)))

(defroutes server-routes
  (route/files "/" {:root "resources/public/"})
  (route/not-found "404"))

(defn run
  ([]
    (run 9999))
  ([port]
    (httpd/run-server
      (site (-> server-routes logging)) {:port port})))

(defn stop [pid]
  nil)

(defn taillog [logfile]
  nil)

(deftask devserver
  "[NOT READY] start|stop|taillog dev server for Hoplon files."
  [boot command & {:keys [port pid] :or {:port 9999}}]
  (fn [continue]
    (fn [event]
      (case command
        "start" (fn []
                  (run :port port)
                  ; XXX set the pid for for the spawned process in the boot env
                  ; XXX set the log file location
                  )
        "stop" (stop "pid")
        "taillog" (taillog "log file")
        "default")
      (continue event))))
