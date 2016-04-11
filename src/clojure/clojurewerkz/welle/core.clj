(ns clojurewerkz.welle.core
  (:import [com.basho.riak.client.api RiakClient RiakCommand]
           [com.basho.riak.client.core RiakCluster$Builder RiakNode$Builder RiakFuture]))


(defn new-client
  "creates a new client connection
Accepts an array of addresses that may contain ports
(new-client [\"127.0.0.1\" \"127.0.0.1:8087\"]) "
  ([]
   (new-client ["127.0.0.1"] {}))
  ([addresses]
   (new-client addresses {}))
  ([^java.util.List addresses {:keys [min-connections max-connections username password keystore]
                               :or {min-connections 10 max-connections 50}}]
   (let [builder (doto (new RiakNode$Builder)
                   ;; todo add auth here (.withAuth username password keystore)
                   (.withMinConnections min-connections)
                   (.withMaxConnections max-connections)
                   )
         nodes (RiakNode$Builder/buildNodes builder addresses)
         cluster (.build (new RiakCluster$Builder nodes))]
     (.start cluster)
     (new RiakClient cluster))))

(defn execute [^RiakClient client ^RiakCommand command]
  (.execute client command))

(defn ^RiakFuture execute-async [^RiakClient client ^RiakCommand command]
  (.executeAsync client command))

(defn get-cluster [^RiakClient client]
  (.getRiakCluster client))

(defn shutdown [^RiakClient client]
  (.shutdown client))
