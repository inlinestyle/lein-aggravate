# lein-aggravate

A Leiningen plugin for concatenating and compressing files. 

Good for your `.css` files (reduce page load time by cutting down on asset loading calls).

Possibly good for your `.js` files (good use cases are compiled `.coffee` files and natural `.js` files).

This plugin will concatenate anything, but for now the only available compressor is [YuiCompressor](https://github.com/yui/yuicompressor/), and only for `css` (there's some crazy rhino version dependency issue with `js` parsing).

## Usage

To get this at the user level, just follow the usual Leiningen instructions:
For Leiningen 2: Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `:user` profile.
For Leiningen 1: Do `lein plugin install lein-aggravate 0.1.0-SNAPSHOT`.

For your individual project:
Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `project.clj`.


There are two optional fields that you can add to your `project.clj`, `aggregate-files` and `aggregate-dirs`:

```clojure
:aggregate-files [{:input ["foo/bar.css" "foo/baz.css"]
                   :output "foo/compiled-files/main.css"
                   :compressor "yui"}
                  {:input ["messed/up.css" "messed/up/more.css"]
                   :output "foo/compiled-files/messed.css"}]
:aggregate-dirs [{:input ["foo"]
                  :output "foo/compiled-dirs/main.css"
                  :suffix "css"
                  :compressor "yui"}]
```
* In both cases, the only required settings are `:input` and `:output`. 
* `:aggregate-dirs` recursively fetches all files in or below the specified directory (uses `file-seq`)
* The `:suffix` option for `:aggregate-dirs` maps will filter the files it finds.
* Currently the only available option for `:compressor` is `"yui"`, and note that this should only be used on `css` files.


You can run this in your project with:
```
$ lein aggravate
```

##TODOS

1. Implement Leiningen hook usage.

2. Unit tests

3. Add a compressor for `js`

4. Learn to write better clojure

5. Learn to write better READMEs

## License

Copyright © 2012 Ben Yelsey

Distributed under the Eclipse Public License, the same as Clojure.
