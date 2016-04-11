(ns clojurewerkz.welle.test.core-test
  (:require [clojurewerkz.welle.core :as wc]
            [clojure.test :refer :all]))

(deftest new-client-connects
  (testing "accepts zero arguments"
    (let [client (wc/new-client)]
      (is (instance? com.basho.riak.client.api.RiakClient client))
      (wc/shutdown client)))
  (testing "accepts an array with two ip addresses"
    (let [client (wc/new-client ["127.0.0.1" "127.0.0.1"])]
      (is (instance? com.basho.riak.client.api.RiakClient client))
      (wc/shutdown client)))
  (testing "accepts an array with two ip addresses and ports"
    (let [client (wc/new-client ["127.0.0.1:8087" "127.0.0.1:8087"])]
      (is (instance? com.basho.riak.client.api.RiakClient client))
      (wc/shutdown client))))
