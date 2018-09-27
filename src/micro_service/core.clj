(ns micro-service.core
  (:require [org.httpkit.server :refer [run-server]]
            [clj-time.core :as t]
            [compojure.core :refer [routes GET ANY]]
            [compojure.route :as route]))

(defonce ^:private server (atom nil))

(defn remove-trailing-slashes [handler]
  (fn [req]
    (let [uri (:uri req)
          not-root? (not= uri "/")
          ends-with-slash? (.endsWith ^String uri "/")
          fixed-uri (if (and not-root?
                             ends-with-slash?)
                      (subs uri 0 (dec (count uri)))
                      uri)
          fixed-req (assoc req :uri fixed-uri)]
      (handler fixed-req))))

(defn get-time
  []
  (let [response {:status  200
                  :headers {"Content-Type" "application/json"}
                  :body    (str (t/time-now))}]
    response))

(defn stop-server [server]
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn app []
  (routes (GET "/" [] "Welcome")
          (GET "/get-time" [] (get-time))
          (route/not-found "Not found")))


(defn -main [& args]
  (run-server (remove-trailing-slashes (app)) {:port 8080})
  (println "Server started on port 8080"))
