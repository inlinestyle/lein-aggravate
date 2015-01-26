# lein-aggravate

[![Clojars Project](http://clojars.org/lein-aggravate/latest-version.svg)](http://clojars.org/lein-aggravate)

A Leiningen plugin for concatenating and compressing files. 

Good for your `.css` files (reduce page load time by cutting down on asset loading calls).

Possibly good for your `.js` files (good use cases are compiled `.coffee` files and natural `.js` files).

This plugin will concatenate anything, but for now the only available compressor is [YuiCompressor](https://github.com/yui/yuicompressor/), and only for `css` (there's some crazy rhino version dependency issue with `js` parsing).

## Usage

### Adding to your project

1. To get this at the user level, just follow the usual Leiningen instructions:
    * For Leiningen 2: Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `:user` profile.
    * For Leiningen 1: Do `lein plugin install lein-aggravate 0.1.0-SNAPSHOT`.

2. For your individual project:
    * Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `project.clj`.

### Configuring

There are two optional fields that you can add to your `project.clj`, `aggravate-files` and `aggravate-dirs`.
The following is a somewhat contrived usage example:

```clojure
:aggravate-files [{:input ["foo/bar.css" "foo/baz.css"]
                   :output "foo/compiled-files/main.css"
                   :compressor "yui"}
                  {:input ["messed/up.css" "messed/up/more.css"]
                   :output "foo/compiled-files/messed.css"}]
:aggravate-dirs [{:input ["foo"]
                  :output "foo/compiled-dirs/main.css"
                  :suffix "css"
                  :compressor "yui"}]
```
* In both cases, the only required settings are `:input` and `:output`. 
* `:aggravate-dirs` recursively fetches all files in or below the specified directory (uses `file-seq`)
* The `:suffix` option for `:aggravate-dirs` maps will filter the files it finds.
* Currently the only available option for `:compressor` is `"yui"`, and note that this should only be used on `css` files.

### Running

You can run `aggravate` in your project with:
```
$ lein aggravate
```

You can also have `aggravate` run after the rest of your Leiningen project compiles by adding the following to your `project.clj`:
```clojure
:hooks [leiningen.aggravate]
```

##TODOS

1. Unit tests

2. Some form of CI (Travis?)

3. Add a compressor for `js`

4. Learn to write better clojure

5. Learn to write better READMEs

## License

Copyright © 2012 Ben Yelsey

Distributed under the Eclipse Public License, the same as Clojure.
