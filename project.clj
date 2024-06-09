(defproject io.logicblocks/icu4clj "0.0.1-RC2"
  :description "A thin wrapper around ICU4J."
  :url "https://github.com/logicblocks/icu4clj"

  :license {:name "The MIT License"
            :url  "https://opensource.org/licenses/MIT"}

  :plugins [[fipp "0.6.26"]
            [lein-cloverage "1.2.4"]
            [lein-shell "0.5.0"]
            [lein-ancient "0.7.0"]
            [lein-changelog "0.3.2"]
            [lein-cprint "1.3.3"]
            [lein-eftest "0.6.0"]
            [lein-codox "0.10.8"]
            [lein-cljfmt "0.9.2"]
            [lein-kibit "0.1.8"]
            [lein-bikeshed "0.5.2"]
            [jonase/eastwood "1.4.0"]]

  :dependencies [[com.ibm.icu/icu4j "75.1"]]

  :profiles
  {:shared
   ^{:pom-scope :test}
   {:dependencies
    [[org.clojure/clojure "1.11.3"]
     [org.clojure/test.check "1.1.1"]

     [com.github.flow-storm/clojure "1.11.3-1"]
     [com.github.flow-storm/flow-storm-dbg "3.15.5"]

     [vlaaad/reveal "1.3.282"]

     [nrepl "1.1.2"]

     [eftest "0.6.0"]]}

   :dev-specific
   {:source-paths ["dev"]
    :eftest       {:multithread? false}}

   :flow-storm-specific
   {:exclusions [org.clojure/clojure]
    :jvm-opts   ["-Dclojure.storm.instrumentEnable=true"
                 "-Dclojure.storm.instrumentOnlyPrefixes=spec.validate,clojure.spec"]}

   :reveal-specific
   {:repl-options {:nrepl-middleware [vlaaad.reveal.nrepl/middleware]}
    :jvm-opts     ["-Dvlaaad.reveal.prefs={:theme :light}"]}

   :test-specific
   {:eftest {:multithread? false}}

   :dev
   [:shared :dev-specific]

   :flow-storm
   [:shared :flow-storm-specific]

   :reveal
   [:shared :reveal-specific]

   :test
   [:shared :test-specific]

   :prerelease
   {:release-tasks
    [["shell" "git" "diff" "--exit-code"]
     ["change" "version" "leiningen.release/bump-version" "rc"]
     ["change" "version" "leiningen.release/bump-version" "release"]
     ["vcs" "commit" "Pre-release version %s [skip ci]"]
     ["vcs" "tag"]
     ["deploy"]]}

   :release
   {:release-tasks
    [["shell" "git" "diff" "--exit-code"]
     ["change" "version" "leiningen.release/bump-version" "release"]
     ["codox"]
     ["changelog" "release"]
     ["shell" "sed" "-E" "-i.bak" "s/\"[0-9]+\\.[0-9]+\\.[0-9]+\"/\"${:version}\"/g" "README.md"]
     ["shell" "rm" "-f" "README.md.bak"]
     ["shell" "git" "add" "."]
     ["vcs" "commit" "Release version %s [skip ci]"]
     ["vcs" "tag"]
     ["deploy"]
     ["change" "version" "leiningen.release/bump-version" "patch"]
     ["change" "version" "leiningen.release/bump-version" "rc"]
     ["change" "version" "leiningen.release/bump-version" "release"]
     ["vcs" "commit" "Pre-release version %s [skip ci]"]
     ["vcs" "tag"]
     ["vcs" "push"]]}}

  :target-path "target/%s/"

  :cloverage
  {:ns-exclude-regex [#"^user"]}

  :codox
  {:namespaces  [#"^icu4clj\."]
   :metadata    {:doc/format :markdown}
   :output-path "docs"
   :doc-paths   ["docs"]
   :source-uri  "https://github.com/logicblocks/icu4clj/blob/{version}/{filepath}#L{line}"}

  :cljfmt {:indents ^:replace {#".*" [[:inner 0]]}}

  :eastwood {:config-files ["config/linter.clj"]}

  :bikeshed {:name-collisions false
             :long-lines      false}

  :deploy-repositories
  {"releases"  {:url "https://repo.clojars.org" :creds :gpg}
   "snapshots" {:url "https://repo.clojars.org" :creds :gpg}})
