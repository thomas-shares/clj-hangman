(ns clj-hangman.test.core
  (:use [clj-hangman.core])
  (:use midje.sweet))

(fact
    (check-letter "a" "a" ".") => "a" 
    (check-letter "a" "b" ".") => "."
    (check-letter "a" "b" "q") => "q")

(fact
    (my-search-func \e "test" "...." ) => ".e.."
    (my-search-func \t "test" "...." ) => "t..t"
    (my-search-func \t "test" ".e.." ) => "te.t" ) 
