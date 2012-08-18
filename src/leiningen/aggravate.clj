(ns leiningen.aggravate
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as string]))

(defn ends-with [string suffix]
  (let [index (.lastIndexOf string suffix)]
    (if-not (neg? index)
      (= (.substring string index) suffix)
      false)))

(defn remove-file "Delete a file, and return the file object" [file-obj]
  (io/delete-file file-obj true)
  file-obj)

(defn remove-files "Delete the file if it exists" [filename]
  (let [file-obj (io/file filename)]
    (cond
      (not file-obj) (throw (Exception. "No output file supplied for option set!"))
      (.isDirectory file-obj) (throw (Exception. (str "Directory named " filename " in the way")))
      (and (.exists file-obj) (-> file-obj .isFile not)) (throw (Exception. (str "Something weird named " filename " in the way")))
      :else (remove-file file-obj))))

(defn create-file "Make a new file" [filename]
  (let [file-obj (io/file filename)]
    (let [parent-file (.getParentFile file-obj)]
      (if parent-file
        (.mkdirs parent-file)))
    (.createNewFile file-obj)
    file-obj))

(defn aggregate-files [inputs output]
  (with-open [wrtr (io/writer (io/file output) :append true)]
    (doseq [input inputs]
      (try 
        (with-open [rdr (io/reader input)]
          (doseq [line (line-seq rdr)]
            (.write wrtr (str line "\n"))))
        (catch java.io.FileNotFoundException e
          (prn (str "Couldn't find file " (.getName input) " continuing...")))))))

(defn get-files-in-dirs [dirnames & [suffix]]
  (set (flatten (for [dirname dirnames]
                  (let [files (-> dirname io/file .getAbsoluteFile file-seq)]
                    (if suffix
                      (filter #(ends-with (.getName %) suffix) files)
                      files))))))

(defn aggravate "Aggregate your files!" [project & args]
  (doseq [options (mapcat project [:aggregate-files :aggregate-dirs])]
    (remove-files (options :output)))
  (doseq [options (project :aggregate-dirs)]
    (aggregate-files (get-files-in-dirs (options :input) (options :suffix)) 
                     (create-file (options :output))))
  (doseq [options (project :aggregate-files)]
    (aggregate-files (map io/file (options :input)) 
                     (create-file (options :output)))))
