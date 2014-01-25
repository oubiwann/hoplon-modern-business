(ns io.cogitat.boot
  (:require [tailrecursion.boot.core :refer [deftask]]))


(deftask foo
  "A simple task to show the basic form; simply prints a message."
  [boot]
  (print 
  (fn [continue]
    (fn [event]
      (println "Hello, boot!")
      (continue event))))
