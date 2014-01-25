(ns io.cogitat.boot
  (:require [boot.core :refer [deftask]]))


(deftask foo [boot]
  (fn [continue]
    (fn [event]
      (println "Hello, boot!")
      (continue event))))
