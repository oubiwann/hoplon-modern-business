(ns io.cogitat.bootdemo
  (:require [tailrecursion.boot.core :refer [deftask]]))


(defn print-message
  ([]
    (println "Hello, boot!"))
  ([msg args]
    (print-message)
    (println msg)
    (println args)))

(deftask foo
  "A simple task with no args to show the basic form; simply prints a message.

  Called as:
      $ boot foo
  "
  [boot]
  (fn [continue]
    (fn [event]
      (print-message)
      (continue event))))

(deftask bar
  "A simple task with one required arg; prints message with the arg.

  Called as:
      $ boot [bar wib]
  "
  [boot arg]
  (fn [continue]
    (fn [event]
      (print-message "Here is your one arg:" arg)
      (continue event))))

(deftask baz
  "A simple task with one optional arg; prints message, and optional arg.

  Called as:
      $ boot [baz wib]
      $ boot baz
  "
  [boot & [arg]]
  (fn [continue]
    (fn [event]
      (print-message "Here is your one arg:" arg)
      (continue event))))

(deftask qux
  "A simple task with two args; prints message with args.

  Called as:
      $ boot [qux wib wob]
  "
  [boot arg1 arg2]
  (fn [continue]
    (fn [event]
      (print-message "Here are your two args:" [arg1 arg2])
      (continue event))))

(deftask quux
  "A simple task with multiple args; prints message with args.

  Called as:
      $ boot [quux wib wob wab]
      $ boot [quux wib wob]
      $ boot [quux wib]
      $ boot quux
  "
  [boot & args]
  (fn [continue]
    (fn [event]
      (print-message "Here are your args:" args)
      (continue event))))

(deftask corge
  "A simple task with two named args; prints message with arg values.

  Called as:
    $ boot [corge :foo wib :bar wob]
    $ boot [corge :foo wib]
    $ boot [corge :bar wob]
  "
  [boot & {:keys [foo bar]}]
  (fn [continue]
    (fn [event]
      (print-message "Here are your two named args:" [foo bar])
      (continue event))))

(deftask grault
  "A simple task with two named args; prints message with arg values.

  Called as:
    $ boot [grault qux :foo quux :bar corge
    $ boot [grault qux :foo quux]
    $ boot [grault qux]
  "
  [boot arg & {:keys [foo bar] :or {:foo "baz"}}]
  (fn [continue]
    (fn [event]
      (print-message "Here is your arg:" arg)
      (println "Here are your named args:")
      (println [foo bar])
      (continue event))))
