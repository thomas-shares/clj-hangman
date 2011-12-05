(ns clj-hangman.test.core
  (:use [clj-hangman.core])
  (:use [clojure.test]))

 

(deftest test-check-letter
    (is (check-letter "a" "a" ".") "a")
    (is (check-letter "a" "b" ".") ".")
    (is (check-letter "a" "b" "q") "q"))
    
(deftest test-my-func
    (is (my-func \e "......." ) nil))
