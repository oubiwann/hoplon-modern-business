(ns io.cogitat.boot
  (:require [tailrecursion.boot.core :refer [deftask mkdir! mk!]]))


(deftask process-hlx
  "A custom pre-processor for use with Hoplon.

  This task does the following:
   * compiles boilerplate .hlx files to .hl files (in a temp dir)
   * adds the temp dir to the boot env's src-paths so that they will get
     compiled
   * 

  Called as:
      $ boot watch process-hlx hoplon
  "
  [boot]
  (fn [continue]
    (fn [event]
      ; XXX do the stuffs
      ; see doc/notex.rst, "Remove the need to create HEAD boilerplate"
      (continue event))))
