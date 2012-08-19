(ns leiningen.file-helpers
  (:require [clojure.java.io :as io]))

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

(defn ensure-file-obj [name-or-obj]
  (if (instance? java.io.File name-or-obj)
    name-or-obj
    (io/file name-or-obj)))

(defn aggregate-files [inputs output]
  (with-open [wrtr (io/writer (ensure-file-obj output) :append true)]
    (doseq [input inputs]
      (try 
        (with-open [rdr (io/reader (ensure-file-obj input))]
          (doseq [line (line-seq rdr)]
            (.write wrtr (str line "\n"))))
        (catch java.io.FileNotFoundException e
          (prn (str "Couldn't find file " (.getAbsolutePath (ensure-file-obj input)) " continuing...")))))))

(defn get-files-in-dirs [dirnames & [suffix]]
  (set (flatten (for [dirname dirnames]
                  (let [files (-> dirname io/file .getAbsoluteFile file-seq)]
                    (if suffix
                      (filter #(ends-with (.getName %) suffix) files)
                      files))))))
